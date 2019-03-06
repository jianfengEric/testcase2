package com.tng.portal.ana.service.impl;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tng.portal.ana.constant.AccountStatus;
import com.tng.portal.ana.constant.SystemMsg;
import com.tng.portal.ana.entity.AnaAccount;
import com.tng.portal.ana.entity.AnaApplication;
import com.tng.portal.ana.entity.AnaFunction;
import com.tng.portal.ana.entity.AnaRole;
import com.tng.portal.ana.entity.AnaRoleFunctionPermission;
import com.tng.portal.ana.repository.AnaAccountRepository;
import com.tng.portal.ana.repository.AnaApplicationRepository;
import com.tng.portal.ana.repository.AnaFunctionRepository;
import com.tng.portal.ana.repository.AnaRoleFunctionPermissionRepository;
import com.tng.portal.ana.repository.AnaRoleFunctionRepository;
import com.tng.portal.ana.repository.AnaRolePermissionRepository;
import com.tng.portal.ana.service.FunctionService;
import com.tng.portal.ana.service.UserService;
import com.tng.portal.ana.vo.ApplicationDto;
import com.tng.portal.ana.vo.FunctionDto;
import com.tng.portal.ana.vo.FunctionPermissionDto;
import com.tng.portal.ana.vo.FunctionPostDto;
import com.tng.portal.ana.vo.FunctionUpdateDto;
import com.tng.portal.common.exception.BusinessException;
import com.tng.portal.common.util.PropertiesUtil;
import com.tng.portal.common.vo.PageDatas;
import com.tng.portal.common.vo.rest.RestfulResponse;

/**
 * Created by Zero on 2016/11/10.
 */
@Service
@Transactional
public class FunctionServiceImpl implements FunctionService {

    private static final Logger logger = LoggerFactory.getLogger(FunctionServiceImpl.class);
    @Autowired
    private AnaFunctionRepository functionRepository;
    @Autowired
    private AnaApplicationRepository applicationRepository;
    @Autowired
    private AnaRoleFunctionPermissionRepository roleFunctionPermissionRepository;
    @Autowired
    private AnaAccountRepository anaAccountRepository;
    @Qualifier("anaUserService")
    @Autowired
    private UserService userService;

    /**
     * Create ANA function info
     * 
     * @param postDto
     * 			new function info
     * 
     * @return
     * @
     */
    @Override
    public RestfulResponse<String> createFunction(FunctionPostDto postDto)  {
        RestfulResponse<String> restResponse = new RestfulResponse<>();
        String code = postDto.getCode();
        List<AnaFunction> list = functionRepository.findByCode(code);
        if (!list.isEmpty()){
            throw new BusinessException(SystemMsg.ServerErrorMsg.exist_function.getErrorCode());
        }
        AnaApplication anaApplication = applicationRepository.findOne(postDto.getApplicationCode());
        if (anaApplication == null){
            throw new BusinessException(SystemMsg.ServerErrorMsg.not_exist_application.getErrorCode());
        }
        AnaFunction anaFunction = new AnaFunction();
        anaFunction.setCode(postDto.getCode());
        anaFunction.setDescription(postDto.getDescription());
        anaFunction.setAnaApplication(anaApplication);
        anaFunction.setPermissionSum(postDto.getPermissionSum());

        functionRepository.save(anaFunction);
        restResponse.setData(anaFunction.getCode());
        return restResponse;
    }

    /**
     * update ANA function info
     * 
     * @param updateDto
     * 			updated function info
     * @return
     * @
     */
    @Override
    public RestfulResponse<String> updateFunction(FunctionUpdateDto updateDto)  {
        RestfulResponse<String> restResponse = new RestfulResponse<>();
        AnaFunction anaFunction = functionRepository.findOne(updateDto.getCode());
        if (anaFunction == null){
            throw new BusinessException(SystemMsg.ServerErrorMsg.not_exist_function.getErrorCode());
        }
        AnaApplication anaApplication = applicationRepository.findByCode(updateDto.getApplicationCode());
        if (anaApplication == null){
            throw new BusinessException(SystemMsg.ServerErrorMsg.not_exist_application.getErrorCode());
        }
        anaFunction.setDescription(updateDto.getDescription());
        anaFunction.setPermissionSum(updateDto.getPermissionSum());
        anaFunction.setAnaApplication(anaApplication);

        restResponse.setData(updateDto.getCode());
        return restResponse;
    }


    /**
     * Delete ANA function info by function code
     * 
     * @param code
     * 			ANA_FUNCTION.CODE
     * 
     * @return
     * @
     */
    @Override
    public RestfulResponse<String> deleteFunction(String code)  {
        RestfulResponse<String> restResponse = new RestfulResponse<>();
        AnaFunction anaFunction = functionRepository.findOne(code);
        if (anaFunction == null){
            throw new BusinessException(SystemMsg.ServerErrorMsg.not_exist_function.getErrorCode());
        }
        List<AnaRoleFunctionPermission> list = roleFunctionPermissionRepository.findByAnaFunction(anaFunction);
        if (!list.isEmpty()){
            throw new BusinessException(SystemMsg.ServerErrorMsg.delete_function_error.getErrorCode());
        }
        //remove related permission of the function
        roleFunctionPermissionRepository.delectByFunctionCode(anaFunction.getCode());

        functionRepository.delete(anaFunction);
        restResponse.setData(code);
        return restResponse;
    }

    /**
     * Query ANA function list 
     * 
     * @param pageNo
	 * 			current page number
	 * 
	 * @param pageSize
	 * 			page size
	 * 
	 * @param sortBy
	 * 			sort by
	 * 
	 * @param isAscending
	 * 			true--ascend or false--descend
	 * 
     * @return
     */
    @Override
    public RestfulResponse<PageDatas> listFunctions(Integer pageNo, Integer pageSize,String sortBy,Boolean isAscending) {
        RestfulResponse<PageDatas> restResponse = new RestfulResponse<>();
        PageDatas<FunctionDto> pageDatas = new PageDatas<>(pageNo,pageSize);
        List<AnaFunction> list = pageDatas.findAll(functionRepository,sortBy,isAscending,"code");

        List<FunctionDto> functionDtos = list.stream().map(item -> {
            FunctionDto dto = new FunctionDto();
            dto.setCode(item.getCode());
            dto.setDescription(item.getDescription());
            dto.setPermissionSum(item.getPermissionSum());
            ApplicationDto application = new ApplicationDto();
            application.setCode(item.getAnaApplication().getCode());
            application.setName(item.getAnaApplication().getName());
            dto.setApplication(application);
            return dto;
        }).collect(Collectors.toList());

        pageDatas.setList(functionDtos);
        restResponse.setData(pageDatas);
        return restResponse;
    }


@Autowired
private AnaRoleFunctionRepository roleFunctionRepository;
    @Autowired
    private AnaRolePermissionRepository rolePermissionRepository;
    /**
     * Query current account's function permission
     * 
     * @param account
     * 			user account
     * 
     * @return
     * @
     */
    @Override
    public RestfulResponse<List<FunctionPermissionDto>> findFunctionPermissionByAccount(String account) {
        if (StringUtils.isBlank(account)) {
            throw new BusinessException(SystemMsg.ServerErrorMsg.not_exist_token_account.getErrorCode());
        }
        AnaAccount anaAccount = anaAccountRepository.findByAccountAndStatusNot(account, AccountStatus.Terminated.getCode());
        if (Objects.isNull(anaAccount)) {//sonar modify
            throw new BusinessException(SystemMsg.ErrorMsg.client_not_exist_account.getErrorCode(), new String[]{PropertiesUtil.getServiceName()});
        }

        Map<String, Integer> functionPermissionMap = new LinkedHashMap<>();
        List<AnaRole> anaRoles = anaAccount.getAnaRoles();

        for (AnaRole role : anaRoles) {
            List<AnaRoleFunctionPermission> roleFunctionPermissions = role.getAnaRoleFunctions();
            for (AnaRoleFunctionPermission functionPermission : roleFunctionPermissions) {
                String functionCode = functionPermission.getAnaFunction().getCode();
                Integer mapPermessionSum = functionPermissionMap.get(functionCode);
				if (hasfunction(anaAccount, functionPermission.getAnaFunction())) {
					if (null != mapPermessionSum) {
						int functionPermessionSum = functionPermission.getPermissionsSum();
						int permessionSum = functionPermessionSum | mapPermessionSum;
						functionPermissionMap.put(functionCode, permessionSum);
					} else {
						functionPermissionMap.put(functionCode, functionPermission.getPermissionsSum());
					}
				}
            }
        }
        List<FunctionPermissionDto> functionPermissionDtos = buildFunctionPermissionDtos(functionPermissionMap);

        return RestfulResponse.ofData(functionPermissionDtos);
    }
    
    /**
     * Check if internal/external user have function code
     * @param anaAccount
     * @param anaFunction
     * @return
     */
    private Boolean hasfunction(AnaAccount anaAccount,AnaFunction anaFunction){
    	Boolean internal = anaAccount.getInternal();
    	Boolean external = anaFunction.getExternal();
    	return internal || external;
    }

    /**
     * Build function permission dto
     * 
     * @param maps
     * 			include function permission info
     *  
     * @return
     */
    private List<FunctionPermissionDto> buildFunctionPermissionDtos( Map<String,Integer> maps){
        List<FunctionPermissionDto> dtoList = new ArrayList<>();
        Set<Map.Entry<String,Integer>> entrySet = maps.entrySet();
        for(Map.Entry<String,Integer> item :entrySet){
            FunctionPermissionDto dto = new FunctionPermissionDto();
            dto.setFunctionCode(item.getKey());
            dto.setPermissionSum(item.getValue());
            dtoList.add(dto);
        }
        return dtoList;
    }


}

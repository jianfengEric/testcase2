package com.tng.portal.ana.controller;


import com.tng.portal.ana.constant.SystemMsg;
import com.tng.portal.ana.entity.AnaAccountAccessToken;
import com.tng.portal.ana.vo.AnaAccountAccessTokenDto;
import com.tng.portal.common.exception.BusinessException;
import com.tng.portal.ana.service.AccountService;
import com.tng.portal.common.vo.rest.RestfulResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Zero on 2016/11/15.
 */
@RestController
@RequestMapping("token")
public class CheckAppTokenController {
    @Autowired
    private AccountService accountService;
    
    /**
     * Query current account token info by token, transfer by header
     * 
     * @param request
     * 			include token info
     * 
     * @return
     *
     */
    @ApiOperation(value="Query current account token info by token, transfer by header", notes="")
    @RequestMapping(value = "check",method = RequestMethod.GET)
    public @ResponseBody RestfulResponse<Boolean> checkToken(HttpServletRequest request)  {
        String token = request.getHeader("token");
        RestfulResponse responseBody = new RestfulResponse();
        if(null==token){
            throw new BusinessException(SystemMsg.ServerErrorMsg.CHECK_TOKEN_ERROR.getErrorCode());
        }
        AnaAccountAccessToken anaAccountToken = accountService.getAccountTokenByToken(token);
        if(null == anaAccountToken){
            throw new BusinessException(SystemMsg.ServerErrorMsg.CHECK_TOKEN_ERROR.getErrorCode());
        }
        responseBody.setSuccessStatus();
        responseBody.setData(true);
        return responseBody;
    }


    @RequestMapping(value = "getClientToken",method = RequestMethod.GET)
    public @ResponseBody RestfulResponse<AnaAccountAccessTokenDto> getToken(@RequestParam String token, @RequestParam String code) {
        RestfulResponse<AnaAccountAccessTokenDto> restfulResponse = new RestfulResponse<>();
        AnaAccountAccessTokenDto anaAccountAccessTokenDto =  accountService.getToken(token, code);
        restfulResponse.setData(anaAccountAccessTokenDto);
        restfulResponse.setSuccessStatus();
        return  restfulResponse;
    }

}

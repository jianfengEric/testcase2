package com.tng.portal.ana.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tng.portal.ana.bean.Account;
import com.tng.portal.ana.service.RemoteAccountService;
import com.tng.portal.ana.util.ToolUtil;
import com.tng.portal.ana.vo.AccountDto;
import com.tng.portal.ana.vo.AccountQueryPostDto;
import com.tng.portal.ana.vo.AccountUpdateDto;
import com.tng.portal.ana.vo.AccountUsernameDto;
import com.tng.portal.ana.vo.AddAccountPostDto;
import com.tng.portal.ana.vo.AnaAccountApplicationViewDto;
import com.tng.portal.ana.vo.MamAddAccountPostDto;
import com.tng.portal.common.util.I18nMessge;
import com.tng.portal.common.util.JsonUtils;
import com.tng.portal.common.vo.PageDatas;
import com.tng.portal.common.vo.rest.RestfulResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@RestController
@RequestMapping("remote/account")
public class RemoteAccountController {

    private static final Logger logger = LoggerFactory.getLogger(RemoteAccountController.class);

    @Autowired
    private RemoteAccountService accountService;
    
    @Autowired
    private RemoteAccountService remoteAccountService;

    @Autowired
    private I18nMessge i18nMessge;

    @Autowired
    public HttpServletRequest request;

    /**
     * MAM Call API From Ana To Create Account,And bind MAM Account
     * @param postDto ana account info
     * @return
     */
    @RequestMapping(value = "createAndBindAnaAccount", method = RequestMethod.POST)
    @ResponseBody
    public RestfulResponse<Map<String, List<String>>> createAndBindAnaAccount(@ApiParam(value = "account dto") @RequestBody MamAddAccountPostDto postDto) {
        return accountService.createAndBindAnaAccount(ToolUtil.getRemoteHost(request),postDto);
    }

    /**
     * Get multiple SSO Account result description 
     *
     * @param map KeyYes success,fail,agent,value It's the corresponding applicationCode
     * @return
     **
    private RestfulResponse<Map<String, List<String>>> getCreateAccountResult(Map<String, List<String>> map) {
        Optional<String> successCode = map.get(MamAccountManagerController.KEY_SUCCESS).stream().reduce((i1, i2) -> i1 + "," + i2);
        Optional<String> failCode = map.get(MamAccountManagerController.KEY_FAIL).stream().reduce((i1, i2) -> i1 + "," + i2);
        Optional<String> agentFailCode = map.get(MamAccountManagerController.KEY_AGENT).stream().reduce((i1, i2) -> i1 + "," + i2);

        RestfulResponse<Map<String, List<String>>> response = new RestfulResponse<>();
        StringBuilder enMessage = new StringBuilder();
        StringBuilder zhCNMessage = new StringBuilder();
        StringBuilder zhHKMessage = new StringBuilder();
        if (successCode.isPresent()) {
            String code = SystemMsg.ErrorMsg.SuccessCreateSsoAccountConnect.getCode();
            String appCode = successCode.get();
            enMessage.append(i18nMessge.getEnMessage(code, appCode)).append("\n");
            zhCNMessage.append(i18nMessge.getZhCNMessage(code, appCode)).append("\n");
            zhHKMessage.append(i18nMessge.getZhHKMessage(code, appCode));
            response.setErrorCode("");
            response.setStatus("success");
        }
        if (failCode.isPresent()) {
            String code = SystemMsg.ErrorMsg.ErrorCreateSsoAccountConnect.getCode();
            String appCode = failCode.get();
            enMessage.append(i18nMessge.getEnMessage(code, appCode)).append("\n");
            zhCNMessage.append(i18nMessge.getZhCNMessage(code, appCode)).append("\n");
            zhHKMessage.append(i18nMessge.getZhHKMessage(code, appCode));
            response.setErrorCode(code);
            response.setStatus("fail");
        }
        if (agentFailCode.isPresent()) {
            String code = SystemMsg.ErrorMsg.AgentAccountExist.getCode();
            String appCode = agentFailCode.get();
            enMessage.append(i18nMessge.getEnMessage(code, appCode)).append("\n");
            zhCNMessage.append(i18nMessge.getZhCNMessage(code, appCode)).append("\n");
            zhHKMessage.append(i18nMessge.getZhHKMessage(code, appCode));
            response.setErrorCode(code);
            response.setStatus("fail");
        }
        response.setMessageEN(enMessage.toString());
        response.setMessageZhCN(zhCNMessage.toString());
        response.setMessageZhHK(zhHKMessage.toString());
        response.setData(map);
        return response;
    }
/*
    private List<String> getBindingDataCode(MamAddAccountPostDto dto) {
        List<AnaAccountApplicationDto> bindingData = dto.getBindingData();
        List<String> failList = new ArrayList<>();
        if (bindingData != null && !bindingData.isEmpty()) {
            for (AnaAccountApplicationDto bindingDatum : bindingData) {
                failList.add(bindingDatum.getApplicationCode());
            }
        }
        return failList;
    }

    /**
     * Create account At ANA
     * @param postDto New account info
     * @return
     */
    @RequestMapping(value = "addSsoAccount", method = RequestMethod.POST)
    @ResponseBody
    public RestfulResponse<String> addSsoAccount(@ApiParam(value = "account dto") @RequestBody AddAccountPostDto postDto) {
        return accountService.addSsoAccount(ToolUtil.getRemoteHost(request), postDto);
    }

    /**
     * Query ANA sso account list
     * @param pageNo    current page number
     * @param pageSize  page size
     * @param search    search value
     * @param sortBy    sort by
     * @param isAscending   true--ascend or false--descend
     * @param mid
     * @param accountId
     * @return
     */
    @RequestMapping(value = "sso", method = RequestMethod.GET)
    @ResponseBody
    public RestfulResponse<PageDatas> listSsoAccounts(@ApiParam(value = "current page number") @RequestParam(required = false) Integer pageNo,
                                                      @ApiParam(value = "page size") @RequestParam(required = false) Integer pageSize,
                                                      @ApiParam(value = "search value") @RequestParam(required = false) String search,
                                                      @ApiParam(value = "sort by") @RequestParam(required = false) String sortBy,
                                                      @ApiParam(value = "true--ascend or false--descend") @RequestParam(required = false) Boolean isAscending,
                                                      @ApiParam(value = "mid") @RequestParam(required = false) String mid,
                                                      @ApiParam(value = "accountId") @RequestParam(required = false) String accountId) {
        logger.info("listSsoAccounts() pageNo:{} pageSize:{} search:{} sortBy:{} isAscending:{} mid:{} accountId:{}",
                pageNo, pageSize, search, sortBy, isAscending, mid, accountId);
        return accountService.listAccounts(pageNo, pageSize, search, sortBy, isAscending, mid, accountId);
    }

    /**
     * Query other module account without sso list
     * @param pageNo current page number
     * @param pageSize page size
     * @param search search value
     * @param sortBy sort by
     * @param isAscending true--ascend or false--descend
     * @param mid
     * @param accountId
     * @param applicationCode module name
     * @return
     */
    @RequestMapping(value = "listAccountsWithoutSso", method = RequestMethod.GET)
    @ResponseBody
    public RestfulResponse<PageDatas> listAccountsWithoutSso(@ApiParam(value = "current page number") @RequestParam(required = false) Integer pageNo,
                                                             @ApiParam(value = "page size") @RequestParam(required = false) Integer pageSize,
                                                             @ApiParam(value = "search value") @RequestParam(required = false) String search,
                                                             @ApiParam(value = "sort by") @RequestParam(required = false) String sortBy,
                                                             @ApiParam(value = "true--ascend or false--descend") @RequestParam(required = false) Boolean isAscending,
                                                             @ApiParam(value = "mid") @RequestParam(required = false) String mid,
                                                             @ApiParam(value = "accountId") @RequestParam(required = false) String accountId,
                                                             @ApiParam(value = "applicationCode") @RequestParam(required = true) String applicationCode) {
       return accountService.listAccountsWithoutSso(pageNo, pageSize, search, sortBy, isAscending, mid, accountId, applicationCode);
    }

    /**
     *  Find Account(user's authentication info) Info By binding account id
     * @param id binding account id
     * @return
     */
    @RequestMapping(value = "getAccountInfoByBindAccountId", method = RequestMethod.GET)
    @ResponseBody
    public RestfulResponse<Account> getAccountInfoByBindAccountId(@RequestParam(required = true) String id) {
        return accountService.getAccountInfoByBindAccountId(id);
    }
    /**
     * Find Account(user's authentication info) Info By Account
     * @param account account
     * @return
     */
    @RequestMapping(value = "getAccountInfoByAccount", method = RequestMethod.GET)
    @ResponseBody
    public RestfulResponse<Account> getAccountInfoByAccount(@ApiParam(value = "account") @RequestParam(required = true) String account) {
        return accountService.getAccountInfoByAccount(account);
    }

    /**
     * query account list
     * @param postDto accoount param
     * @return
     */
    @RequestMapping(value = "queryAccountList", method = RequestMethod.POST)
    @ResponseBody
    public RestfulResponse<List<AccountDto>> queryAccountList(@RequestBody AccountQueryPostDto postDto) {
        return accountService.queryAccountList(postDto);
    }

    /**
     * Create ana account and bind other module account
     *
     * @param request HttpServletRequest
     * @param postDto New account info
     * @return
     **/
    @ApiOperation(value = "Add ana account info and bind other module account", notes = "")
    @RequestMapping(value = "addAnaBind", method = RequestMethod.POST)
    public @ResponseBody
    RestfulResponse<String> addBindingAccountSso(HttpServletRequest request,
                                                 @ApiParam(value = "account dto") @RequestBody MamAddAccountPostDto postDto) {

        return accountService.merchantManagementBindingSso(ToolUtil.getRemoteHost(request), postDto);
    }

    /**
     * Get Binding Account list
     * @param id account id
     * @return
     */
    @RequestMapping(value = "getBindAccounts", method = RequestMethod.GET)
    @ResponseBody
    public RestfulResponse<List<AnaAccountApplicationViewDto>> getBindAccounts(@RequestParam("id") String id) {
        return accountService.getBindAccounts(id);
    }

    /**
     * Update Account Info
     * @param updateDto account info
     * @return
     */
    @RequestMapping(value = "updateLocalAccountInfo",method = RequestMethod.PUT)
    @ResponseBody
    public RestfulResponse<String> updateLocalAccountInfo(@ApiParam(value="account dto")@RequestBody AccountUpdateDto updateDto)  {
        logger.info("updateLocalAccountInfo==>"+ JsonUtils.toJSon(updateDto));
        return accountService.updateLocalAccountInfo(ToolUtil.getRemoteHost(request), updateDto);
    }

    /**
     * Reset Password
     * @param id account id
     * @param password new password
     * @param code application code
     * @return
     */
    @RequestMapping(value = "resetPassword",method = RequestMethod.GET)
    @ResponseBody
    public  RestfulResponse resetPassword(@ApiParam(value="account id")@RequestParam("id") String id,
                                                       @RequestParam(value = "password", required = false) String password,
                                                       @RequestParam(value = "code", required = false)String code)  {
        return accountService.resetPassword(id, password, code);
    }

    /**
     * Resend Client Email
     * @param accountId account id
     * @param code application id
     * @return
     */
    @RequestMapping(value = "resendClientEmail",method = RequestMethod.GET)
    @ResponseBody
    public RestfulResponse resendClientEmail(@ApiParam(value="accountId")@RequestParam("accountId") String accountId,
                                                     @RequestParam(value = "code", required = false)String code)  {
        return accountService.resendClientEmail(accountId, code);
    }

    /**
     * Update Client Account Info
     * @param updateDto account info
     * @return
     */
    @RequestMapping(value = "updateClientAccountInfo",method = RequestMethod.PUT)
    @ResponseBody
    public RestfulResponse<String> updateClientAccountInfo(@ApiParam(value="account dto")@RequestBody AccountUpdateDto updateDto)  {
        return accountService.updateClientAccountInfo(ToolUtil.getRemoteHost(request), updateDto);
    }

    /**
     * Update Client Account Status
     * @param id account id
     * @param status account status
     * @param code application code
     * @return
     */
    @RequestMapping(value = "updateClientStatus",method = RequestMethod.GET)
    @ResponseBody
    public RestfulResponse<String> updateClientStatus(@ApiParam(value="account id", required = false)@RequestParam String id,
                                                              @ApiParam(value="status", required = true)@RequestParam String status,
                                                              @RequestParam(value = "code", required = false) String code)  {
        return accountService.updateClientStatus(ToolUtil.getRemoteHost(request), id, status, code);
    }

    /**
     * (In MAM Moudles)Create ANA Account And Bind
     * @param postDto New account info
     * @return
     */
    @RequestMapping(value = "bindAccount", method = RequestMethod.POST)
    @ResponseBody
    public RestfulResponse bindAccount(@ApiParam(value="account dto") @RequestBody MamAddAccountPostDto postDto)  {
        return accountService.bindAccount(postDto);
    }

    /**
     * Find sso(ana) account by bind account(client) id
     * @param id the client account id
     * @return
     */
    @RequestMapping(value = "getSsoAccount",method = RequestMethod.GET)
    @ResponseBody
    public RestfulResponse<String> getSsoAccount(@ApiParam(value="account id", required = true)@RequestParam String id) {
        return accountService.getSsoAccount(id);
    }

    /**
     * When Login In ANA For The First Time,Active Local Moudles Account(Update Local Account Status)
     * @param id account
     * @return
     */
    @RequestMapping(value = "active", method = RequestMethod.GET)
    @ResponseBody
    public RestfulResponse active(@ApiParam(value="account id", required = true)@RequestParam String id)  {
        return accountService.active(id);
    }

    /**
     * Query account info by username or id
     *
     * @param accountDto
     * @return
     */
    @ApiOperation(value = "Query current user's authentication info by username", notes = "")
    @RequestMapping(value = "getAccountInfoByNameOrId", method = RequestMethod.POST)
    public @ResponseBody
    RestfulResponse<Account> getAccountInfoByNameOrId(@ApiParam(value = "username dto") @RequestBody(required = true) AccountUsernameDto accountDto) {
        return accountService.getAccountInfoByNameOrId(accountDto);
    }

    @RequestMapping(value = "inactive", method = RequestMethod.POST)
    @ResponseBody
    public RestfulResponse inactive(String mid) {
        return accountService.inactive(mid);
    }


    /**
     * Query ANA sso account list
     * @param accountId
     * @return
     */
    @RequestMapping(value = "client", method = RequestMethod.GET)
    @ResponseBody
    public List<AccountDto> listClientAccounts(@RequestParam String accountId) {
        logger.info("listClientAccounts()  accountId:{}", accountId);
        return accountService.getClientAccounts(accountId);
    }
    
    @GetMapping("find-binding-id")
    @ResponseBody
    public RestfulResponse<String> findBindingId(String bindingAccountId,String srcApplicationCode,String trgApplicationCode){
    	String res = remoteAccountService.findBindingId(bindingAccountId, srcApplicationCode, trgApplicationCode);
        return RestfulResponse.ofData(res);
    }
}

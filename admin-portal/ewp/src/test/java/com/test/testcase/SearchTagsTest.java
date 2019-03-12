package com.test.testcase;

import com.gea.portal.ewp.Application;
import com.gea.portal.ewp.service.EwalletParticipantService;
import com.gea.portal.ewp.service.MpCallerService;
import com.gea.portal.ewp.testUtils.CsvUtils;
import com.tng.portal.ana.authentication.AnaPrincipalAuthenticationToken;
import com.tng.portal.ana.bean.UserDetails;
import com.tng.portal.ana.service.TokenService;
import com.tng.portal.ana.service.UserService;
import com.tng.portal.common.dto.ewp.*;
import com.tng.portal.common.dto.mp.MoneyPoolListDto;
import com.tng.portal.common.enumeration.ApprovalType;
import com.tng.portal.common.enumeration.Instance;
import com.tng.portal.common.enumeration.ParticipantStatus;
import com.tng.portal.common.enumeration.RequestApprovalStatus;
import com.tng.portal.common.vo.PageDatas;
import com.tng.portal.common.vo.rest.RestfulResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@SpringBootTest(classes = { Application.class })
public class SearchTagsTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private EwalletParticipantService ewalletParticipantService;

    @Autowired
    private MpCallerService mpCallerService;


    @Qualifier("tokenServiceImpl")
    @Autowired
    private TokenService tokenServiceImpl;

    @Qualifier("anaUserService")
    @Autowired
    private UserService userService;

    @DataProvider(name="pageData")
    public Iterator<Object[]> pageData() throws IOException {
        return (Iterator<Object[]>)new CsvUtils("pageData.csv");
    }

    @DataProvider(name="participantData")
    public Iterator<Object[]> participantData() throws IOException {
        return (Iterator<Object[]>)new CsvUtils("participantData.csv");
    }

    @Test(dataProvider="pageData")
    public void testListParticipant(Map<String, String> data) throws IOException {
        String token=data.get("token");
        UserDetails userDetails = userService.getUserDetailByToken(token);
        if(null!=userDetails){
            AnaPrincipalAuthenticationToken authentication = new AnaPrincipalAuthenticationToken(userDetails,token,"");
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        RestfulResponse<PageDatas<ParticipantDto>> restResponse = new RestfulResponse<>();
        Integer pageNo = Integer.valueOf(data.get("pageNo"));
        Integer pageSize = 10;
        String instance=data.get("instance");
        String isAscending=data.get("isAscending");
        PageDatas<ParticipantDto> pageData = ewalletParticipantService.listParticipant(pageNo, pageSize, data.get("geaParticipantRefId"), data.get("participantName"), data.get("participantStatus"),data.get("approvalStatus"), Instance.valueOf(instance),data.get("sortBy"),Boolean.valueOf(isAscending));
        restResponse.setData(pageData);
        restResponse.setSuccessStatus();
        Assert.assertNotNull(restResponse, "response");
//        再Json化成对象
        Assert.assertNotNull(restResponse, "response");
    }

    @Test(dataProvider="pageData")
    public void testGetServiceAssignment(Map<String, String> data) throws IOException {
        String instance=data.get("instance");
        RestfulResponse<ServiceAssignmentDto> restResponse= ewalletParticipantService.getServiceAssignment(data.get("participantId"), Instance.valueOf(instance) == null ? Instance.PRE_PROD : Instance.valueOf(instance));
        Assert.assertNotNull(restResponse, "response");
//        再Json化成对象
        Assert.assertNotNull(restResponse, "response");
    }

    @Test(dataProvider="participantData")
    public void testGetParticipantByNameOrIdList(Map<String, String> data) throws IOException {
        RestfulResponse<List<ParticipantDto>> restfulResponse = new RestfulResponse<>();
        List<ParticipantDto> participantDtoList = ewalletParticipantService.getParticipantByNameOrIdList(data.get("geaParticipantRefId"), data.get("participantName"),data.get("serviceId"), Instance.valueOf(data.get("instance")));
        restfulResponse.setData(participantDtoList);
        Assert.assertNotNull(restfulResponse, "response");
//        再Json化成对象
        Assert.assertNotNull(restfulResponse, "response");
    }

    @Test(dataProvider="pageData")
    public void testCheckEdit(Map<String, String> data) throws IOException {
        Integer restfulResponse = ewalletParticipantService.checkEdit(data.get("participantId"), Instance.valueOf(data.get("instance")),data.get("type"));
        Assert.assertNotNull(restfulResponse, "response");
//        再Json化成对象
        Assert.assertNotNull(restfulResponse, "response");
    }

    @Test(dataProvider="pageData")
    public void testGetAllMoneyPoolList(Map<String, String> data) throws IOException {
        List<MoneyPoolListDto> list =mpCallerService.callGetAllMoneyPoolList(data.get("geaParticipantRefId"), Instance.valueOf(data.get("instance"))).getData();
        Assert.assertNotNull(list, "response");
//        再Json化成对象
        Assert.assertNotNull(list, "response");
    }

    @Test(dataProvider="pageData")
    public void testGetFullCompanyInfomation(Map<String, String> data) throws IOException {
        RestfulResponse<FullCompanyInfoDto> response=ewalletParticipantService.getFullCompanyInfomation(data.get("participantId"), Instance.valueOf(data.get("instance")) == null ? Instance.PRE_PROD : Instance.valueOf(data.get("instance")));
        Assert.assertNotNull(response, "response");
//        再Json化成对象
        Assert.assertNotNull(response, "response");
    }

    @Test(dataProvider="pageData")
    public void testGetGatewaySetting(Map<String, String> data) throws IOException {
        RestfulResponse<GatewaySettingDto> response=ewalletParticipantService.getGatewaySetting(data.get("participantId"), Instance.valueOf(data.get("instance"))  == null ? Instance.PRE_PROD : Instance.valueOf(data.get("instance")) );
        Assert.assertNotNull(response, "response");
//        再Json化成对象
        Assert.assertNotNull(response, "response");
    }

    @Test(dataProvider="pageData")
    public void testGetServiceSetting(Map<String, String> data) throws IOException {
        RestfulResponse<ServiceSettingRequestDto> response=ewalletParticipantService.getServiceSetting(data.get("participantId"), Instance.valueOf(data.get("instance")) == null ? Instance.PRE_PROD : Instance.valueOf(data.get("instance")));
        Assert.assertNotNull(response, "response");
//        再Json化成对象
        Assert.assertNotNull(response, "response");
    }

    @Test(dataProvider="pageData")
    public void testGetCutOffTime(Map<String, String> data) throws IOException {
        RestfulResponse<CutOffTimeDto> response=ewalletParticipantService.getCutOffTime(data.get("participantId"), Instance.valueOf(data.get("instance")) == null ? Instance.PRE_PROD : Instance.valueOf(data.get("instance")));
        Assert.assertNotNull(response, "response");
//        再Json化成对象
        Assert.assertNotNull(response, "response");
    }

    @Test(description = "get-Participant-Status-list")
    public void testGetParticipantStatusList() throws IOException {
        RestfulResponse<List<String>> restfulResponse = new RestfulResponse<>();
        List<String> list = new ArrayList<>();
        ParticipantStatus[] s = ParticipantStatus.values();
        for(int i = 0; i< s.length; i++){
            if(!s[i].equals(ParticipantStatus.PENDING_FOR_PROCESS) && !s[i].equals(ParticipantStatus.REJECTED)){
                list.add(s[i].name());
            }
        }
        restfulResponse.setData(list);
        Assert.assertNotNull(restfulResponse, "response");
//        再Json化成对象
        Assert.assertNotNull(restfulResponse, "response");
    }

    @Test(dataProvider="pageData")
    public void testGetApprovalStatusList(Map<String, String> data) throws IOException {
        RestfulResponse<List<String>> restfulResponse = new RestfulResponse<>();
        List<String> list = Stream.of(RequestApprovalStatus.values()).map(RequestApprovalStatus::getListView).distinct().collect(Collectors.toList());
        if(Instance.valueOf(data.get("instance")) == Instance.PROD){
            list.add(RequestApprovalStatus.ST);
        }
        restfulResponse.setData(list);
        Assert.assertNotNull(restfulResponse, "response");
//        再Json化成对象
        Assert.assertNotNull(restfulResponse, "response");
    }

    @Test(description = "gen-api-gateway-key")
    public void testGenApiGatewayKey() throws IOException {
        RestfulResponse<String> restfulResponse = new RestfulResponse<>();
        String key = UUID.randomUUID().toString();
        restfulResponse.setData(key);
        Assert.assertNotNull(restfulResponse, "response");
//        再Json化成对象
        Assert.assertNotNull(restfulResponse, "response");
    }


    @Test(dataProvider="pageData")
    public void testIsCompleteData(Map<String, String> data) throws IOException {
        Map<ApprovalType,Boolean> map =  ewalletParticipantService.isCompleteData(Long.valueOf(data.get("participantId")), Instance.valueOf(data.get("instance")),null);
        Assert.assertNotNull(map, "response");
//        再Json化成对象
        Assert.assertNotNull(map, "response");
    }


}

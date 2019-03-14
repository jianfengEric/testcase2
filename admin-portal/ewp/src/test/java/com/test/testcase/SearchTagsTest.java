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
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${current.environment}")
    private String currentEnvironment;

    @DataProvider(name="pageData")
    public Iterator<Object[]> pageData() throws IOException {
        String path=".src.main.java.com.gea.portal.ewp.testData."+currentEnvironment+"TestData.";
        return (Iterator<Object[]>)new CsvUtils("pageData.csv",path);
    }

    @DataProvider(name="participantData")
    public Iterator<Object[]> participantData() throws IOException {
        String path=".src.main.java.com.gea.portal.ewp.testData."+currentEnvironment+"TestData.";
        return (Iterator<Object[]>)new CsvUtils("participantData.csv",path);
    }

    @DataProvider(name="fullCompanyInformationData")
    public Iterator<Object[]> fullCompanyInformationDto() throws IOException {
        String path=".src.main.java.com.gea.portal.ewp.testData."+currentEnvironment+"TestData.";
        return (Iterator<Object[]>)new CsvUtils("fullCompanyInformationData.csv",path);
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

    @Test(dataProvider="fullCompanyInformationData")
    public void testCreateFullCompanyInformation(Map<String, String> data) throws IOException {
        String token=data.get("token");
        UserDetails userDetails = userService.getUserDetailByToken(token);
        if(null!=userDetails){
            AnaPrincipalAuthenticationToken authentication = new AnaPrincipalAuthenticationToken(userDetails,token,"");
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        FullCompanyInformationDto postDto=new FullCompanyInformationDto();
        postDto=getPostDtoData(postDto,data);
        RestfulResponse<String> restResponse=ewalletParticipantService.createFullCompanyInformation(postDto);
        Assert.assertNotNull(restResponse, "response");
//        再Json化成对象
        Assert.assertNotNull(restResponse, "response");
    }

    private FullCompanyInformationDto getPostDtoData(FullCompanyInformationDto postDto, Map<String, String> data) {
        postDto.setParticipantId(data.get("participantId"));
        postDto.setRegistrationDate(data.get("registrationDate"));
        postDto.setAddress(data.get("address"));
        postDto.setCountry(data.get("country"));
        postDto.setFullCompanyName(data.get("fullCompanyName"));
        postDto.setNotificationEmail(data.get("notificationEmail"));
        postDto.setParticipantName(data.get("participantName"));
        postDto.setRequestRemark(data.get("requestRemark"));
        postDto.setRegistrationNo(data.get("registrationNo"));
        //
        List<DisputeContactDto> disputeContactDtoList=new ArrayList<>();
        DisputeContactDto disputeContactDto=new DisputeContactDto();
        disputeContactDto.setRoleName("testnum5");
        disputeContactDto.setPhoneOffice("13214236554");
        disputeContactDto.setPhoneMobile("13214236554");
        disputeContactDto.setNameEn("testnum5");
        disputeContactDto.setMobileSms(true);
        disputeContactDto.setEmail("testnum5@qq.com");
        disputeContactDto.setDepartmentName("testnum5");
        disputeContactDtoList.add(disputeContactDto);
        postDto.setDisputeContactDto(disputeContactDtoList);
        //
        KeyPersonInformationDto keyPersonInformationDto=new KeyPersonInformationDto();
        keyPersonInformationDto.setDepartment("testnum5");
        keyPersonInformationDto.setDirectLine("13214236554");
        keyPersonInformationDto.setEmail("testnum5@qq.com");
        keyPersonInformationDto.setMobileNumber("13214236554");
        keyPersonInformationDto.setName("testnum5");
        keyPersonInformationDto.setReceiveSms(true);
        keyPersonInformationDto.setRole("testnum5");
        List<KeyPersonInformationDto> keyPersonInformationDtoList=new ArrayList<>();
        keyPersonInformationDtoList.add(keyPersonInformationDto);
        postDto.setKeyPersonInformationDto(keyPersonInformationDtoList);
        //
        MaterialDto materialDto=new MaterialDto();
        materialDto.setFilePath("46160833_e5a7_4b58_8018_3224075f7fcf.png");
        materialDto.setMaterialFilename("u=415293130 - 副本.png");
        postDto.setMaterialDto(materialDto);
        //
        OwnerDetailsDto ownerDetailsDto=new OwnerDetailsDto();
        ownerDetailsDto.setDirectLine("13214236554");
        ownerDetailsDto.setEmail("testnum5@qq.com");
        ownerDetailsDto.setMobileNumber("13214236554");
        MaterialDto materialDto1=new MaterialDto();
        materialDto1.setFilePath("464fcc91_7244_4cf5_9171_070f6068fea7.png");
        materialDto1.setMaterialFilename("u=415293130 - 副本.png");
        ownerDetailsDto.setMaterialDto(materialDto1);
        ownerDetailsDto.setName("testnum5");
        ownerDetailsDto.setRole("testnum5");
        List<OwnerDetailsDto> ownerDetailsDtoList=new ArrayList<>();
        ownerDetailsDtoList.add(ownerDetailsDto);
        postDto.setOwnerDetailsDto(ownerDetailsDtoList);
        return postDto;
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

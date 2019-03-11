package com.test.testcase;

import com.gea.portal.ewp.Application;
import com.gea.portal.ewp.service.EwalletParticipantService;
import com.gea.portal.ewp.testUtils.CsvUtils;
import com.tng.portal.common.dto.ewp.ParticipantDto;
import com.tng.portal.common.dto.ewp.ServiceAssignmentDto;
import com.tng.portal.common.enumeration.Instance;
import com.tng.portal.common.vo.PageDatas;
import com.tng.portal.common.vo.rest.RestfulResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@SpringBootTest(classes = { Application.class })
public class SearchTagsTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private EwalletParticipantService ewalletParticipantService;

    /*@BeforeSuite
    public void beforeSuite() throws IOException {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("env.properties");
        properties = new Properties();
        properties.load(stream);
        String host = properties.getProperty("douban.host");
        stream = this.getClass().getClassLoader().getResourceAsStream("parameters/search/SearchTagsParams.properties");
        properties.load(stream);
        stream = this.getClass().getClassLoader().getResourceAsStream("");
        stream.close();
    }*/

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
}

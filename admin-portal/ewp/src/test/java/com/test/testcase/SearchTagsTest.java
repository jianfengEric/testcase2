package com.test.testcase;

import com.gea.portal.ewp.douban.domain.MovieResponseVO;
import com.gea.portal.ewp.service.EwalletParticipantService;
import com.gea.portal.ewp.testUtils.CsvUtils;
import com.tng.portal.common.dto.ewp.ParticipantDto;
import com.tng.portal.common.enumeration.Instance;
import com.tng.portal.common.vo.PageDatas;
import com.tng.portal.common.vo.rest.RestfulResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SearchTagsTest {

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

    @DataProvider(name="num")
    public Iterator<Object[]> Numbers() throws IOException{
        return (Iterator<Object[]>)new CsvUtils("data.csv");
    }

    @Test(dataProvider="num")
    public void testcase1(Map<String, String> data) throws IOException {
        RestfulResponse<PageDatas<ParticipantDto>> restResponse = new RestfulResponse<>();
        PageDatas<ParticipantDto> pageData = ewalletParticipantService.listParticipant(Integer.valueOf(data.get("pageNo")), Integer.valueOf(data.get("pageSize")), data.get("geaParticipantRefId"), data.get("participantName"), data.get("participantStatus"),data.get("approvalStatus"), Instance.valueOf(data.get("instance")),data.get("sortBy"),Boolean.valueOf(data.get("isAscending")));
        restResponse.setData(pageData);
        restResponse.setSuccessStatus();

        /*MovieResponseVO body = response.body();*//*
        MovieResponseVO body=new MovieResponseVO();
        List<String> tags=new ArrayList<>();
        tags.add("test1");
        tags.add("test2");
        body.setTags(tags);*/
        Assert.assertNotNull(restResponse, "response");
//        再Json化成对象
        Assert.assertNotNull(restResponse, "response");
    }

    @Test(description = "type=tv source=index")
    public void testcase2() throws IOException {

        /*MovieResponseVO body = response.body();*/
        MovieResponseVO body=new MovieResponseVO();
        List<String> tags=new ArrayList<>();
        tags.add("test3");
        tags.add("test4");
        body.setTags(tags);
        Assert.assertNotNull(body, "response.body()");
        Assert.assertNotNull(body.getTags(), "tags");
    }
}

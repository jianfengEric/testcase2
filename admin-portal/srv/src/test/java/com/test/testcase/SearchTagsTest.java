package com.test.testcase;

import com.tng.portal.common.vo.rest.RestfulResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchTagsTest {

   /* @BeforeSuite
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

    @Test(description = "test1-get-service-base-markup")
    public void testcase1() throws IOException {
        RestfulResponse body=new RestfulResponse();
        List<String> tags=new ArrayList<>();
        tags.add("test1");
        tags.add("test2");
        Assert.assertNotNull(body, "response");
//        再Json化成对象
        Assert.assertNotNull(body, "response");
    }

    @Test(description = "test2-check-edit")
    public void testcase2() throws IOException {
        RestfulResponse body=new RestfulResponse();
        List<String> tags=new ArrayList<>();
        tags.add("test1");
        tags.add("test2");
        Assert.assertNotNull(body, "response");
//        再Json化成对象
        Assert.assertNotNull(body, "response");
    }
}

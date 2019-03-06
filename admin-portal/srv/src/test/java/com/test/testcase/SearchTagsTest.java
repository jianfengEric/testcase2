package com.test.testcase;

import com.alibaba.fastjson.JSONObject;
import com.gea.portal.srv.service.SrvBaseService;
import com.gea.portal.srv.tools.JsonSchemaUtils;
import com.tng.portal.common.enumeration.Instance;
import com.tng.portal.common.vo.PageDatas;
import com.tng.portal.common.vo.rest.RestfulResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Properties;

public class SearchTagsTest {
    private static SrvBaseService baseService;
    private static Properties properties;
    private static String SCHEMA_PATH = "parameters/search/schema/SearchTagsMovie.json";

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
        RestfulResponse<PageDatas> response=baseService.getServiceBaseMarkups(1, 10,null,true, Instance.PROD);
        Assert.assertNotNull(response, "response");
//        响应返回内容想通过schema标准校验
        JsonSchemaUtils.assertResponseJsonSchema(SCHEMA_PATH, JSONObject.toJSONString(response));
//        再Json化成对象
        Assert.assertNotNull(response, "page");
    }

    @Test(description = "test2-check-edit")
    public void testcase2() throws IOException {
        Boolean response=baseService.checkEdit(Instance.PROD);
        Assert.assertNotNull(response, "response.body()");
        JsonSchemaUtils.assertResponseJsonSchema(SCHEMA_PATH, JSONObject.toJSONString(response));
        Assert.assertNotNull(response, "response");
    }
}

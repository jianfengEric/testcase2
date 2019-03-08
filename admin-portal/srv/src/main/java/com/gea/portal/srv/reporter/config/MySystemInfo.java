package com.gea.portal.srv.reporter.config;

import com.vimalselvam.testng.SystemInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Auther: jx
 * @Date: 2018/6/7 10:54
 * @Description:
 */
public class MySystemInfo implements SystemInfo {
    @Override
    public Map<String, String> getSystemInfo() {
        /*InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("env.properties");*/
        Properties properties = new Properties();
        Map<String, String> systemInfo = new HashMap<>();
        try {
            /*properties.load(inputStream);*/
            systemInfo.put("environment", "Environment");
            systemInfo.put("测试人员", "jxq");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return systemInfo;
    }
}

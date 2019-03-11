package com.gea.portal.srv.tools;

import com.alibaba.fastjson.JSONObject;
import com.tng.portal.common.vo.rest.RestResponse;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;


public class RespVoConverterFactory extends Converter.Factory {
    public static RespVoConverterFactory create() {
        return new RespVoConverterFactory();
    }

    private RespVoConverterFactory() {

    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new RespVoConverter();
    }

    public class RespVoConverter implements Converter<ResponseBody, RestResponse> {
        @Override
        public RestResponse convert(ResponseBody value) throws IOException {
            String temp = value.string();
            return JSONObject.parseObject(temp, RestResponse.class);
        }
    }

}

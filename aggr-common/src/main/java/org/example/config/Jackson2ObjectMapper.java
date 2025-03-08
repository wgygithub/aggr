package org.example.config;


import com.fasterxml.jackson.databind.JavaType;
import kong.unirest.GenericType;
import kong.unirest.ObjectMapper;
import org.example.resp.Result;
import org.example.util.JacksonUtil;

import java.util.List;

public class Jackson2ObjectMapper implements ObjectMapper {
    @Override
    public <T> T readValue(String value, Class<T> valueType) {
        return JacksonUtil.fromJson(value, valueType);// 反序列化
    }

    @Override
    public <T> T readValue(String value, GenericType<T> genericType) {
        JavaType javaType = JacksonUtil.getObjectMapper().getTypeFactory()
                .constructType(genericType.getType());
        return JacksonUtil.fromJson(value, javaType);
    }

    @Override
    public String writeValue(Object value) {
        return JacksonUtil.toJson(value); // 序列化
    }

    public static void main(String[] args) {
        Jackson2ObjectMapper mapper = new Jackson2ObjectMapper();
        Result<List<String>> result = new Result<>();
        result.setData(List.of("1", "2"));
        String str = mapper.writeValue(result);
        Result<List<String>> listResult = mapper.readValue(str, new GenericType<>() {
        });
        System.out.println(listResult);
    }

}

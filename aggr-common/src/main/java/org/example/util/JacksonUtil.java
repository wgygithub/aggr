package org.example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.example.exceptions.JacksonJsonException;

import java.io.IOException;

@Slf4j
public class JacksonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 配置 ObjectMapper
        // 忽略在 JSON 中存在但 Java 对象中不存在的属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 允许序列化空对象
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 注册 JavaTimeModule 以支持 Java 8 的日期时间类型
        objectMapper.registerModule(new JavaTimeModule());
        // 禁用默认的日期时间序列化格式
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // 设置自定义日期格式
        objectMapper.findAndRegisterModules();
        objectMapper.setDateFormat(new ThreadSafeDateFormat("yy-MM-dd HH:mm:ss")); // 自定义线程安全的日期格式
    }

    private JacksonUtil() {
    }

    public final static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /**
     * 将 Java 对象转换为 JSON 字符串
     *
     * @param obj 要转换的 Java 对象
     * @return JSON 字符串，如果转换失败则抛出 JacksonJsonException
     */
    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Failed to convert object to JSON", e);
            throw new JacksonJsonException("Failed to convert object to JSON");
        }
    }

    /**
     * 将 JSON 字符串转换为指定类型的 Java 对象
     *
     * @param json  JSON 字符串
     * @param clazz 目标 Java 对象的类型
     * @param <T>   泛型类型
     * @return 转换后的 Java 对象，如果转换失败则抛出 JacksonJsonException
     */
    public static <T> T fromJson(String json, Class<T> clazz) throws JacksonJsonException {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            log.error("Failed to convert JSON to object", e);
            throw new JacksonJsonException("Failed to convert JSON to object");
        }
    }

    public static <T> T fromJson(String json, JavaType javaType) throws JacksonJsonException {
        try {
            return objectMapper.readValue(json, javaType);
        } catch (IOException e) {
            log.error("Failed to convert JSON to object", e);
            throw new JacksonJsonException("Failed to convert JSON to object");
        }
    }

    public static JsonNode readTree(String responseBody) {
        try {
            return objectMapper.readTree(responseBody);
        } catch (IOException e) {
            log.error("Failed to convert JSON to object", e);
            throw new JacksonJsonException("Failed to convert JSON to object");
        }
    }


    // 自定义线程安全的日期格式类
    private static class ThreadSafeDateFormat extends java.text.SimpleDateFormat {
        private static final long serialVersionUID = 1L;

        public ThreadSafeDateFormat(String pattern) {
            super(pattern);
        }

        @Override
        public StringBuffer format(java.util.Date date, StringBuffer toAppendTo, java.text.FieldPosition pos) {
            synchronized (this) {
                return super.format(date, toAppendTo, pos);
            }
        }

        @Override
        public java.util.Date parse(String source, java.text.ParsePosition pos) {
            synchronized (this) {
                return super.parse(source, pos);
            }
        }
    }
}

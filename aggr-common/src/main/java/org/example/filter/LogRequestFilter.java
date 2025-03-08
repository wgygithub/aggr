package org.example.filter;


import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.example.enums.ResultType;
import org.example.exceptions.JacksonJsonException;
import org.example.util.IpUtils;
import org.example.util.JacksonUtil;
import org.example.util.TraceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Supplier;

/**
 * 记录请求日志
 */
@Slf4j
public class LogRequestFilter extends OncePerRequestFilter implements Ordered {

    private static final Logger interfaceLog = LoggerFactory.getLogger("interface");

    private static final Set<String> IGNORE_PATH = new HashSet<>();

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 8;
    }

    public void setIgnorePath(Set<String> ignorePath) {
        IGNORE_PATH.addAll(ignorePath);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getMethod().equals(HttpMethod.HEAD.toString())
                || request.getMethod().equals(HttpMethod.OPTIONS.toString())) {
            return;
        }
        if (IGNORE_PATH.contains(request.getRequestURI())) {
            return;
        }
        if (isMultipartRequest(request)) {
            return;
        }
        Date start = new Date();
        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }
        if (!(response instanceof ContentCachingResponseWrapper)) {
            response = new ContentCachingResponseWrapper(response);
        }
        String traceId = request.getHeader("traceId");
        try {
            traceId = TraceUtil.getTraceId(traceId);
            filterChain.doFilter(request, response);
        } finally {
            String requestBody = null;
            ContentCachingRequestWrapper requestWrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
            if (requestWrapper != null) {
                String resp = new String(requestWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
                if (StrUtil.isNotBlank(resp)) {
                    requestBody = resp;
                }
            }
            String responseBody = null;
            ContentCachingResponseWrapper responseWrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
            if (responseWrapper != null && StrUtil.containsIgnoreCase(responseWrapper.getContentType(), "json")) {
                String resp = new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
                if (StrUtil.isNotBlank(resp)) {
                    responseBody = resp;
                }
            }
            Map<String, String> headers = new HashMap<>();
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                headers.put(name, request.getHeader(name));
            }
            long cost = DateUtil.between(start, new Date(), DateUnit.MS);
            Integer code = extractValueFromResponse(responseBody, "code", ResultType.FAIL::getCode);
            String msg = "";
            String successFlag = code == ResultType.SUCCESS.getCode() ? "T" : "F";
            if (successFlag.equalsIgnoreCase("F")) {
                msg = extractValueFromResponse(responseBody, "message", ResultType.FAIL::getMsg);
            }
            String format = """
                    Timestamp      : {}
                    SuccessFlag    : {}
                    StatsCode      : {}
                    MSG            : {}
                    TraceId        : {}
                    URL            : {}
                    HTTP Headers   : {}
                    HTTP Method    : {}
                    IP             : {}
                    Request        : {}
                    Response       : {}
                    Time-Consuming : {} ms
                    ======================================================================================""";
            interfaceLog.info(format, DateUtil.formatDateTime(start), successFlag, code, msg, traceId,
                    request.getRequestURL().toString(), headers, request.getMethod(),
                    IpUtils.getIpAddr(request), JacksonUtil.toJson(new Param(request.getQueryString(),
                            requestBody)), responseBody, cost);
            ContentCachingResponseWrapper resp = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
            Objects.requireNonNull(resp).copyBodyToResponse();
        }
    }

    @Override
    public void destroy() {
        TraceUtil.clear();
    }

    // 解析响应JSON中的code字段
    private <T> T extractValueFromResponse(String responseBody, String valueKey, Supplier<T> defaultValueSupplier) {
        try {
            JsonNode jsonNode = JacksonUtil.readTree(responseBody);
            if (jsonNode != null && jsonNode.has(valueKey)) {
                ObjectMapper objectMapper = JacksonUtil.getObjectMapper();
                return objectMapper.convertValue(jsonNode.get(valueKey), objectMapper.getTypeFactory().constructType(defaultValueSupplier.get().getClass()));
            } else {
                // 如果JSON中没有code字段，则返回默认值
                log.warn("Response body does not contain the key: {}", valueKey);
                return defaultValueSupplier.get();
            }
        } catch (JacksonJsonException jacksonJsonException) {
            logger.error("Failed to parse response body due to JSON processing error", jacksonJsonException);
            return defaultValueSupplier.get(); // 解析失败时的默认值
        }
    }

    @AllArgsConstructor
    @Data
    public static class Param {

        private String query;
        private String body;
    }

    private boolean isMultipartRequest(HttpServletRequest request) {
        String contentType = request.getContentType();
        return contentType != null && contentType.startsWith("multipart/form-data");
    }
}
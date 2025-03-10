package org.example.interceptor;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.url.UrlBuilder;
import kong.unirest.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.example.exceptions.RemoteCallException;
import org.example.util.TraceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class UnirestLoggingInterceptor implements Interceptor {
    private static final Logger apiLog = LoggerFactory.getLogger("api");

    private static final Set<String> ignoreContentType = new HashSet<>() {{
        add(ContentType.IMAGE_BMP.getMimeType());
        add(ContentType.IMAGE_GIF.getMimeType());
        add(ContentType.IMAGE_PNG.getMimeType());
        add(ContentType.IMAGE_JPEG.getMimeType());
        add("text/csv");
    }};

    private final ThreadLocal<HttpRequest<?>> requestThreadLocal = new ThreadLocal<>();

    @Override
    public void onRequest(HttpRequest<?> request, Config config) {
        requestThreadLocal.set(request);
    }

    @Override
    public void onResponse(HttpResponse<?> response, HttpRequestSummary request, Config config) {
        List<String> contentType = response.getHeaders().get("Content-Type");

        if (contentType.stream().anyMatch(ignoreContentType::contains)) {
            return;
        }

        if (response.isSuccess()) {
            success(response, config);
        } else {
            error(response, config);
        }
    }

    private void success(HttpResponse<?> response, Config config) {
        HttpRequest<?> httpRequest = requestThreadLocal.get();
        ObjectMapper mapper = config.getObjectMapper();
        String traceId = TraceUtil.getTraceId(null);
        String format = """
                TraceId    : {}
                TimeStamp  : {}
                Url        : {}
                StatusCode : {}
                Headers    : {}
                Params     : {}
                Body       : {}
                TimeCost   : {} ms
                Response   : {}
                ====================================================================================
                """;

        apiLog.info(format,
                traceId,
                DateUtil.format(Date.from(httpRequest.getCreationTime()), "yyyy-MM-dd HH:mm:ss.SSS"),
                httpRequest.getUrl(),
                response.getStatus(),
                writeValue(httpRequest.getHeaders().all(), mapper),
                writeValue(UrlBuilder.of(httpRequest.getUrl(), StandardCharsets.UTF_8).getQuery().getQueryMap(), mapper),
                writeValue(getBody(httpRequest), mapper),
                httpRequest.getCreationTime().until(Instant.now(), ChronoUnit.MILLIS),
                writeValue(response.getBody(), mapper));
    }

    private void error(HttpResponse<?> response, Config config) {
        HttpRequest<?> httpRequest = requestThreadLocal.get();
        ObjectMapper mapper = config.getObjectMapper();
        String traceId = TraceUtil.getTraceId(null);
        String format = """
                TraceId    : {}
                TimeStamp  : {}
                Url        : {}
                StatusCode : {}
                Headers    : {}
                Params     : {}
                Body       : {}
                TimeCost   : {} ms
                Response   : {}
                ====================================================================================
                """;

        String responseStr = writeValue(response.getBody(), mapper);
        apiLog.info(format,
                traceId,
                DateUtil.format(Date.from(httpRequest.getCreationTime()), "yyyy-MM-dd HH:mm:ss.SSS"),
                httpRequest.getUrl(),
                response.getStatus(), // 添加状态码
                writeValue(httpRequest.getHeaders().all(), mapper),
                writeValue(UrlBuilder.of(httpRequest.getUrl(), StandardCharsets.UTF_8).getQuery().getQueryMap(), mapper),
                writeValue(getBody(httpRequest), mapper), // 使用 getBody 方法获取请求体
                httpRequest.getCreationTime().until(Instant.now(), ChronoUnit.MILLIS),
                responseStr);

        throw new RemoteCallException("call " + httpRequest.getUrl() + " error");
    }

    private Object getBody(HttpRequest<?> request) {
        if (request.getBody().isEmpty()) {
            return null;
        }
        return request.getBody()
                .filter(body -> body.isMultiPart() || body.isEntityBody())
                .map(body -> body.uniPart().getValue())
                .orElse(((Body) request).multiParts().stream()
                        .map(bodyPart -> new ImmutablePair<>(bodyPart.getName(), bodyPart.getValue()))
                        .collect(Collectors.toList()));
    }

    private String writeValue(Object o, ObjectMapper objectMapper) {
        if (o instanceof String) {
            return (String) o;
        }

        return objectMapper.writeValue(o);
    }


}

package org.example.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.example.wrapper.BufferingClientHttpResponseWrapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class CustomClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {
  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] bytes, ClientHttpRequestExecution execution) throws IOException {
    log.info("HTTP Method: {}, URI: {}, Headers: {}", request.getMethod(), request.getURI(), request.getHeaders());
    request.getMethod();
    if (request.getMethod().equals(HttpMethod.POST)) {
      log.info("HTTP body: {}", new String(bytes, StandardCharsets.UTF_8));
    }

    ClientHttpResponse response = execution.execute(request, bytes);
    ClientHttpResponse responseWrapper = new BufferingClientHttpResponseWrapper(response);

    String body = StreamUtils.copyToString(responseWrapper.getBody(), StandardCharsets.UTF_8);
    log.info("RESPONSE body: {}", body);

    return responseWrapper;
  }
}

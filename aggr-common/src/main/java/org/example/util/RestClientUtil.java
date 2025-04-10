package org.example.util;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;
import org.example.config.HttpClientConfig;
import org.example.interceptor.CustomClientHttpRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/**
 * @author admin
 */
@Component
public class RestClientUtil {

  @Autowired
  private HttpClientConfig httpClientConfig;

  // SSL安全配置
  @Bean
  public SSLConnectionSocketFactory sslSocketFactory() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
    SSLContext sslContext = SSLContexts.custom().loadTrustMaterial((chain, authType) -> true).build();
    return new SSLConnectionSocketFactory(sslContext);
  }

  @Bean
  public PoolingHttpClientConnectionManager manager(SSLConnectionSocketFactory sslSocketFactory) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
    Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslSocketFactory).build();

    PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager(registry);
    manager.setMaxTotal(httpClientConfig.getMaxTotal());
    manager.setDefaultMaxPerRoute(httpClientConfig.getMaxPerRoute());

    manager.setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(Timeout.of(httpClientConfig.getSocket(), TimeUnit.MILLISECONDS)).build());

    manager.setDefaultConnectionConfig(ConnectionConfig.custom().setConnectTimeout(Timeout.of(httpClientConfig.getConnect(), TimeUnit.MILLISECONDS)).build());

    return manager;
  }

  // HttpClient实例
  @Bean(destroyMethod = "close")
  public CloseableHttpClient httpClient(PoolingHttpClientConnectionManager manager) {
    return HttpClients.custom().setConnectionManager(manager).setDefaultRequestConfig(RequestConfig.custom().setConnectionRequestTimeout(Timeout.of(httpClientConfig.getRequest(), TimeUnit.MILLISECONDS)).setResponseTimeout(Timeout.of(httpClientConfig.getSocket(), TimeUnit.MILLISECONDS)).build())
      //.setRetryStrategy(new DefaultHttpRequestRetryStrategy(3, true))
      .setKeepAliveStrategy((response, context) -> TimeValue.ofMilliseconds(30_000)).build();
  }

  // RestTemplate配置
  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder, HttpClient httpClient) {
    return builder.requestFactory(() -> new HttpComponentsClientHttpRequestFactory(httpClient)).interceptors(new CustomClientHttpRequestInterceptor()).build();
  }

  // RestClient配置
  @Bean
  public RestClient restClient(RestTemplate restTemplate) {
    return RestClient.builder(restTemplate)
      .requestInterceptor(new CustomClientHttpRequestInterceptor()).build();
  }

}

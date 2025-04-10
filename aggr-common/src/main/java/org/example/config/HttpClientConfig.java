package org.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author admin
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "http.client")
public class HttpClientConfig {
  private Integer maxTotal;
  private Integer maxPerRoute;
  private Long connect;
  private Long socket;
  private Long request;

}


package org.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author admin
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

  private String secret;

  private int expire;
}

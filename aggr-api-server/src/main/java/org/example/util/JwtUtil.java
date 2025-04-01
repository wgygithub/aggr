package org.example.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.config.JwtConfig;
import org.example.exceptions.AuthException;
import org.example.po.auth.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Getter
@Component
public class JwtUtil {
  @Autowired
  private JwtConfig config;

  private final static String PAYLOAD_NAME = "USERINFO";

  public String createToken(UserInfo userInfo) {
    if (userInfo == null) {
      throw new IllegalArgumentException("UserInfo cannot be null");
    }
    // 校验过期时间配置
    int expireSeconds = config.getExpire();
    if (expireSeconds <= 0) {
      throw new IllegalArgumentException("Expiration time must be greater than zero");
    }
    long currentTimeMillis = System.currentTimeMillis();
    Date expirationDate = DateUtil.offsetSecond(new Date(currentTimeMillis), config.getExpire());
    return JWT.create()
      .setKey(config.getSecret().getBytes(StandardCharsets.UTF_8))
      .setPayload(PAYLOAD_NAME, userInfo)
      .setExpiresAt(expirationDate)
      .sign();
  }

  public UserInfo validate(String token) {
    if (token == null || token.isEmpty()) {
      log.warn("Token is null or empty");
      throw new AuthException("Invalid token");
    }
    try {
      JWT jwt = JWTUtil.parseToken(token).setKey(getSecretBytes());
      if (!jwt.validate(0)) {
        log.warn("Token validation failed: {}", token);
        throw new AuthException("Token is invalid");
      }
      return (UserInfo) jwt.getPayload(PAYLOAD_NAME);
    } catch (Exception e) {
      log.error("Error while parsing token", e);
      throw new AuthException("Token parsing failed");
    }
  }

  /**
   * 获取加密密钥字节数组
   *
   * @return 密钥字节数组
   */
  private byte[] getSecretBytes() {
    String secret = config.getSecret();
    if (secret == null || secret.isEmpty()) {
      throw new IllegalStateException("JWT secret cannot be null or empty");
    }
    return secret.getBytes(StandardCharsets.UTF_8);
  }
}

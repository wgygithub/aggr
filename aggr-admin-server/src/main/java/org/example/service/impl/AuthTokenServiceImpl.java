package org.example.service.impl;

import cn.hutool.core.util.IdUtil;
import org.example.entity.AuthToken;
import org.example.mapper.AuthTokenMapper;
import org.example.service.AuthTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author admin
 * @description 针对表【auth_token】的数据库操作Service实现
 * @createDate 2025-03-20 23:39:41
 */
@Service
public class AuthTokenServiceImpl implements AuthTokenService {

  @Autowired
  private AuthTokenMapper authTokenMapper;

  @Override
  public AuthToken getToken(String token) {
    return authTokenMapper.getToken(token);
  }

  @Override
  public AuthToken saveToken(Integer userId) {
    AuthToken authToken = new AuthToken();
    authToken.setUserId(userId);
    long epochSecond = LocalDateTime.now().plusMinutes(30).atZone(ZoneId.systemDefault())
      .toInstant().getEpochSecond();
    authToken.setExpireTime(epochSecond);
    authToken.setToken(IdUtil.nanoId());
    authToken.setUserId(userId);
    authToken.setDeviceToken(IdUtil.nanoId());
    authToken.setExpireTime(epochSecond);
    authTokenMapper.insert(authToken);
    return authToken;
  }
}





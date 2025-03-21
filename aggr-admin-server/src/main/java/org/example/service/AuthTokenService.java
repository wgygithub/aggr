package org.example.service;

import org.example.entity.AuthToken;

/**
 * @author admin
 * @description 针对表【auth_token】的数据库操作Service
 * @createDate 2025-03-20 23:39:41
 */
public interface AuthTokenService {
  /**
   * 根据token查询token信息
   *
   * @param token
   * @return
   */
  AuthToken getToken(String token);

  /**
   * @param userId
   * @return
   */
  AuthToken saveToken(Integer userId);
}

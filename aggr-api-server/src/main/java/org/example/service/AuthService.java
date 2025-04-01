package org.example.service;

import org.example.param.user.LoginParam;
import org.example.param.user.RegisterParam;

public interface AuthService {
  /**
   * 登录
   *
   * @param loginParam
   */
  void login(LoginParam loginParam);

  /**
   * 注册
   *
   * @param registerParam
   */
  void register(RegisterParam registerParam);
}

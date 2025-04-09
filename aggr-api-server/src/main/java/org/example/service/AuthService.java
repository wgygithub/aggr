package org.example.service;

import org.example.param.user.LoginParam;
import org.example.param.user.RegisterParam;
import org.example.vo.LoginVo;

/**
 * @author admin
 */
public interface AuthService {
  /**
   * 登录
   *
   * @param loginParam
   */
  LoginVo login(LoginParam loginParam);

  /**
   * 注册
   *
   * @param registerParam
   */
  void register(RegisterParam registerParam);
}

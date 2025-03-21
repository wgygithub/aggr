package org.example.service;

import org.example.param.auth.LoginParam;
import org.example.vo.auth.LoginVo;

public interface AuthService {
  /**
   * 用户登录
   *
   * @param loginParam
   * @return
   */
  LoginVo login(LoginParam loginParam);
}

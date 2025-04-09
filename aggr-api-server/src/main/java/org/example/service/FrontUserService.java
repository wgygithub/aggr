package org.example.service;

import org.example.domain.FrontUser;
import org.example.param.user.LoginParam;
import org.example.param.user.RegisterParam;
import org.example.vo.LoginVo;

/**
 * @author admin
 * @description 针对表【front_user(前端用户表)】的数据库操作Service
 * @createDate 2025-04-01 21:23:54
 */
public interface FrontUserService {
  /**
   * @param userName
   */
  FrontUser getUserByUserName(String userName);

  /**
   * 注册
   *
   * @param registerParam
   */
  void register(RegisterParam registerParam);

  /**
   * 根据邀请码查询用户信息
   *
   * @param inviterCode
   * @return
   */
  FrontUser getUserByInviterCode(String inviterCode);

  /**
   * 用户登录
   *
   * @param loginParam
   * @return
   */
  LoginVo login(LoginParam loginParam);
}

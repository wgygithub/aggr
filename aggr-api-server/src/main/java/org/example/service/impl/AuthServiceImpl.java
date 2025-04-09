package org.example.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.annotations.CustomLock;
import org.example.param.user.LoginParam;
import org.example.param.user.RegisterParam;
import org.example.service.AuthService;
import org.example.service.FrontUserService;
import org.example.util.JwtUtil;
import org.example.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author admin
 */
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private FrontUserService userService;

  @Override
  public LoginVo login(LoginParam loginParam) {
    log.info("user:{} login", loginParam.getUserName());
    return userService.login(loginParam);
  }

  @Override
  @CustomLock(key = "#registerParam.userName", prefix = "register", leaseTime = 1000)
  public void register(RegisterParam registerParam) {
    userService.register(registerParam);
  }
}

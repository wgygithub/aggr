package org.example.service.impl;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.exceptions.ParamException;
import org.example.param.user.LoginParam;
import org.example.param.user.RegisterParam;
import org.example.service.AuthService;
import org.example.service.FrontUserService;
import org.example.util.JwtUtil;
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
  public void login(LoginParam loginParam) {
    log.info("user:{} login", loginParam.getUserName());
  }

  @Override
  public void register(RegisterParam registerParam) {

    if (userService.checkUserNameIsUnique(registerParam.getUserName())) {
      log.info("user register, but {} is exist", registerParam.getUserName());
      throw new ParamException(StrUtil.format("username {} is exist",
        registerParam.getUserName()));
    }



  }
}

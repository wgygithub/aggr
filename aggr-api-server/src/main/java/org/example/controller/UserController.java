package org.example.controller;

import org.example.param.user.LoginParam;
import org.example.param.user.RegisterParam;
import org.example.resp.Result;
import org.example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private AuthService authService;

  /**
   * 登录接口
   *
   * @return
   */
  @PostMapping("/login")
  public Result<Void> login(@RequestBody @Validated LoginParam loginParam) {
    authService.login(loginParam);
    return Result.OK();
  }


  /**
   * 注册接口
   *
   * @return
   */
  @PostMapping("/register")
  public Result<Void> register(@RequestBody @Validated RegisterParam registerParam) {
    authService.register(registerParam);
    return Result.OK();
  }

}

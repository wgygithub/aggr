package org.example.controller.auth;


import lombok.extern.slf4j.Slf4j;
import org.example.annotations.RateLimit;
import org.example.param.auth.LoginParam;
import org.example.resp.Result;
import org.example.service.AuthCaptchaService;
import org.example.service.AuthService;
import org.example.vo.auth.CaptchaImageVo;
import org.example.vo.auth.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 验证相关接口
 */
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

  @Autowired
  private AuthCaptchaService authCaptchaService;

  @Autowired
  private AuthService authService;

  /**
   * 生成图像验证码
   */
  @GetMapping("/captcha")
  public Result<CaptchaImageVo> getCaptcha() {
    return Result.OK(authCaptchaService.generateCaptcha());
  }

  @RateLimit(name = "login", limit = 5)
  @PostMapping("/login")
  public Result<LoginVo> login(@RequestBody @Validated LoginParam loginParam) {
    log.info("user:{} login", loginParam.getUsername());
    return Result.OK(authService.login(loginParam));
  }


}

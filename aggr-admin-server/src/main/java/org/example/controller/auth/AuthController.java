package org.example.controller.auth;


import org.example.resp.Result;
import org.example.service.AuthCaptchaService;
import org.example.vo.auth.CaptchaImageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证相关接口
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private AuthCaptchaService authCaptchaService;

  /**
   * 生成图像验证码
   */
  @GetMapping("/captcha")
  public Result<CaptchaImageVo> getCaptcha() {
    return Result.OK(authCaptchaService.generateCaptcha());
  }


}

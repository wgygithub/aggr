package org.example.service.auth;

import org.example.service.AuthCaptchaService;
import org.example.vo.auth.CaptchaImageVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthCaptchaServiceTest {
  @Autowired
  private AuthCaptchaService authCaptchaService;

  @Test
  public void generateCaptcha() {
    CaptchaImageVo captchaImageVo = authCaptchaService.generateCaptcha();
    System.out.println(captchaImageVo);
  }

}

package org.example.param.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginParam {

  // 用户名
  @NotBlank
  private String username;
  //用户密码
  @NotBlank
  private String password;

  //图像验证码
  private String captchaCode;

  //验证码唯一标识
  private String uuid;

  // 谷歌验证码
  private String totp;
}

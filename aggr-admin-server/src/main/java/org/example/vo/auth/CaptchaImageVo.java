package org.example.vo.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CaptchaImageVo implements java.io.Serializable {
  //验证码唯一标识
  private String uuid;
  //验证码图片
  private String captchaCode;
}

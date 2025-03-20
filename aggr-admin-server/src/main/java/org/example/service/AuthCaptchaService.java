package org.example.service;


import org.example.vo.auth.CaptchaImageVo;

public interface AuthCaptchaService {

  /**
   * 生成图像验证码
   *
   * @return
   */
  CaptchaImageVo generateCaptcha();

  /**
   * 验证图像验证码
   *
   * @param uuid
   * @param code
   * @return
   */
  boolean captchaCheck(String uuid, String code);

}

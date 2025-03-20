package org.example.service.impl;

import cn.hutool.cache.Cache;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.util.IdUtil;
import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.exceptions.AppException;
import org.example.service.AuthCaptchaService;
import org.example.vo.auth.CaptchaImageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Slf4j
@Service
public class AuthCaptchaServiceImpl implements AuthCaptchaService {

  private static final String CAPTCHA_CACHE_PREFIX = "data:image/png;base64,";

  private static final Cache<String, String> captchaCache = new TimedCache<>(1000 * 60 * 5);

  @Autowired
  private Producer producer;

  @Override
  public CaptchaImageVo generateCaptcha() {
    try {
      // 生成唯一标识符
      String uuid = IdUtil.fastUUID();

      // 生成验证码文本
      String capText = producer.createText();
      if (capText == null || !capText.contains("@")) {
        throw new AppException("Invalid captcha text format: " + capText);
      }

      // 分离验证码文本和校验码
      String capStr = extractCapStr(capText);
      String code = extractCode(capText);

      // 生成验证码图片
      BufferedImage image = producer.createImage(capStr);
      if (image == null) {
        throw new AppException("Failed to generate captcha image");
      }

      // 将验证码缓存到缓存中
      captchaCache.put(uuid, code);

      // 将图片转换为Base64字符串
      String base64Image = imageToBase64(image);

      // 返回包含Base64字符串的CaptchaImageVo对象
      return CaptchaImageVo.builder().uuid(uuid).captchaCode(CAPTCHA_CACHE_PREFIX + base64Image).build();
    } catch (IOException e) {
      log.error("Failed to write captcha image to stream", e);
      throw new AppException("Failed to write captcha image to stream");
    } catch (Exception e) {
      log.error("Error generating captcha: {}", e.getMessage(), e);
      throw new AppException("Error generating captcha");
    }
  }

  // 将BufferedImage转换为Base64字符串
  private String imageToBase64(BufferedImage image) throws IOException {
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      ImageIO.write(image, "png", baos);
      byte[] imageBytes = baos.toByteArray();
      return Base64.getEncoder().encodeToString(imageBytes);
    } catch (IOException e) {
      // 捕获并记录异常，便于调试
      log.error("Error converting image to Base64: {}", e.getMessage(), e);
      throw e;
    }
  }

  // 提取验证码文本部分
  private String extractCapStr(String capText) {
    int atIndex = capText.lastIndexOf("@");
    if (atIndex <= 0) {
      log.error("Invalid captcha text format: {}", capText);
      throw new AppException("Invalid captcha text format: " + capText);
    }
    return capText.substring(0, atIndex);
  }

  // 提取校验码部分
  private String extractCode(String capText) {
    int atIndex = capText.lastIndexOf("@");
    if (atIndex < 0 || atIndex >= capText.length() - 1) {
      throw new AppException("Invalid captcha text format: " + capText);
    }
    return capText.substring(atIndex + 1);
  }

  @Override
  public boolean captchaCheck(String uuid, String code) {
    return StringUtils.isNotBlank(uuid) && StringUtils.isNotBlank(code) && code.equalsIgnoreCase(captchaCache.get(uuid));
  }
}

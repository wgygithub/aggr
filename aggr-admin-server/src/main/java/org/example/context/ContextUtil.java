package org.example.context;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.context.vo.UserInfoVo;
import org.example.exceptions.AppException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Slf4j
public class ContextUtil {
  private static final String USERINFO_KEY = "userInfo";

  public static HttpServletRequest request() {
    try {
      return ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
    } catch (Exception e) {
      log.error("无法获取当前请求的 HttpServletRequest 对象", e);
      return null;
    }
  }

  public static UserInfoVo userInfo() {
    HttpServletRequest request = Optional.ofNullable(request()).orElseThrow(() ->
      new AppException("当前请求对象为空，无法获取用户信息"));
    return (UserInfoVo) request.getAttribute(USERINFO_KEY);
  }

  public static void setUserInfo(UserInfoVo userInfoVo) {
    HttpServletRequest request = Optional.ofNullable(request()).orElseThrow(() ->
      new AppException("当前请求对象为空，无法获取用户信息"));
    Optional.ofNullable(userInfoVo).orElseThrow(() ->
      new AppException("用户信息为空，无法设置用户信息"));
    request.setAttribute(USERINFO_KEY, userInfoVo);
  }
}

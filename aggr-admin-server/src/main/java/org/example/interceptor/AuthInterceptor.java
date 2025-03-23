package org.example.interceptor;

import cn.hutool.cache.Cache;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.context.ContextUtil;
import org.example.context.vo.UserInfoVo;
import org.example.entity.AuthToken;
import org.example.exceptions.AuthException;
import org.example.exceptions.HeaderParamException;
import org.example.exceptions.TokenExpiredException;
import org.example.mapper.AuthTokenMapper;
import org.example.service.AdminUserService;
import org.example.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

  private static final List<String> WHITE_URI = List.of(
    "/healthy-check"
  );
  private static final Cache<String, AuthToken> cache = new TimedCache<>(1000 * 10);

  @Autowired
  private AuthTokenMapper tokenMapper;
  @Autowired
  private AdminUserService userService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    checkHeaderParam(request);
    String authorization = request.getHeader("Authorization");
    if (StringUtils.isBlank(authorization)) {
      throw new AuthException();
    }

    if (!authorization.startsWith("Bearer ")) {
      throw new AuthException("Auth Protocol Error");
    }

    String token = authorization.substring("Bearer ".length());

    AuthToken authToken = cache.get(token, true, () -> tokenMapper.selectById(token));

    if (Objects.isNull(authToken) || Objects.isNull(authToken.getExpireTime())) {
      throw new TokenExpiredException("Please login first!");
    }

    //增加token过期时间校验
    Long expireTime = authToken.getExpireTime();
    if (DateUtil.isBefore(expireTime, LocalDateTime.now())) {
      throw new TokenExpiredException("Token expired");
    }

    UserInfoVo userInfoVo = userService.userInfo(authToken.getUserId());

    if (!userInfoVo.getActive()) {
      log.info("user:{} is blocked", userInfoVo.getUserName());
      throw new AuthException(StrUtil.format("user:{} is blocked",
        userInfoVo.getUserName()));
    }

    ContextUtil.setUserInfo(userInfoVo);

    return WHITE_URI.contains(request.getRequestURI());
  }

  private void checkHeaderParam(HttpServletRequest request) {
    String currencyStr = request.getHeader("currency");
    if (StringUtils.isEmpty(currencyStr)) {
      throw new HeaderParamException("header missing currency");
    }
  }
}

package org.example.config;

import org.example.filter.LogRequestFilter;
import org.example.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Set;

@Component
public class WebConfig implements WebMvcConfigurer {
  @Autowired
  private SystemConfig systemConfig;

  static final Set<String> IGNORE_PATH = Set.of("/favicon.ico");

  @Autowired
  private AuthInterceptor authInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    InterceptorRegistration interceptorRegistration = registry.addInterceptor(authInterceptor);
    interceptorRegistration.addPathPatterns("/**");
  }

  @Bean
  public FilterRegistrationBean<LogRequestFilter> logRequestFilter() {
    FilterRegistrationBean<LogRequestFilter> registrationBean = new FilterRegistrationBean<>();
    LogRequestFilter logRequestFilter = new LogRequestFilter();
    logRequestFilter.setIgnorePath(IGNORE_PATH);
    registrationBean.setFilter(logRequestFilter);
    // 设置过滤器的URL模式
    registrationBean.addUrlPatterns("/*");
    return registrationBean;
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    if (systemConfig.isCorsEnable()) {
      registry.addMapping("/**") // 所有路径允许跨域
        .allowedOrigins("*") // 允许多个域名
        .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的HTTP方法
        .allowedHeaders("*") // 允许所有请求头
        .allowCredentials(true) // 允许携带Cookie
        .maxAge(3600); // 预检请求缓存时间
    }

  }

}

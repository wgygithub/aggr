package org.example.config;

import org.example.filter.LogRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class WebConfig {
    static final Set<String> IGNORE_PATH = Set.of("/favicon.ico");

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

}

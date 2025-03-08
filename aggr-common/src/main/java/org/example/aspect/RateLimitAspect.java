package org.example.aspect;


import com.google.common.util.concurrent.RateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.annotations.RateLimit;
import org.example.exceptions.RateLimitException;
import org.example.util.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("UnstableApiUsage")
@Aspect
@Component
public class RateLimitAspect {

    private final ConcurrentHashMap<Pair<String, String>, RateLimiter> limiters = new ConcurrentHashMap<>();

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    @Around("@annotation(rateLimit)")
    public Object around(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        // 获取注解参数
        String name = rateLimit.name();
        int limit = rateLimit.limit();
        String ip = IpUtils.getIpAddr(request);
        // 获取对应的 RateLimiter
        RateLimiter limiter = getRateLimiter(new ImmutablePair<>(name, ip), limit);

        // 设置 HTTP Header
        if (response != null) {
            response.setHeader("X-RateLimit-Limit", String.valueOf(limit));
            response.setHeader("X-RateLimit-Remaining", String.valueOf(Math.max(0, (int) (limiter.getRate() - 1))));
        }
        // 尝试获取令牌
        if (!limiter.tryAcquire()) {
            throw new RateLimitException("Rate limit exceeded for " + name);
        }
        // 继续执行方法
        return joinPoint.proceed();
    }


    public RateLimiter getRateLimiter(Pair<String, String> key, int limit) {
        return limiters.computeIfAbsent(key, k -> RateLimiter.create(limit));
    }

}

package org.example.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.annotations.CustomLock;
import org.example.exceptions.LockAcquireException;
import org.example.lock.LockExecutor;
import org.example.resolver.LockKeyResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author admin
 */
@Slf4j
@Aspect
@Component
public class LockAspect {

  @Autowired
  private LockKeyResolver keyResolver;
  @Autowired
  private LockExecutor lockExecutor;

  @Around("@annotation(customLock)")
  public Object around(ProceedingJoinPoint joinPoint, CustomLock customLock) throws Throwable {
    String rawKey = keyResolver.resolveKey(joinPoint, customLock.key());
    String fullKey = customLock.prefix() + rawKey;

    if (!lockExecutor.acquire(fullKey, customLock.leaseTime())) {
      log.warn("Failed to acquire lock for key: {}", fullKey);
      throw new LockAcquireException("Failed to acquire lock");
    }

    try {
      return joinPoint.proceed();
    } finally {
      lockExecutor.release(fullKey);
    }
  }
}

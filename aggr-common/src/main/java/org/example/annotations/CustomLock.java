package org.example.annotations;

import org.example.enums.LockType;

import java.lang.annotation.*;

/**
 * @author admin
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomLock {
  /**
   * 锁Key表达式（支持SpEL）
   */
  String key() default "";

  /**
   * Key前缀
   */
  String prefix() default "lock:";

  /**
   * 锁类型（LOCAL/REDIS）
   */
  LockType type() default LockType.LOCAL;

  /**
   * 等待时间（毫秒）
   */
  long leaseTime() default 3000;
}

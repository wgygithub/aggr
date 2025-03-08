package org.example.annotations;

import java.lang.annotation.*;

/**
 * 用于标记方法需要做幂等性校验
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Idempotent {

  /**
   * 幂等键前缀，可选（用于区分不同业务）
   */
  String value() default "IDEMPOTENT:";

  String key() default "orderId";

//  /**
//   * 自定义幂等超时时间等，也可加在此处
//   */
//  long expireSeconds() default 300;
}

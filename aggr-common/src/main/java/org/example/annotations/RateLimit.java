package org.example.annotations;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {

  String name(); // 接口名称

  int limit(); // 每分钟令牌总数
}

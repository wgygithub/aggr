package org.example.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.annotations.Idempotent;
import org.example.exceptions.RepeatCallException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 用于模拟幂等存储，生产环境可使用 Redis 等
 * key: 幂等key (如 "IDEMPOTENT:xxxxx" )
 * value: 存储一个标记或者实际执行结果，也可用 Boolean
 */
@Slf4j
@Aspect
@Component
public class IdempotentAspect {

    private final Map<String, Object> idempotentStore = new ConcurrentHashMap<>();

    /**
     * AOP 切点：拦截 @IdempotentCall 注解的方法
     */
    @Around("@annotation(org.example.annotations.Idempotent)")
    public Object idempotentHandler(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        // 获取注解
        Idempotent annotation = method.getAnnotation(Idempotent.class);
        String keyPrefix = annotation.value();
        String key = annotation.key();
        // 1. 获取幂等Key (orderId)
        String keyValue = extractkey(pjp, pjp.getArgs(), key);
        if (keyValue == null) {
            throw new IllegalAccessError("No orderId found in method arguments");
        }
        // 2. 构建完整key
        String idempotentKey = keyPrefix + keyValue;
        // 3. 幂等校验
        if (idempotentStore.containsKey(idempotentKey)) {
            throw new RepeatCallException("Duplicate request with key=" + keyValue);
        }
        idempotentStore.put(idempotentKey, "LOCKED");
        try {
            return pjp.proceed();
        } finally {
            idempotentStore.remove(idempotentKey);
        }
    }

    /**
     * 根据切点方法的参数，查找 orderId:
     * 1) 若直接存在字符串参数名为 "orderId" 则返回其值;
     * 2) 否则若存在对象参数, 并且该对象有 String 类型的字段名为 "orderId", 则返回其值
     */
    private String extractkey(ProceedingJoinPoint pjp, Object[] args, String key) {
        if (args == null) {
            return null;
        }

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        String[] parameterNames = signature.getParameterNames();

        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg == null) {
                continue;
            }

            // 1. 若是 String 且参数名即是 orderId
            if (arg instanceof String val) {
                if (parameterNames[i].equals(key) && !val.isEmpty()) {
                    return val;
                }
            } else {
                // 2. 若是对象，扫描其字段
                String maybeId = findOrderIdFromObject(arg, key);
                if (maybeId != null) {
                    return maybeId;
                }
            }
        }

        // 如果都没找到
        return null;
    }

    /**
     * 反射扫描对象字段，看是否有名为 "orderId" 且类型为 String 的字段
     */
    private String findOrderIdFromObject(Object obj, String key) {
        Class<?> clazz = obj.getClass();
        // 获取所有字段(包含私有)
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (key.equals(field.getName())) {
                field.setAccessible(true);
                try {
                    Object val = field.get(obj);
                    if (val != null) {
                        return val.toString();
                    }
                } catch (IllegalAccessException e) {
                    // ignore or log
                }
            }
        }
        return null;
    }
}

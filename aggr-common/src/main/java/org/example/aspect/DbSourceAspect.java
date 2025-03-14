package org.example.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.annotations.DbSource;
import org.example.dynamicdb.DataSourceContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 数据库切面
 */
@Slf4j
@Aspect
@Component
public class DbSourceAspect {

    @Pointcut("@annotation(org.example.annotations.DbSource)")
    public void dynamicDataSource() {
    }

    @Around("dynamicDataSource()")
    public Object datasourceAround(ProceedingJoinPoint point) throws Throwable {
        // 获取要切换的数据源名称
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        DbSource dbSource = method.getAnnotation(DbSource.class);
        log.info("select dataSource:" + dbSource.value());
        DataSourceContextHolder.setDataSourceKey(dbSource.value());
        try {
            return point.proceed();
        } finally {
            DataSourceContextHolder.clear();
        }
    }
}

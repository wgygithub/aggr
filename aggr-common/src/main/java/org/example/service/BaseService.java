package org.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import lombok.extern.slf4j.Slf4j;
import org.example.exceptions.AppException;

import java.lang.reflect.Field;

@Slf4j
public abstract class BaseService {

    protected <T, E> void setFieldsIfNotNull(LambdaUpdateWrapper<T> wrapper,
                                             E param,
                                             Class<T> entityClass) {
        Field[] fields = param.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(param);
                if (ObjectUtil.isNotNull(value)) {
                    SFunction<T, ?> sFunction = getField(entityClass, field.getName());
                    if (sFunction != null) {
                        wrapper.set(sFunction, value);
                    }
                }
            } catch (IllegalAccessException e) {
                log.warn("无法设置字段: {}", field.getName());
                throw new AppException("无法设置字段: " + field.getName());
            }
        }
    }

    private <T> SFunction<T, ?> getField(Class<T> entityClass, String fieldName) {
        try {
            Field field = entityClass.getDeclaredField(fieldName);
            return (SFunction<T, ?>) field.get(entityClass);
        } catch (Exception e) {
            log.warn("字段 {} 不存在于类 {}", fieldName, entityClass.getName());
            throw new AppException("字段 " + fieldName + " 不存在于类 " + entityClass.getName());
        }
    }
}

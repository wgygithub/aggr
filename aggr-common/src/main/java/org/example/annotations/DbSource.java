package org.example.annotations;

import org.example.constant.DataSourceConstants;

import java.lang.annotation.*;


/**
 * @author admin
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DbSource {
    /**
     * 数据源 key 值。
     * <p>
     * 默认值为 {@link DataSourceConstants#DEFAULT_MASTER_KEY}。
     *
     * @return 数据源 key 值
     */
    String value() default DataSourceConstants.DEFAULT_MASTER_KEY;
}

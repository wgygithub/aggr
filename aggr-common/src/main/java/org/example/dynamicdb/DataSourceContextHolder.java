package org.example.dynamicdb;

import lombok.extern.slf4j.Slf4j;
import org.example.constant.DataSourceConstants;

@Slf4j
public class DataSourceContextHolder {
    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public static void setDataSourceKey(String key) {
        CONTEXT.set(key);
    }

    //默认是master
    public static String getDataSourceKey() {
        return CONTEXT.get() == null ? DataSourceConstants.DEFAULT_MASTER_KEY : CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}

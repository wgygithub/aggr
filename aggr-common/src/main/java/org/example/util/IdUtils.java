package org.example.util;

import cn.hutool.core.util.IdUtil;

public class IdUtils {
    /**
     * 获取指定长度的字符串，不带连字符，最多可以到32位
     *
     * @return
     */
    public static String getSimpleUUID(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("param is illegal");
        }
        return IdUtil.fastSimpleUUID().substring(0, length);
    }
}

package org.example.util;

import cn.hutool.core.util.StrUtil;
import org.slf4j.MDC;

public final class TraceUtil {

    private static final String LOG_TRACE_ID = "traceId";

    public static String getTraceId(String traceId) {
        traceId = MDC.get(LOG_TRACE_ID);
        if (StrUtil.isNotBlank(traceId)) {
            return traceId;
        }
        if (StrUtil.isNotBlank(traceId)) {
            MDC.put(LOG_TRACE_ID, traceId);
            return traceId;
        }
        traceId = IdUtils.getSimpleUUID(32);
        MDC.put(LOG_TRACE_ID, traceId);
        return traceId;
    }

    public static void clear() {
        MDC.clear();
    }

}

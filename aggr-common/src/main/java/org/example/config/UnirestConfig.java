package org.example.config;

import kong.unirest.Unirest;
import kong.unirest.UnirestInstance;
import lombok.extern.slf4j.Slf4j;
import org.example.interceptor.UnirestLoggingInterceptor;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
public class UnirestConfig {
    private static final UnirestLoggingInterceptor loggingInterceptor = new UnirestLoggingInterceptor();
    private static final Jackson2ObjectMapper objectMapper = new Jackson2ObjectMapper();

    static {
        setDefaultConfig(Unirest.primaryInstance());
    }

    private static void setDefaultConfig(UnirestInstance instance) {
        instance.config().connectTimeout(1000 * 10).setObjectMapper(objectMapper).interceptor(loggingInterceptor);
    }

    public static UnirestInstance spawnInstance() {
        UnirestInstance unirestInstance = Unirest.spawnInstance();
        setDefaultConfig(unirestInstance);
        return unirestInstance;
    }

    public static void main(String[] args) {
        try (UnirestInstance unirestInstance = spawnInstance()) {
        }
    }

}

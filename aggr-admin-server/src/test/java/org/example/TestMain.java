package org.example;

import kong.unirest.GenericType;
import kong.unirest.UnirestInstance;
import org.example.config.UnirestConfig;
import org.example.resp.Result;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestMain {
    @Test
    public void test() {
        try (UnirestInstance unirestInstance = UnirestConfig.spawnInstance()) {
            Result<?> body = unirestInstance.get("http://127.0.0.1:10002/auth/captcha-auth/cap").asObject(new GenericType<Result<?>>() {
            }).getBody();
            System.out.println(body);
        }

    }


}

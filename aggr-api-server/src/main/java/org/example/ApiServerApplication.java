package org.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author admin
 */
@SpringBootApplication
@MapperScan
public class ApiServerApplication {
  public static void main(String[] args) {
    SpringApplication.run(ApiServerApplication.class, args);
  }
}
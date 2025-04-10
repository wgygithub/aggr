package org.example.utill;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestClient;

@SpringBootTest
public class RestClientUtilTest {

  @Autowired
  private RestClient restClient;

  @Test
  public void test() {
    String body = restClient.get().uri("https://www.baidu.com")


      .retrieve().body(String.class);
    System.out.println(body);
  }

}

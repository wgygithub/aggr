package org.example.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginVo implements Serializable {
  //登录token
  private String token;
}

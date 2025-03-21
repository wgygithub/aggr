package org.example.vo.auth;

import lombok.Data;

@Data
public class LoginVo {
  //登录token
  private String authToken;
  //设备token
  private String deviceToken;
  //过期时间
  private Long expiredAt;
  //是否绑定了谷歌验证 , 0:未绑定，1:已绑定
  private String bindFlag;
}

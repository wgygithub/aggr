package org.example.param.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * @author admin
 */
@Data
public class RegisterParam implements Serializable {
  @NotBlank
  private String userName;
  @NotBlank
  private String password;

  private String phone;

  private String email;
  //邀请人
  private String inviterCode;
}

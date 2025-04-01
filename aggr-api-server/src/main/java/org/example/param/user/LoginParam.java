package org.example.param.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class LoginParam implements Serializable {
  @NotBlank
  private String userName;
  @NotBlank
  private String password;
}

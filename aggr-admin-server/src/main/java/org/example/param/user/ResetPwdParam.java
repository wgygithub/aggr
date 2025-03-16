package org.example.param.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResetPwdParam {
    @NotNull
    private Integer userId;
    @NotNull
    private String oldPwd;
    @NotNull
    private String newPwd;
}

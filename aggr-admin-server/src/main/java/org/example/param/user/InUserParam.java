package org.example.param.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InUserParam {
    @NotBlank
    private String userName;

    private String password;
    @NotNull
    private Integer roleId;

    private Boolean active;
}

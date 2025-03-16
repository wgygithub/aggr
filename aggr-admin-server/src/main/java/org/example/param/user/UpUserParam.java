package org.example.param.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpUserParam {
    @NotNull
    private Integer userId;

    private String password;

    private Boolean active;

    private Integer roleId;
}

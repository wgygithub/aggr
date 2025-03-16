package org.example.param.role;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpRoleParam {
    @NotNull
    private Integer roleId;

    private String roleName;

    private Boolean active;

    private String remark;
}

package org.example.param.role;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class InRoleParam implements Serializable {
    @NotBlank
    private String roleName;

    private Boolean active;

    private String remark;

}

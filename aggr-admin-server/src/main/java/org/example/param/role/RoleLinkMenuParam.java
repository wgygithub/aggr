package org.example.param.role;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RoleLinkMenuParam {
  @NotNull
  private Integer roleId;

  @NotNull
  private List<Integer> menuIds;
}

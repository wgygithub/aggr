package org.example.vo.role;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdminRoleVo implements Serializable {
    //角色id
    private Integer roleId;
    //角色名称
    private String roleName;
    //角色状态
    private Boolean active;

    private String remark;

    private Long createTime;

    private Long updateTime;

}

package org.example.vo.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdminUserVo implements Serializable {

    private String userId;

    private String userName;

    private String roleName;

    private Integer roleId;

    private Boolean active;

    private Long createTime;

    private Long updateTime;
}

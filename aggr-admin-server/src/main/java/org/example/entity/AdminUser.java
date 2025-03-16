package org.example.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统用户表
 */
@TableName("admin_user")
@Data
public class AdminUser extends BaseEntity implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名称，唯一
     */
    private String userName;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户状态
     */
    private Boolean active;

    /**
     * 角色id
     */
    private Integer roleId;
}

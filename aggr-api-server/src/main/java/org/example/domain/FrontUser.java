package org.example.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 前端用户表
 * @TableName front_user
 */
@TableName(value ="front_user")
@Data
public class FrontUser {
    /**
     * 用户id
     */
    @TableId
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态: 0-禁用，1-启用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Long createtime;

    /**
     * 更新时间
     */
    private Long updatetime;
}
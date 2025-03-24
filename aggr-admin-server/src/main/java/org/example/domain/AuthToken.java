package org.example.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName auth_token
 */
@TableName(value ="auth_token")
@Data
public class AuthToken {
    /**
     * 登录token
     */
    @TableId
    private String token;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 设备token
     */
    private String deviceToken;

    /**
     * 过期时间
     */
    private Long expireTime;
}
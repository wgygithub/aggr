package org.example.param.user;


import lombok.Data;
import org.example.param.BasePageParam;

@Data
public class UserPageQueryParam extends BasePageParam {
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 角色id
     */
    private Integer roleId;
}

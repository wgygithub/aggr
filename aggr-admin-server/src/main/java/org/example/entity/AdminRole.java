package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统角色表，存储系统中各个角色的信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "admin_role")
public class AdminRole extends BaseEntity {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 是否激活,1激活，0不激活
     */
    private Boolean active;

    /**
     * 角色描述
     */
    private String remark;
}
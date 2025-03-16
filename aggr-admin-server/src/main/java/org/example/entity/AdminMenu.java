package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 菜单表
 */
@TableName(value = "admin_menu")
@Data
public class AdminMenu extends BaseEntity {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private String title;

    /**
     *
     */
    private String icon;

    /**
     *
     */
    private String path;

    /**
     *
     */
    private String component;

    /**
     * 父类菜单，如果是没有父级菜单为0
     */
    private Integer parent;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    private String menuType;

    /**
     * 菜单图标
     */
    private String elSvgIcon;

    /**
     * 路由名称
     */
    private String routeName;

    /**
     * 路由参数
     */
    private String query;

    /**
     * 重定向
     */
    private String redirect;
}
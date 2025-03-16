package org.example.param.menu;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InMenuParam {
    /**
     * 菜单名称
     */
    @NotBlank
    private String name;

    /**
     * 菜单标题
     */
    @NotBlank
    private String title;

    /**
     * 菜单icon
     */
    private String icon;

    /**
     * 菜单路径
     */
    private String path;

    /**
     *
     */
    private String component;

    /**
     * 默认root菜单为0
     * 父类菜单
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

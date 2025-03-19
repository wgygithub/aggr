package org.example.service;

import org.example.param.menu.InMenuParam;
import org.example.param.menu.UpMenuParam;
import org.example.vo.menu.AdminMenuVo;

import java.util.List;

public interface AdminMenuService {

    /**
     * 获取菜单信息
     *
     * @param
     * @return
     */
    List<AdminMenuVo> queryMenu(Integer roleId);

    /**
     * 插入菜单信息
     *
     * @param param
     * @return
     */
    int insertMenu(InMenuParam param);

    /**
     * 删除菜单信息
     *
     * @param menuId
     * @return
     */
    int deleteMenu(Integer menuId);

    /**
     * 更新菜单信息
     *
     * @param param
     * @return
     */
    int updateMenu(UpMenuParam param);
}

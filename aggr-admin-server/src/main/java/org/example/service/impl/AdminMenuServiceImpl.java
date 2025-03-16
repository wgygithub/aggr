package org.example.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.AdminMenu;
import org.example.entity.AdminRoleMenu;
import org.example.exceptions.AppException;
import org.example.exceptions.ParamException;
import org.example.mapper.AdminMenuMapper;
import org.example.mapper.AdminRoleMenuMapper;
import org.example.param.menu.InMenuParam;
import org.example.service.AdminMenuService;
import org.example.vo.menu.AdminMenuVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * -- 删除 role_menu 关联
 * DELETE FROM role_menu
 * WHERE menu_id IN (
 * WITH RECURSIVE cte AS (
 * SELECT id FROM admin_menu WHERE id = #{menuId}
 * UNION ALL
 * SELECT am.id FROM admin_menu am INNER JOIN cte ON am.parent = cte.id
 * )
 * SELECT id FROM cte
 * );
 * <p>
 * -- 删除菜单数据
 * WITH RECURSIVE cte AS (
 * SELECT id FROM admin_menu WHERE id = #{menuId}
 * UNION ALL
 * SELECT am.id FROM admin_menu am INNER JOIN cte ON am.parent = cte.id
 * )
 * DELETE FROM admin_menu WHERE id IN (SELECT id FROM cte);
 */
@Slf4j
@Service
public class AdminMenuServiceImpl implements AdminMenuService {

    @Autowired
    private AdminMenuMapper adminMenuMapper;

    @Autowired
    private AdminRoleMenuMapper adminRoleMenuMapper;

    /**
     * 需要支持根据角色id查询
     *
     * @return
     */
    @Override
    public List<AdminMenuVo> queryMenu(Integer roleId) {
        if (roleId == null) {
            return adminMenuMapper.selectList(null).stream().map(this::convert).toList();
        }
        return adminRoleMenuMapper.queryMenuByRoleId(roleId).stream().map(this::convert).toList();
    }


    private AdminMenuVo convert(AdminMenu adminMenu) {
        AdminMenuVo adminMenuVo = new AdminMenuVo();
        adminMenuVo.setMenuId(adminMenu.getId());
        BeanUtils.copyProperties(adminMenu, adminMenuVo);
        return adminMenuVo;
    }

    @Override
    public int insertMenu(InMenuParam param) {
        Optional.ofNullable(param.getParent()).ifPresent(this::checkMenuId);
        AdminMenu adminMenu = new AdminMenu();
        BeanUtils.copyProperties(param, adminMenu);
        Integer parant = ObjectUtil.isNull(adminMenu.getParent()) ? 0 : adminMenu.getParent();
        adminMenu.setParent(parant);
        int count = adminMenuMapper.insert(adminMenu);
        if (count <= 0) {
            log.info("新增菜单失败");
            throw new AppException("新增菜单失败");
        }
        return 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteMenu(Integer menuId) {
        checkMenuId(menuId);
        //校验菜单是否被其他角色引用
        boolean exists = adminRoleMenuMapper.exists(Wrappers.<AdminRoleMenu>lambdaQuery()
                .eq(AdminRoleMenu::getMenuId, menuId));
        if (exists) {
            log.info("{}菜单被其他角色引用，不允许删除", menuId);
            throw new AppException(StrUtil.format("{}菜单被其他角色引用，不允许删除", menuId));
        }
        //删除，需要将下级菜单一起删除
        // 递归删除子菜单
        deleteSubMenus(menuId);
        // 删除当前菜单
        int count = adminMenuMapper.deleteById(menuId);
        if (count <= 0) {
            log.info("{}菜单删除失败", menuId);
            throw new AppException(StrUtil.format("菜单删除失败", menuId));
        }
        return count;
    }

    private void deleteSubMenus(Integer menuId) {
        List<AdminMenu> subMenus = adminMenuMapper.selectList(Wrappers.<AdminMenu>lambdaQuery()
                .eq(AdminMenu::getParent, menuId));
        for (AdminMenu subMenu : subMenus) {
            deleteMenu(subMenu.getId());
        }
    }


    private void checkMenuId(Integer menuId) {
        boolean exists = adminMenuMapper.exists(Wrappers.<AdminMenu>lambdaQuery().eq(AdminMenu::getId, menuId));
        if (!exists) {
            throw new ParamException(StrUtil.format("{} 菜单不存在", menuId));
        }
    }
}

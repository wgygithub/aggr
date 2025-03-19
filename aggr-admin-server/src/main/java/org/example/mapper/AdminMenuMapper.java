package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.example.entity.AdminMenu;

/**
 * @author admin
 * @description 针对表【admin_menu(菜单表)】的数据库操作Mapper
 * @createDate 2025-03-15 16:18:42
 * @Entity generator.domain.AdminMenu
 */
public interface AdminMenuMapper extends BaseMapper<AdminMenu> {

    /**
     * 递归删除菜单
     *
     * @param menuId
     * @return
     */
    int delRecurByMenu(@Param("menuId") Integer menuId);

}





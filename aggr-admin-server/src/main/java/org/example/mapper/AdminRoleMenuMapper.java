package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.example.domain.AdminMenu;
import org.example.domain.AdminRoleMenu;

import java.util.List;

public interface AdminRoleMenuMapper extends BaseMapper<AdminRoleMenu> {

  List<AdminMenu> queryMenuByRoleId(@Param("roleId") Integer roleId);

  int existsRecurByMenuId(@Param("menuId") Integer menuId);
}





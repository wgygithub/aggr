package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.example.entity.AdminMenu;
import org.example.entity.AdminRoleMenu;

import java.util.List;

public interface AdminRoleMenuMapper extends BaseMapper<AdminRoleMenu> {

    List<AdminMenu> queryMenuByRoleId(@Param("roleId") Integer roleId);

}





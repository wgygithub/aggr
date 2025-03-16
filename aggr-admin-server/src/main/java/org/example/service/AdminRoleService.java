package org.example.service;

import org.example.param.role.InRoleParam;
import org.example.param.role.UpRoleParam;
import org.example.vo.role.AdminRoleVo;

import java.util.List;

public interface AdminRoleService {
    /**
     * 获取所有角色
     */
    List<AdminRoleVo> roleQuery();

    /**
     * 新增角色
     *
     * @return
     */
    int insertRole(InRoleParam param);

    /**
     * 更新角色
     */
    int updateRole(UpRoleParam param);

    /**
     * 删除角色
     *
     * @param roleId
     */
    int deleteRole(Integer roleId);
}

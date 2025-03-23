package org.example.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.AdminRole;
import org.example.domain.AdminRoleMenu;
import org.example.domain.AdminUser;
import org.example.exceptions.AppException;
import org.example.exceptions.ParamException;
import org.example.mapper.AdminRoleMapper;
import org.example.mapper.AdminRoleMenuMapper;
import org.example.mapper.AdminUserMapper;
import org.example.param.role.InRoleParam;
import org.example.param.role.RoleLinkMenuParam;
import org.example.param.role.UpRoleParam;
import org.example.service.AdminRoleService;
import org.example.vo.role.AdminRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AdminRoleServiceImpl implements AdminRoleService {
    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Autowired
    private AdminUserMapper adminUserMapper;

    @Autowired
    private AdminRoleMenuMapper adminRoleMenuMapper;

    @Override
    public List<AdminRoleVo> roleQuery() {
        return adminRoleMapper.selectList(null).stream().map(this::convert).toList();
    }

    @Override
    public int insertRole(InRoleParam param) {
        checkInParam(param);
        AdminRole adminRole = new AdminRole();
        adminRole.setRoleName(param.getRoleName());
        adminRole.setRemark(param.getRemark());
        Boolean active = ObjectUtil.isNull(param.getActive()) ? Boolean.TRUE : param.getActive();
        adminRole.setActive(active);
        int count = adminRoleMapper.insert(adminRole);
        if (count <= 0) {
            log.info("{}角色插入失败", param.getRoleName());
            throw new AppException("新增角色失败");
        }
        return count;
    }

    @Override
    public int updateRole(UpRoleParam param) {
        checkUpParam(param);
        int count = adminRoleMapper.update(Wrappers.<AdminRole>lambdaUpdate()
                .eq(AdminRole::getId, param.getRoleId())
                .set(ObjectUtil.isNotNull(param.getRoleName()), AdminRole::getRoleName, param.getRoleName())
                .set(ObjectUtil.isNotNull(param.getActive()), AdminRole::getActive, param.getActive())
                .set(ObjectUtil.isNotNull(param.getRemark()), AdminRole::getRemark, param.getRemark())
                .set(AdminRole::getUpdateTime, System.currentTimeMillis())
        );
        if (count <= 0) {
            log.info("{}角色更新失败", param.getRoleId());
            throw new AppException(StrUtil.format("{}角色更新失败", param.getRoleId()));
        }
        return count;
    }

    @Override
    public int deleteRole(Integer roleId) {
        checkDeleteParam(roleId);
        int count = adminRoleMapper.deleteById(roleId);
        if (count <= 0) {
            log.info("{}角色删除失败", roleId);
            throw new AppException(StrUtil.format("{}角色删除失败", roleId));
        }
        return count;
    }

    @Override
    @Transactional
    public void roleLinkeMenu(RoleLinkMenuParam param) {
        Optional.ofNullable(adminRoleMapper.selectById(param.getRoleId()))
          .orElseThrow(()->new ParamException(StrUtil.format("角色{}不存在", param.getRoleId())));

        boolean exists = adminRoleMenuMapper.exists(Wrappers.<AdminRoleMenu>lambdaQuery().eq(AdminRoleMenu::getRoleId, param.getRoleId()));
        if (exists){
            int delete = adminRoleMenuMapper.delete(Wrappers.<AdminRoleMenu>lambdaUpdate().eq(AdminRoleMenu::getRoleId, param.getRoleId()));
            log.info("{}角色删除成功,删除{}条记录", param.getRoleId(),delete);
        }
        // 校验菜单ID列表是否为空
        List<Integer> menuIds = param.getMenuIds();
        if (CollectionUtil.isEmpty(menuIds)) {
            log.info("{}角色下没有菜单信息，只是做了取消菜单功能", param.getRoleId());
            return;
        }
        param.getMenuIds().forEach(menuId -> {
            AdminRoleMenu adminRoleMenu = new AdminRoleMenu();
            adminRoleMenu.setRoleId(param.getRoleId());
            adminRoleMenu.setMenuId(menuId);
            adminRoleMenuMapper.insert(adminRoleMenu);
        });
    }

    /**
     * 删除角色参数校验
     *
     * @param roleId
     */
    private void checkDeleteParam(Integer roleId) {
        checkRoleId(roleId);
        // 删除角色前需要判断角色下是否有用户，如果有用户则不允许删除
        boolean exists = adminUserMapper.exists(Wrappers.<AdminUser>lambdaQuery().eq(AdminUser::getRoleId, roleId));
        if (exists) {
            log.info("{}角色下有用户，不允许删除", roleId);
            throw new AppException(StrUtil.format("{}角色下有用户，不允许删除", roleId));
        }
        // 判断角色下面是否分配了菜单，如果分配了，不允许删除
        boolean menuExist = adminRoleMenuMapper.exists(Wrappers.<AdminRoleMenu>lambdaQuery().eq(AdminRoleMenu::getRoleId, roleId));
        if (menuExist) {
            log.info("{}角色下关联了权限，不允许删除", roleId);
            throw new AppException(StrUtil.format("{}角色下关联了权限，请先解除关联", roleId));
        }
    }

    private void checkUpParam(UpRoleParam param) {
        Optional.ofNullable(param.getRoleId()).ifPresent(this::checkRoleId);
        Optional.ofNullable(param.getRoleName()).ifPresent(this::checkRoleName);
    }

    private void checkRoleId(Integer roleId) {
        boolean exists = adminRoleMapper.exists(Wrappers.<AdminRole>lambdaQuery().
                eq(AdminRole::getId, roleId));
        if (!exists) {
            log.info("{} 角色不存在", roleId);
            throw new ParamException(StrUtil.format("{} 角色不存在", roleId));
        }
    }

    /**
     * 检查新增参数
     *
     * @param param
     */
    private void checkInParam(InRoleParam param) {
        checkRoleName(param.getRoleName());
    }

    private void checkRoleName(String roleName) {
        boolean exists = adminRoleMapper.exists(Wrappers.<AdminRole>lambdaQuery()
                .eq(AdminRole::getRoleName, roleName));
        if (exists) {
            log.info("{}角色名重复", roleName);
            throw new ParamException(StrUtil.format("{} 角色名重复", roleName));
        }
    }


    /**
     * 类型转换
     *
     * @param adminRole
     * @return
     */
    private AdminRoleVo convert(AdminRole adminRole) {
        AdminRoleVo adminRoleVo = new AdminRoleVo();
        adminRoleVo.setRoleId(adminRole.getId());
        adminRoleVo.setRoleName(adminRole.getRoleName());
        adminRoleVo.setActive(adminRole.getActive());
        adminRoleVo.setRemark(adminRole.getRemark());
        adminRoleVo.setCreateTime(adminRole.getCreateTime());
        adminRoleVo.setUpdateTime(adminRole.getUpdateTime());
        return adminRoleVo;
    }
}

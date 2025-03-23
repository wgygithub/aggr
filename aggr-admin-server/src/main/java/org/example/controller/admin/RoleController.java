package org.example.controller.admin;

import org.example.param.role.InRoleParam;
import org.example.param.role.RoleLinkMenuParam;
import org.example.param.role.UpRoleParam;
import org.example.resp.Result;
import org.example.service.AdminRoleService;
import org.example.vo.role.AdminRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private AdminRoleService adminRoleService;

    /**
     * 角色查询
     * @return
     */
    @GetMapping
    public Result<List<AdminRoleVo>> roleQuery() {
        return Result.OK(adminRoleService.roleQuery());
    }

    /**
     * 角色新增
     * @param param
     * @return
     */
    @PostMapping
    public Result<Void> insertRole(@RequestBody @Validated InRoleParam param) {
        adminRoleService.insertRole(param);
        return Result.OK();
    }

    /**
     * 角色更新
     * @param param
     * @return
     */
    @PutMapping
    public Result<Void> updateRole(@RequestBody @Validated UpRoleParam param) {
        adminRoleService.updateRole(param);
        return Result.OK();
    }

    /**
     * 角色删除
     * @param roleId
     * @return
     */
    @DeleteMapping("/{roleId}")
    public Result<Void> deleteRole(@PathVariable("roleId") Integer roleId) {
        adminRoleService.deleteRole(roleId);
        return Result.OK();
    }

    /**
     * 角色关联菜单
     * @return
     */
    @PostMapping("/link")
    public Result<Void> roleLinkeMenu(@RequestBody @Validated RoleLinkMenuParam param){
        adminRoleService.roleLinkeMenu(param);
        return Result.OK();
    }

}

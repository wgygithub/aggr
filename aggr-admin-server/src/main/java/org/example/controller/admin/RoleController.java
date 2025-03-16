package org.example.controller.admin;

import org.example.param.role.InRoleParam;
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

    @GetMapping
    public Result<List<AdminRoleVo>> roleQuery() {
        return Result.OK(adminRoleService.roleQuery());
    }

    @PostMapping
    public Result<Void> insertRole(@RequestBody @Validated InRoleParam param) {
        adminRoleService.insertRole(param);
        return Result.OK();
    }

    @PutMapping
    public Result<Void> updateRole(@RequestBody @Validated UpRoleParam param) {
        adminRoleService.updateRole(param);
        return Result.OK();
    }

    @DeleteMapping("/{roleId}")
    public Result<Void> deleteRole(@PathVariable("roleId") Integer roleId) {
        adminRoleService.deleteRole(roleId);
        return Result.OK();
    }

}

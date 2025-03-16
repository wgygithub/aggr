package org.example.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.param.user.InUserParam;
import org.example.param.user.ResetPwdParam;
import org.example.param.user.UpUserParam;
import org.example.param.user.UserPageQueryParam;
import org.example.resp.Result;
import org.example.service.AdminUserService;
import org.example.vo.user.AdminUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin-user")
public class UserController {

    @Autowired
    private AdminUserService adminUserService;

    @GetMapping
    public Result<IPage<AdminUserVo>> userQuery(UserPageQueryParam userPageQueryParam) {
        return Result.OK(adminUserService.userQuery(userPageQueryParam));
    }

    @PostMapping
    public Result<Void> insertUser(@RequestBody @Validated InUserParam param) {
        adminUserService.insertUser(param);
        return Result.OK();
    }

    @PutMapping
    public Result<Void> updateUser(@RequestBody @Validated UpUserParam param) {
        adminUserService.updateUser(param);
        return Result.OK();
    }

    @DeleteMapping("/{userId}")
    public Result<Void> deleteUser(@PathVariable("userId") Integer userId) {
        adminUserService.deleteUser(userId);
        return Result.OK();
    }

    @PutMapping("/resetPwd")
    public Result<Void> resetPwd(@RequestBody @Validated ResetPwdParam param) {
        adminUserService.resetPwd(param);
        return Result.OK();
    }
}

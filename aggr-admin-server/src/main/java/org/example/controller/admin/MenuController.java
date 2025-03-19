package org.example.controller.admin;

import org.example.param.menu.InMenuParam;
import org.example.param.menu.UpMenuParam;
import org.example.resp.Result;
import org.example.service.AdminMenuService;
import org.example.vo.menu.AdminMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private AdminMenuService adminMenuService;

    @GetMapping
    public Result<List<AdminMenuVo>> queryMenu() {
        return Result.OK(adminMenuService.queryMenu(null));
    }

    @PostMapping
    public Result<Void> insertMenu(@RequestBody @Validated InMenuParam param) {
        adminMenuService.insertMenu(param);
        return Result.OK();
    }

    @PutMapping
    public Result<Void> updateMenu(@RequestBody @Validated UpMenuParam param) {
        adminMenuService.updateMenu(param);
        return Result.OK();
    }

    @DeleteMapping("/{menuId}")
    public Result<Void> deleteMenu(@PathVariable("menuId") Integer menuId) {
        adminMenuService.deleteMenu(menuId);
        return Result.OK();
    }
}

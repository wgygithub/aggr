package org.example.controller;

import org.example.resp.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 公共控制器
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    @GetMapping
    public Result<Map<String, String>> index() {
        HashMap<String, String> map = new HashMap<>();

        map.putIfAbsent("name", "yipay");
        return Result.OK(map);
    }

}

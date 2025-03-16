package org.example;

import org.example.entity.AdminUser;
import org.example.mapper.AdminUserMapper;
import org.example.util.PasswordUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestMain {
    @Autowired
    private AdminUserMapper amdinUser;

    @Test
    public void test() {
        List<AdminUser> adminUsers = amdinUser.selectList(null);
        adminUsers.forEach(item -> {
            System.out.println(PasswordUtils.matches("123456", item.getPassword()));
        });
    }


    @Test
    public void test2() {
        AdminUser adminUser = new AdminUser();
        adminUser.setActive(true);
        adminUser.setPassword(PasswordUtils.encode("123456"));
        adminUser.setRoleId(1);
        adminUser.setUserName("zhangsan");
        amdinUser.insert(adminUser);
    }
}

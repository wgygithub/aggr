package org.example.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdminRoleMenuMapperTest {

    @Autowired
    private AdminRoleMenuMapper adminRoleMenuMapper;

    @Test
    public void testQueryMenuByRoleId() {
        int i = adminRoleMenuMapper.existsRecurByMenuId(5);
        System.out.println(i);
    }

}

package org.example.service.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.param.user.UserPageQueryParam;
import org.example.service.AdminUserService;
import org.example.vo.user.AdminUserVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdminUserServiceTest {

    @Autowired
    private AdminUserService adminUserService;

    @Test
    public void testUserQuery() {
        UserPageQueryParam userPageQueryParam = new UserPageQueryParam();
        userPageQueryParam.setPageNum(1);
        userPageQueryParam.setPageSize(10);
        userPageQueryParam.setUserName("zhangsan");
        IPage<AdminUserVo> adminUserVoIPage = adminUserService.userQuery(userPageQueryParam);
        System.out.println(adminUserVoIPage);
    }

}

package org.example.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.example.domain.FrontUser;
import org.example.mapper.FrontUserMapper;
import org.example.service.FrontUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author admin
 * @description 针对表【front_user(前端用户表)】的数据库操作Service实现
 * @createDate 2025-04-01 21:23:54
 */
@Service
public class FrontUserServiceImpl implements FrontUserService {

  @Autowired
  private FrontUserMapper frontUserMapper;

  @Override
  public boolean checkUserNameIsUnique(String username) {
    return frontUserMapper.exists(Wrappers.
      lambdaQuery(FrontUser.class)
      .eq(FrontUser::getUserName, username));
  }
}





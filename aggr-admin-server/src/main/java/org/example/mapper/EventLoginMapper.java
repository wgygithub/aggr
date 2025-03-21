package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.example.domain.EventLogin;
import org.example.util.DateUtil;

/**
 * @author admin
 * @description 针对表【event_login】的数据库操作Mapper
 * @createDate 2025-03-21 20:52:32
 * @Entity org.example.domain.EventLogin
 */
public interface EventLoginMapper extends BaseMapper<EventLogin> {

  default Long selectLoginFailTimes(String username) {

    return selectCount(Wrappers.<EventLogin>lambdaQuery()
      .eq(EventLogin::getUsername, username)
      .eq(EventLogin::getSuccess, false)
      .lt(EventLogin::getCreateTime, DateUtil.minusMinutes(3))
    );
  }
}





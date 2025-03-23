package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.annotations.DbSource;
import org.example.constant.DataSourceConstants;
import org.example.domain.AuthToken;

/**
 * @author admin
 * @description 针对表【auth_token】的数据库操作Mapper
 * @createDate 2025-03-20 23:39:41
 * @Entity generator.domain.AuthToken
 */
public interface AuthTokenMapper extends BaseMapper<AuthToken> {

  @DbSource(value = DataSourceConstants.DEFAULT_SLAVE_KEY)
  default AuthToken getToken(String token) {
    return this.selectById(token);
  }
}





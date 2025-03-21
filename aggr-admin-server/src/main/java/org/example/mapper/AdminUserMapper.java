package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.ibatis.annotations.Param;
import org.example.annotations.DbSource;
import org.example.constant.DataSourceConstants;
import org.example.entity.AdminUser;
import org.example.param.user.UserPageQueryParam;
import org.example.vo.user.AdminUserVo;

public interface AdminUserMapper extends BaseMapper<AdminUser> {

  /**
   * 获取用户信息
   *
   * @param page
   * @param param
   * @return
   */
  @DbSource(value = DataSourceConstants.DEFAULT_SLAVE_KEY)
  IPage<AdminUserVo> userQuery(@Param("page") IPage<AdminUser> page,
                               @Param("param") UserPageQueryParam param);

  @DbSource(value = DataSourceConstants.DEFAULT_SLAVE_KEY)
  default AdminUser selectByUsername(String username) {
    return selectOne(Wrappers.<AdminUser>lambdaQuery().eq(AdminUser::getUserName, username));
  }

  /**
   * 更新用户状态
   *
   * @param userId
   * @param active
   */
  default void updateActiveById(Integer userId, Boolean active) {
    update(null, Wrappers.<AdminUser>lambdaUpdate()
      .set(AdminUser::getActive, active)
      .eq(AdminUser::getId, userId));
  }
}

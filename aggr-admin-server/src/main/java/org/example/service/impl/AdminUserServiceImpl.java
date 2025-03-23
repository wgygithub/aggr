package org.example.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.example.constant.AdminUserConstants;
import org.example.context.vo.UserInfoVo;
import org.example.entity.AdminUser;
import org.example.exceptions.AppException;
import org.example.exceptions.ParamException;
import org.example.mapper.AdminRoleMapper;
import org.example.mapper.AdminUserMapper;
import org.example.param.user.InUserParam;
import org.example.param.user.ResetPwdParam;
import org.example.param.user.UpUserParam;
import org.example.param.user.UserPageQueryParam;
import org.example.service.AdminUserService;
import org.example.util.PasswordUtils;
import org.example.vo.user.AdminUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Service
public class AdminUserServiceImpl implements AdminUserService {

  @Autowired
  private AdminUserMapper adminUserMapper;

  @Autowired
  private AdminRoleMapper adminRoleMapper;

  @Override
  public IPage<AdminUserVo> userQuery(UserPageQueryParam userPageQueryParam) {
    return adminUserMapper.userQuery(userPageQueryParam.toPage(), userPageQueryParam);
  }

  @Override
  public int insertUser(InUserParam param) {
    checkInParam(param);
    AdminUser adminUser = new AdminUser();
    adminUser.setUserName(param.getUserName());
    adminUser.setRoleId(param.getRoleId());
    Boolean active = ObjectUtil.isNull(adminUser.getActive()) ? Boolean.TRUE : param.getActive();
    adminUser.setActive(active);
    String rawPassword = ObjectUtil.isEmpty(param.getPassword()) ? AdminUserConstants.DEFAULT_PASSWORD : param.getPassword();
    adminUser.setPassword(PasswordUtils.encode(rawPassword));

    int insert = adminUserMapper.insert(adminUser);
    if (insert <= 0) {
      log.info("{}用户插入失败", param.getUserName());
      throw new AppException("新增用户失败");
    }
    return insert;
  }

  @Override
  public int updateUser(UpUserParam param) {
    //检查参数
    checkUpParam(param);
    AdminUser adminUser = Optional.ofNullable(adminUserMapper.selectById(param.getUserId())).orElseThrow(() -> new ParamException("用户不存在"));

    int count = adminUserMapper.update(Wrappers.<AdminUser>lambdaUpdate()
      .eq(AdminUser::getId, adminUser.getId())
      .set(ObjectUtil.isNotNull(param.getPassword()), AdminUser::getPassword,
        PasswordUtils.encode(param.getPassword()))
      .set(ObjectUtil.isNotNull(param.getRoleId()), AdminUser::getRoleId, param.getRoleId())
      .set(ObjectUtil.isNotNull(param.getActive()), AdminUser::getActive, param.getActive())
      .set(AdminUser::getUpdateTime, System.currentTimeMillis()));

    if (count <= 0) {
      log.info("{}用户更新失败", adminUser.getUserName());
      throw new AppException(StrUtil.format("{} 用户更新失败", adminUser.getUserName()));
    }
    return count;
  }

  private void checkUpParam(UpUserParam param) {
    Optional.ofNullable(param.getRoleId()).ifPresent(this::checkRoleId);
  }

  @Override
  public int deleteUser(Integer userId) {
    Optional.ofNullable(userId).orElseThrow(() -> new ParamException("id不能为空"));
    int count = adminUserMapper.deleteById(userId);
    if (count <= 0) {
      log.info("{}用户删除失败", userId);
      throw new AppException(StrUtil.format("用户删除失败", userId));
    }
    return count;
  }

  @Override
  public int resetPwd(ResetPwdParam param) {
    AdminUser adminUser = Optional.ofNullable(adminUserMapper.selectById(param.getUserId())).orElseThrow(() -> new ParamException(StrUtil.format("{}用户不存在", param.getUserId())));
    checkResetPwdParam(adminUser, param);

    int count = adminUserMapper.update(Wrappers.<AdminUser>lambdaUpdate().eq(AdminUser::getId,
        adminUser.getId()).set(AdminUser::getPassword,
        PasswordUtils.encode(param.getNewPwd()))
      .set(AdminUser::getUpdateTime, System.currentTimeMillis()));
    if (count <= 0) {
      log.info("{}用户重置密码失败", param.getUserId());
      throw new AppException(StrUtil.format("{}用户重置密码失败", adminUser.getUserName()));
    }
    return count;
  }

  @Override
  public UserInfoVo userInfo(Integer userId) {
    AdminUser adminUser = adminUserMapper.selectById(userId);
    return Optional.ofNullable(adminUser)
      .map(this::covertToUserInfoVo)
      .orElse(null);
  }

  private UserInfoVo covertToUserInfoVo(AdminUser adminUser) {
    UserInfoVo userInfoVo = new UserInfoVo();
    userInfoVo.setUserName(adminUser.getUserName());
    userInfoVo.setActive(adminUser.getActive());
    userInfoVo.setUserId(adminUser.getId());
    return userInfoVo;
  }

  private void checkResetPwdParam(AdminUser adminUser, ResetPwdParam param) {
    if (!PasswordUtils.matches(param.getOldPwd(), adminUser.getPassword())) {
      log.info("{}用户密码错误", adminUser.getUserName());
      throw new ParamException("旧密码错误");
    }
    //todo 密码正则校验
  }

  private void isValidPassword(String password) {
    // 密码强度规则正则表达式
    final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$";
    final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
    if (!pattern.matcher(password).matches()) {
      throw new ParamException("password is not valid");
    }
  }

  /**
   * 检查用户是否有重复
   * 检查角色是否存在
   *
   * @param param
   */
  private void checkInParam(InUserParam param) {
    checkRoleId(param.getRoleId());
    checkUserName(param.getUserName());
  }

  private void checkRoleId(Integer roleId) {
    Optional.ofNullable(adminRoleMapper.selectById(roleId)).orElseThrow(() -> {
      log.info("{}角色不存在", roleId);
      return new ParamException(StrUtil.format("{} 角色不存在", roleId));
    });
  }

  private void checkUserName(String userName) {
    boolean exists = adminUserMapper.exists(Wrappers.<AdminUser>lambdaQuery().eq(AdminUser::getUserName, userName));
    if (exists) {
      log.info("{}用户名重复", userName);
      throw new ParamException(StrUtil.format("{} 用户名重复", userName));
    }
  }
}

package org.example.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.config.SystemConfig;
import org.example.domain.EventLogin;
import org.example.entity.AdminUser;
import org.example.entity.AuthToken;
import org.example.exceptions.AppException;
import org.example.exceptions.ParamException;
import org.example.mapper.AdminUserMapper;
import org.example.mapper.EventLoginMapper;
import org.example.param.auth.LoginParam;
import org.example.service.AuthCaptchaService;
import org.example.service.AuthService;
import org.example.service.AuthTokenService;
import org.example.util.PasswordUtils;
import org.example.vo.auth.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
  @Autowired
  private SystemConfig systemConfig;

  @Autowired
  private AuthCaptchaService authCaptchaService;

  @Autowired
  private AdminUserMapper adminUserMapper;

  @Autowired
  private AuthTokenService authTokenService;

  @Autowired
  private EventLoginMapper eventLoginMapper;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public LoginVo login(LoginParam loginParam) {
    // 校验登录参数
    checkLoginParam(loginParam);
    // 查询用户信息
    AdminUser user = adminUserMapper.selectByUsername(loginParam.getUsername());
    if (ObjectUtil.isNull(user)) {
      recordEventLogin(loginParam.getUsername(), Boolean.FALSE, "user not exist");
      log.warn("用户{}不存在", loginParam.getUsername());
      throw new AppException(StrUtil.format("user {} not exist", loginParam.getUsername()));
    }

    if (!user.getActive()) {
      recordEventLogin(loginParam.getUsername(), Boolean.FALSE, "user blocked");
      throw new AppException("User blocked, please contact our administrator.");
    }

    // 验证密码
    boolean matches = PasswordUtils.matches(loginParam.getPassword(), user.getPassword());
    if (!matches) {
      handlePasswordError(loginParam.getUsername(), user);
    }
    // 生成并保存令牌
    AuthToken authToken = authTokenService.saveToken(user.getId());

    // 记录成功登录事件
    recordEventLogin(loginParam.getUsername(), Boolean.TRUE, "login success");

    // 构建返回结果
    LoginVo loginVo = new LoginVo();
    loginVo.setAuthToken(authToken.getToken());
    loginVo.setDeviceToken(authToken.getDeviceToken());
    loginVo.setExpiredAt(authToken.getExpireTime());
    return loginVo;
  }

  /**
   * 处理密码错误逻辑
   *
   * @param username 用户名
   * @param user     用户信息
   */
  private void handlePasswordError(String username, AdminUser user) {
    Long failCount = eventLoginMapper.selectLoginFailTimes(username);
    if (failCount == null) {
      failCount = 0L;
    }

    // 记录失败事件
    recordEventLogin(username, Boolean.FALSE, "password error");

    // 判断是否超过最大尝试次数
    if (failCount > 5) {
      log.warn("用户{}尝试登录超过5次，账户已被冻结", username);
      adminUserMapper.updateActiveById(user.getId(), Boolean.FALSE);
      throw new AppException("Account locked due to too many failed attempts.");
    }

    log.warn("用户{}密码错误", username);
    throw new AppException("password is error");
  }

  /**
   * 登录参数校验
   *
   * @param loginParam
   */
  private void checkLoginParam(LoginParam loginParam) {
    //校验图形验证码
    checkCaprcha(loginParam.getUuid(), loginParam.getCaptchaCode());
  }

  private void checkCaprcha(String uuid, String captchaCode) {
    if (StrUtil.isBlank(uuid) || StrUtil.isBlank(captchaCode)) {
      throw new ParamException("验证码参数缺失");
    }

    if (!systemConfig.isCaptchaEnable()) {
      log.info("验证码未开启");
      return;
    }

    if (!authCaptchaService.captchaCheck(uuid, captchaCode)) {
      log.warn("验证码验证错误");
      throw new ParamException("验证码验证错误");
    }
  }

  /**
   * 记录登录事件
   *
   * @param username 用户名
   * @param success  是否成功
   * @param remark   备注信息
   */
  private void recordEventLogin(String username, Boolean success, String remark) {
    EventLogin event = new EventLogin();
    event.setUsername(username);
    event.setSuccess(success);
    event.setRemark(remark);
    eventLoginMapper.insert(event);
  }

}

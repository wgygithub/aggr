package org.example.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.FrontUser;
import org.example.exceptions.AppException;
import org.example.exceptions.ParamException;
import org.example.mapper.FrontUserMapper;
import org.example.param.user.LoginParam;
import org.example.param.user.RegisterParam;
import org.example.service.FrontUserService;
import org.example.util.DateUtil;
import org.example.util.IdUtils;
import org.example.util.PasswordUtils;
import org.example.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author admin
 * @description 针对表【front_user(前端用户表)】的数据库操作Service实现
 * @createDate 2025-04-01 21:23:54
 */
@Service
@Slf4j
public class FrontUserServiceImpl implements FrontUserService {

  @Autowired
  private FrontUserMapper frontUserMapper;

  /**
   * 一天内密码错误次数超过5次，需要被冻结一天
   *
   * @param loginParam
   * @return
   */
  @Override
  public LoginVo login(LoginParam loginParam) {
    FrontUser user = Optional.ofNullable(
      getUserByUserName(loginParam.getUserName())).orElseThrow(() ->
      new ParamException("username is illeagl"));
    String password = user.getPassword();
    if (!PasswordUtils.matches(loginParam.getPassword(), password)) {
      log.warn("user:{} login failed, password is illegal", loginParam.getUserName());
      throw new ParamException("password is illegal");
    }


    return null;
  }


  private boolean checkUserNameIsUnique(String username) {
    return frontUserMapper.exists(Wrappers.
      lambdaQuery(FrontUser.class)
      .eq(FrontUser::getUserName, username));
  }

  @Override
  public FrontUser getUserByUserName(String userName) {
    Optional.ofNullable(userName).orElseThrow(() -> new ParamException("username is null"));
    return frontUserMapper.selectOne(Wrappers.lambdaQuery(FrontUser.class)
      .eq(FrontUser::getUserName, userName));
  }

  @Override
  public void register(RegisterParam registerParam) {
    if (checkUserNameIsUnique(registerParam.getUserName())) {
      log.info("user register, but {} is exist", registerParam.getUserName());
      throw new ParamException(StrUtil.format("username {} is exist",
        registerParam.getUserName()));
    }
    FrontUser frontUser = genFrontUser(registerParam);
    //用户注册，需要将资金账户初始化？
    frontUserMapper.insert(frontUser);
  }

  /**
   * 邀请人逻辑：
   * 等用户首充，邀请人可以获得邀请人的佣金
   */
  private FrontUser genFrontUser(RegisterParam registerParam) {
    FrontUser frontUser = new FrontUser();
    frontUser.setUserName(registerParam.getUserName());
    frontUser.setPassword(PasswordUtils.encode(registerParam.getPassword()));
    frontUser.setRegisterTime(DateUtil.current());
    frontUser.setPhone(registerParam.getPhone());
    frontUser.setEmail(registerParam.getEmail());
    frontUser.setStatus(Boolean.TRUE);
    // 处理邀请人逻辑

    handleInviterCode(frontUser, registerParam);
    //生成用户的userid
    String userId = generateUniqueUserId();
    frontUser.setUserId(userId);
    //生成个人邀请码
    String inviterCode = generateUniqueInviterCode(userId);
    frontUser.setInviterCode(inviterCode);
    return frontUser;
  }

  private String generateUniqueUserId() {
    String userId;
    int count = 0;
    do {
      if (count == 3) {
        log.warn("Failed to generate unique userId after 3 attempts");
        throw new AppException("Fail to Create User, Can Not To Gen User");
      }
      userId = IdUtils.genUserId();
      count += 1;
    } while (!isUserIdUnique(userId));
    return userId;
  }

  /**
   * 判断用户id是否唯一
   *
   * @param userId
   * @return
   */
  private boolean isUserIdUnique(String userId) {
    return !frontUserMapper.exists(Wrappers.
      lambdaQuery(FrontUser.class)
      .eq(FrontUser::getUserId, userId));
  }

  /**
   * 先按照规则生成邀请码，如果邀请码存在，则随机生成
   *
   * @param userId
   * @return
   */
  private String generateUniqueInviterCode(String userId) {
    String inviterCode = IdUtils.genInviterCode(userId);
    if (isInviterCodeAlreadyUsed(inviterCode)) {
      log.info("userId:{},inviterCode:{} is exist,generate new one",
        userId, inviterCode);
      return IdUtils.getSimpleUUID(8);
    }
    return inviterCode;
  }

  private boolean isInviterCodeAlreadyUsed(String inviterCode) {
    FrontUser userByInviterCode = getUserByInviterCode(inviterCode);
    return ObjectUtil.isNotNull(userByInviterCode);
  }

  private void handleInviterCode(FrontUser frontUser, RegisterParam registerParam) {
    if (StrUtil.isBlank(registerParam.getInviterCode())) {
      return;
    }

    FrontUser userByInviterCode = getUserByInviterCode(registerParam.getInviterCode());
    if (ObjectUtil.isNull(userByInviterCode)) {
      log.warn("Inviter code not found: {}", registerParam.getInviterCode());
      throw new ParamException(StrUtil.format("Cannot find inviterCode: {} user",
        registerParam.getInviterCode()));
    }

    frontUser.setInviterUserId(userByInviterCode.getUserId());
  }

  @Override
  public FrontUser getUserByInviterCode(String inviterCode) {
    return frontUserMapper.selectOne(Wrappers.
      lambdaQuery(FrontUser.class)
      .eq(FrontUser::getInviterCode, inviterCode));
  }
}





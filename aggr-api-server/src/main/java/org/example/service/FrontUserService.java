package org.example.service;

/**
 * @author admin
 * @description 针对表【front_user(前端用户表)】的数据库操作Service
 * @createDate 2025-04-01 21:23:54
 */
public interface FrontUserService {
  boolean checkUserNameIsUnique(String username);

}

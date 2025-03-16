package org.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.param.user.InUserParam;
import org.example.param.user.ResetPwdParam;
import org.example.param.user.UpUserParam;
import org.example.param.user.UserPageQueryParam;
import org.example.vo.user.AdminUserVo;

public interface AdminUserService {

    //获取用户列表信息
    IPage<AdminUserVo> userQuery(UserPageQueryParam userPageQueryParam);

    /**
     * 用户新增
     *
     * @param param
     * @return
     */
    int insertUser(InUserParam param);

    /**
     * 更新用户
     *
     * @param param
     * @return
     */
    int updateUser(UpUserParam param);

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    int deleteUser(Integer userId);

    /**
     * 修改密码
     *
     * @param param
     * @param isCurrentUser
     * @return
     */
    int resetPwd(ResetPwdParam param);
}

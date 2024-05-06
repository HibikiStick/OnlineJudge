package com.example.ojbackend.servcie;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ojbackend.entity.User;
import com.example.ojbackend.entity.vo.LoginUserVO;
import com.example.ojbackend.entity.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService extends IService<User> {
    /**
     * 获取当前登录信息
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 对User信息进行脱敏
     * @param user
     * @return DTO
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 用户登录
     * @param userAccount 账号
     * @param userPassword 密码
     * @param request
     * @return 脱敏后的信息
     */
    LoginUserVO userLogin(String userAccount,String userPassword,HttpServletRequest request);

    /**
     * 获取用户登录Vo
     */
    UserVO getUserVo(User user);

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 是否为管理员
     *
     * @param user
     * @return
     */
    boolean isAdmin(User user);
}

package com.example.ojbackend.controller;

import com.example.ojbackend.common.BaseResponse;
import com.example.ojbackend.common.ErrorCode;
import com.example.ojbackend.common.ResultUtils;
import com.example.ojbackend.entity.User;
import com.example.ojbackend.entity.dto.user.UserLoginRequest;
import com.example.ojbackend.entity.vo.LoginUserVO;
import com.example.ojbackend.exception.BusinessException;
import com.example.ojbackend.servcie.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/get/login")
    public BaseResponse<LoginUserVO> getLoginUser(HttpServletRequest request){
        User user = userService.getLoginUser(request);
        return ResultUtils.success(userService.getLoginUserVO(user));
    }

    @PostMapping("/login")
    public BaseResponse<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        if (userLoginRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (StringUtils.isAnyBlank(userLoginRequest.getUserAccount(),userLoginRequest.getUserPassword())){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(userService.userLogin(userLoginRequest.getUserAccount(), userLoginRequest.getUserPassword(),request));
    }
}

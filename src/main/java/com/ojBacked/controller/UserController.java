package com.ojBacked.controller;


import com.ojBacked.comment.Enum.ErrorCode;
import com.ojBacked.comment.com.Result;
import com.ojBacked.entity.User;
import com.ojBacked.exception.BusinessException;
import com.ojBacked.service.IUserService;
import com.ojBacked.comment.requst.UserLoginRequest;
import com.ojBacked.comment.vo.UserVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author author
 * @since 2024-09-14
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;


    //    @Guigu
    @GetMapping("GetUser")
    public Result<UserVo> getUser(HttpServletRequest request){
        String token = request.getHeader("token");
        if(token==null){
            return Result.fail("未登录");
        }
        long id = Long.parseLong(token);
        User user = userService.selectById(id);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user,userVo);
        return Result.ok(userVo);
    }
    @GetMapping("/register")
    public Result<String> register(){
        return userService.register();
    }
    @PostMapping("/login")
    public Result<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUserVO = userService.userLogin(userAccount, userPassword);
        return Result.ok(loginUserVO);
    }

    @GetMapping("/select")
    public Result<List<User>> select(@PathParam("current") Long current, @PathParam("size") Long size){
        return userService.select(current,size);
    }







}

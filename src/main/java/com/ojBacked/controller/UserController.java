package com.ojBacked.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("GetUser")
    public String getUser(){
        return "Swagger user....";
    }

}

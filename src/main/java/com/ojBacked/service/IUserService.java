package com.ojBacked.service;

import com.ojBacked.comment.com.Result;
import com.ojBacked.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author author
 * @since 2024-09-14
 */
public interface IUserService extends IService<User> {


    Result<String> register();

    Result<List<User>> select(Long current, Long size);

    User userLogin(String userAccount, String userPassword);

    User selectById(Long id);
}

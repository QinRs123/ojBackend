package com.ojBacked.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ojBacked.comment.Enum.ErrorCode;
import com.ojBacked.comment.com.Result;
import com.ojBacked.entity.User;
import com.ojBacked.exception.BusinessException;
import com.ojBacked.mapper.UserMapper;
import com.ojBacked.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-09-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public Result<String> register() {
        User user = new User();
        user.setUserAccount("qrs");
        user.setUserPassword("123");
        user.setUserAvatar("user");
        user.setUpdateTime(LocalDateTime.now());
        user.setCreateTime(LocalDateTime.now());
        int insert = userMapper.insert(user);
        if (insert!=0){
            return Result.ok();
        }
        return Result.fail();
    }

    @Override
    public Result<List<User>> select(Long current, Long size) {
        Page<User> page=new Page<>();
        page.setCurrent(current);
        page.setSize(size);

        List<User> userList = userMapper.selectPageVo(page);
        if(userList==null){
            return Result.fail();
        }
        return Result.ok(userList);
    }

    @Override
    public User userLogin(String userAccount, String userPassword) {
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        LambdaQueryWrapper<User> wrapper =new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserAccount,userAccount);
        wrapper.eq(User::getUserPassword,userPassword);
        User user = userMapper.selectOne(wrapper);
        if(user==null){
            throw new BusinessException(411,"用户不存在或密码错误");
        }
        return user;
    }

    @Override
    public User selectById(Long id) {
        User user = userMapper.selectById(id);
        return user;
    }
}

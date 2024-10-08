package com.ojBacked.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ojBacked.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-09-14
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {


    List<User> selectPageVo(Page<User> page);
}

package com.ojBacked.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ojBacked.entity.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ojBacked.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 题目 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-09-17
 */

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {


    List<Question> selectPageVo(Page<User> page);
}

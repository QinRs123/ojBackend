package com.ojBacked.mapper;

import com.ojBacked.entity.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 帖子 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-09-15
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {

}

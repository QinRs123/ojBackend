package com.ojBacked.service.impl;

import com.ojBacked.entity.Post;
import com.ojBacked.mapper.PostMapper;
import com.ojBacked.service.IPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 帖子 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-09-15
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements IPostService {

}

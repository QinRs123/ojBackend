package com.ojBacked.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ojBacked.comment.com.Result;
import com.ojBacked.comment.requst.QuestionSubmitRequest;
import com.ojBacked.entity.QuestionSubmit;
import com.ojBacked.judge.JudgeService;
import com.ojBacked.mapper.QuestionSubmitMapper;
import com.ojBacked.service.IQuestionSubmitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

/**
 * <p>
 * 题目提交 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-09-17
 */
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit> implements IQuestionSubmitService {

    @Autowired
    private QuestionSubmitMapper questionSubmitMapper;

    @Resource
    @Lazy
    private JudgeService judgeService;

    @Override
    public Result<String> add(QuestionSubmitRequest questionSubmitRequest) {
        /**
         * 为了防止用户多次提交，需要进行限制
         */
        LambdaQueryWrapper<QuestionSubmit> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(QuestionSubmit::getUserId,questionSubmitRequest.getUserId());
        wrapper.eq(QuestionSubmit::getQuestionId,questionSubmitRequest.getQuestionId());
        wrapper.eq(QuestionSubmit::getStatus,0);
        QuestionSubmit isExist = questionSubmitMapper.selectOne(wrapper);
        if(isExist!=null){
            return Result.fail("改题目还在判题中，判题过程无法多次提交相同题目");
        }

        //添加提交题目到数据库
        QuestionSubmit questionSubmit = new QuestionSubmit();
        BeanUtils.copyProperties(questionSubmitRequest,questionSubmit);
        questionSubmit.setJudgeInfo(null);
        questionSubmit.setCreateTime(LocalDateTime.now());
        questionSubmit.setUpdateTime(LocalDateTime.now());
        int insert = questionSubmitMapper.insert(questionSubmit);
        if(insert==0){
            return Result.fail();
        }
        QuestionSubmit exit = questionSubmitMapper.selectOne(wrapper);
        //异步执行判题服务
        CompletableFuture.runAsync(()->{
            judgeService.doJudge(exit.getId());
        });
        return Result.ok();
    }

    @Override
    public Page<QuestionSubmit> listByPageTest(QuestionSubmitRequest questionSubmitRequest,
                                               HttpServletRequest request) {
        Page<QuestionSubmit> page = new Page<>(questionSubmitRequest.getCurrent(),
                questionSubmitRequest.getPageSize());
        QueryWrapper<QuestionSubmit> wrapper = new QueryWrapper<>();
        String token = request.getHeader("token");
        long userId = Long.parseLong(token);
        System.out.println(userId);
        wrapper.eq("userId",userId);
        wrapper.orderByDesc("id");
        Page<QuestionSubmit> result = questionSubmitMapper.selectPage(page, wrapper);
        return result;
    }
}

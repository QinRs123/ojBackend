package com.ojBacked.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ojBacked.comment.com.Result;
import com.ojBacked.comment.requst.QuestionRequest;
import com.ojBacked.comment.vo.QuestionVo;
import com.ojBacked.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ojBacked.entity.QuestionSubmit;

import java.util.List;

/**
 * <p>
 * 题目 服务类
 * </p>
 *
 * @author author
 * @since 2024-09-17
 */
public interface IQuestionService extends IService<Question> {

    QuestionSubmit doJudge(Long questionSubmitId);

    Result<String> add(QuestionRequest questionRequest);

    Result<QuestionVo> getByQuestionId(Long id);

    Wrapper<Question> getQueryWrapper(QuestionRequest questionRequest);

    Page<QuestionVo> getPostVOPage(Page<Question> postPage);

    Result<List<QuestionVo>> selectByPage(long current, long size);

    List<Question> selectByPage2(long current, long size);

    Result<String> deleteById(Long id);

    Result<String> MyUpdate(QuestionRequest questionRequest);

    Page<Question> listByPageTest(QuestionRequest questionRequest);
}

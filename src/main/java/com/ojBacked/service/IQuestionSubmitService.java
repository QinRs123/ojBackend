package com.ojBacked.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ojBacked.comment.com.Result;
import com.ojBacked.comment.requst.QuestionSubmitRequest;
import com.ojBacked.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

/**
 * <p>
 * 题目提交 服务类
 * </p>
 *
 * @author author
 * @since 2024-09-17
 */
public interface IQuestionSubmitService extends IService<QuestionSubmit> {

    Result<String> add(QuestionSubmitRequest questionSubmitRequest);

    Page<QuestionSubmit> listByPageTest(QuestionSubmitRequest questionSubmitRequest, HttpServletRequest request);
}

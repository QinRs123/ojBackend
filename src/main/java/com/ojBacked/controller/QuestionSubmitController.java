package com.ojBacked.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ojBacked.comment.com.Result;
import com.ojBacked.comment.requst.QuestionRequest;
import com.ojBacked.comment.requst.QuestionSubmitRequest;
import com.ojBacked.entity.Question;
import com.ojBacked.entity.QuestionSubmit;
import com.ojBacked.service.IQuestionSubmitService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 题目提交 前端控制器
 * </p>
 *
 * @author author
 * @since 2024-09-17
 */
@RestController
@RequestMapping("/question-submit")
public class QuestionSubmitController {

    @Autowired
    private IQuestionSubmitService questionSubmitService;

    /**
     * add
     */
    @PostMapping("/add")
    public Result<String> questionSubmitAdd(@RequestBody QuestionSubmitRequest questionSubmitRequest){
        return questionSubmitService.add(questionSubmitRequest);
    }

    /**
     * delete
     */

    /**
     * update
     */

    /**
     * select
     */
    @PostMapping("/list/page/test")
    public Result<Page<QuestionSubmit>> listQuestionSubmitByPageTest(@RequestBody QuestionSubmitRequest questionSubmitRequest, HttpServletRequest request) {
        Page<QuestionSubmit> page =questionSubmitService.listByPageTest(questionSubmitRequest,request);
        return Result.ok(page);
    }
}

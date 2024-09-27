package com.ojBacked.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ojBacked.comment.com.Result;
import com.ojBacked.comment.requst.QuestionRequest;
import com.ojBacked.comment.vo.QuestionVo;
import com.ojBacked.entity.Question;
import com.ojBacked.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 题目 前端控制器
 * </p>
 *
 * @author author
 * @since 2024-09-17
 */
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private IQuestionService questionService;

    /**
     * add
     */
    @PostMapping("/add")
    public Result<String> questionAdd(@RequestBody QuestionRequest questionRequest){
        return questionService.add(questionRequest);
    }

    /**
     * delete
     */
    @GetMapping("/deleteById")
    public Result<String> deleteById(@RequestParam("id") Long id){
        return questionService.deleteById(id);
    }

    /**
     * update
     */
    @PostMapping("/update")
    public Result<String> questionUpdate(@RequestBody QuestionRequest questionRequest){
        return questionService.MyUpdate(questionRequest);
    }


    /**
     * select
     */
    @GetMapping("/getById")
    public Result<QuestionVo> questionGetById(@RequestParam("id") Long id){
        return questionService.getByQuestionId(id);
    }

    /**
     * todo : 条件查询优化，
     * @param questionRequest
     * @return
     */
    @PostMapping("/list/page")
    public Result<Page<Question>> listQuestionByPage(@RequestBody QuestionRequest questionRequest) {
        Page<Question> page =questionService.listByPageTest(questionRequest);
        return Result.ok(page);
    }

    @PostMapping("/list/pageVo")
    public Result<Page<QuestionVo>> listQuestionVoByPage(@RequestBody QuestionRequest questionRequest) {
        Page<Question> page =questionService.listByPageTest(questionRequest);
        Page<QuestionVo> postVOPage = questionService.getPostVOPage(page);
        return Result.ok(postVOPage);
    }

    @PostMapping("/list/page/test")
    public Result<Page<Question>> listQuestionByPageTest(@RequestBody QuestionRequest questionRequest) {
        Page<Question> page =questionService.listByPageTest(questionRequest);
        return Result.ok(page);
    }


}

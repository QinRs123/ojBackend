package com.ojBacked.judge;

import cn.hutool.json.JSONUtil;
import com.ojBacked.comment.Enum.ErrorCode;
import com.ojBacked.comment.dto.JudgeCase;
import com.ojBacked.judge.codesandbox.model.JudgeInfo;
import com.ojBacked.entity.Question;
import com.ojBacked.entity.QuestionSubmit;
import com.ojBacked.exception.BusinessException;
import com.ojBacked.judge.codesandbox.CodeSandBox;
import com.ojBacked.judge.codesandbox.CodeSandBoxFactory;
import com.ojBacked.judge.codesandbox.CodeSandBoxProxy;
import com.ojBacked.judge.codesandbox.model.ExecuteCodeRequest;
import com.ojBacked.judge.codesandbox.model.ExecuteCodeResponse;
import com.ojBacked.judge.codesandbox.strategy.JudgeContext;
import com.ojBacked.judge.codesandbox.strategy.JudgeStrategy;
import com.ojBacked.judge.codesandbox.strategy.DefaultJudgeStrategy;
import com.ojBacked.service.IQuestionService;
import com.ojBacked.service.IQuestionSubmitService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private IQuestionService questionService;

    @Resource
    private IQuestionSubmitService questionSubmitService;

//        1）传入题目的提交 id，获取到对应的题目、提交信息（包含代码、编程语言等）
//
//        2）如果题目提交状态不为等待中，就不用重复执行了1832688254514421762_0.3141367498084098
//
//        3）更改判题（题目提交）的状态为 “判题中”，防止重复执行，也能让用户即时看到状态
//
//        4）调用沙箱，获取到执行结果
//
//        5）根据沙箱的执行结果，设置题目的判题状态和信息
    @Override
    public QuestionSubmit doJudge(Long questionSubmitId) {
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if(questionSubmit==null){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"题目提交信息不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if(question==null){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"题目信息不存在");
        }
        //判断题目提交状态
        if(questionSubmit.getStatus()!=0){
            //不处于等待判题状态
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"题目提交状态异常");
        }
        //可以进行判题

        //更改判题状态为判题中 1
        QuestionSubmit updatQquestionSubmit = new QuestionSubmit();
        updatQquestionSubmit.setId(questionSubmit.getId());
        updatQquestionSubmit.setStatus(1);
        updatQquestionSubmit.setUpdateTime(LocalDateTime.now());
        boolean b = questionSubmitService.updateById(updatQquestionSubmit);
        if(!b){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"题目提交状态更新异常");
        }
        String code = questionSubmit.getCode();
        String language = questionSubmit.getLanguage();

        //测试用例
        String judgeCaseStr = question.getJudgeCase();
        //转成对象，在转成String数组
        List<JudgeCase> judgeCase = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCase.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        List<String> AnswerList = judgeCase.stream().map(JudgeCase::getOutput).collect(Collectors.toList());

        //数据准备
        ExecuteCodeRequest executeCodeRequest=ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();

        //利用沙箱，执行代码
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance("remote");
        CodeSandBoxProxy codeSendBoxProxy = new CodeSandBoxProxy(codeSandBox);
        //使用代理对象调用
        ExecuteCodeResponse executeCodeResponse = codeSendBoxProxy.executeCode(executeCodeRequest);
        System.out.println(executeCodeResponse);


        String judgeInfoStr = null;
        if(executeCodeResponse.getStatue()!=null&&executeCodeResponse.getStatue()!=3){
            JudgeContext judgeContext = new JudgeContext();
            judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
            judgeContext.setOutputList(executeCodeResponse.getOutputList());
            judgeContext.setInputList(AnswerList);
            judgeContext.setQuestion(question);
            judgeContext.setQuestionSubmit(questionSubmit);


            //执行判题策略，获取结果信息。
            JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
            JudgeInfo judgeInfo = judgeStrategy.doJudge(judgeContext);
            judgeInfoStr = JSONUtil.toJsonStr(judgeInfo);
            System.out.println("判题结果===》"+judgeInfoStr);

            if(judgeInfo.getMessage().equals("success")){
                updatQquestionSubmit.setStatus(2);
            }else{
                updatQquestionSubmit.setStatus(3);
            }
        }else{
            updatQquestionSubmit.setStatus(3);
            if(executeCodeResponse.getMessage()!=null){
                judgeInfoStr = "编译失败";
            }
        }
        //执行结果信息准备

        //更新提交状态
        updatQquestionSubmit.setId(questionSubmit.getId());

        updatQquestionSubmit.setUpdateTime(LocalDateTime.now());
        updatQquestionSubmit.setJudgeInfo(judgeInfoStr);
        questionSubmitService.updateById(updatQquestionSubmit);
        return updatQquestionSubmit;
    }
}

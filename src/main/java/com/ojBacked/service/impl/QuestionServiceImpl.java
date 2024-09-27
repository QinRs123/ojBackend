package com.ojBacked.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ojBacked.comment.Enum.ErrorCode;
import com.ojBacked.comment.com.Result;
import com.ojBacked.comment.dto.JudgeCase;
import com.ojBacked.comment.dto.JudgeConfig;
import com.ojBacked.comment.requst.QuestionRequest;
import com.ojBacked.comment.vo.QuestionVo;
import com.ojBacked.entity.Question;
import com.ojBacked.entity.QuestionSubmit;
import com.ojBacked.entity.User;
import com.ojBacked.exception.BusinessException;
import com.ojBacked.judge.codesandbox.CodeSandBox;
import com.ojBacked.judge.codesandbox.CodeSandBoxFactory;
import com.ojBacked.judge.codesandbox.CodeSandBoxProxy;
import com.ojBacked.judge.codesandbox.model.ExecuteCodeRequest;
import com.ojBacked.judge.codesandbox.model.ExecuteCodeResponse;
import com.ojBacked.judge.codesandbox.model.JudgeInfo;
import com.ojBacked.judge.codesandbox.strategy.DefaultJudgeStrategy;
import com.ojBacked.judge.codesandbox.strategy.JudgeContext;
import com.ojBacked.judge.codesandbox.strategy.JudgeStrategy;
import com.ojBacked.mapper.QuestionMapper;
import com.ojBacked.service.IQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ojBacked.service.IQuestionSubmitService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 题目 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-09-17
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {

    @Autowired
    private QuestionMapper questionMapper;


    @Autowired
    private IQuestionSubmitService questionSubmitService;

    @Override
    public QuestionSubmit doJudge(Long questionSubmitId) {
//        1）传入题目的提交 id，获取到对应的题目、提交信息（包含代码、编程语言等）
//
//        2）如果题目提交状态不为等待中，就不用重复执行了1832688254514421762_0.3141367498084098
//
//        3）更改判题（题目提交）的状态为 “判题中”，防止重复执行，也能让用户即时看到状态
//
//        4）调用沙箱，获取到执行结果
//
//        5）根据沙箱的执行结果，设置题目的判题状态和信息
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if(questionSubmit==null){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"题目提交信息不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = this.getById(questionId);
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

        //执行结果信息准备
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setOutputList(executeCodeResponse.getOutputList());
        judgeContext.setInputList(inputList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);

        //执行判题策略，获取结果信息。
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        JudgeInfo judgeInfo = judgeStrategy.doJudge(judgeContext);
        String judgeInfoStr = JSONUtil.toJsonStr(judgeInfo);

        //更新提交状态
        updatQquestionSubmit.setId(questionSubmit.getId());
        if(judgeInfo.getMessage().equals("success")){
            updatQquestionSubmit.setStatus(2);
        }else{
            updatQquestionSubmit.setStatus(3);
        }
        updatQquestionSubmit.setUpdateTime(LocalDateTime.now());
        updatQquestionSubmit.setJudgeInfo(judgeInfoStr);
        questionSubmitService.updateById(updatQquestionSubmit);
        return updatQquestionSubmit;
    }
    @Override
    public Result<String> add(QuestionRequest questionRequest) {
//        System.out.println(questionRequest);
        Question question = new Question();
        BeanUtils.copyProperties(questionRequest,question);
        /**
         * 处理JudgeCase题目用例
         */
        List<JudgeCase> judgeCase = questionRequest.getJudgeCase();
        //转成JSON字符串
        Gson gson = new Gson();
        String jCase = gson.toJson(judgeCase);

        /**
         * 处理题目JudgeConfig 题目限制
         */
        JudgeConfig judgeConfig = questionRequest.getJudgeConfig();
        String jConfig = gson.toJson(judgeConfig);

        List<String> tags = questionRequest.getTags();
        String QTags = gson.toJson(tags);

        question.setJudgeCase(jCase);
        question.setJudgeConfig(jConfig);
        question.setCreateTime(LocalDateTime.now());
        question.setUpdateTime(LocalDateTime.now());
        question.setTags(QTags);
        /**
         * 用户id 这里先写死
         */
        question.setUserId((long)1);
//        System.out.println("question==="+question);

        int insert = questionMapper.insert(question);
        if(insert ==1){
            return Result.ok();
        }
        return Result.fail();
    }
    public Question toQuestion(QuestionRequest questionRequest){
//        System.out.println(questionRequest);
        Question question = new Question();
        BeanUtils.copyProperties(questionRequest,question);
        /**
         * 处理JudgeCase题目用例
         */
        List<JudgeCase> judgeCase = questionRequest.getJudgeCase();
        //转成JSON字符串
        Gson gson = new Gson();
        String jCase = gson.toJson(judgeCase);

        /**
         * 处理题目JudgeConfig 题目限制
         */
        JudgeConfig judgeConfig = questionRequest.getJudgeConfig();
        String jConfig = gson.toJson(judgeConfig);

        List<String> tags = questionRequest.getTags();
        String QTags = gson.toJson(tags);

        question.setJudgeCase(jCase);
        question.setJudgeConfig(jConfig);
        question.setTags(QTags);

//        System.out.println("question==="+question);
        return question;
    }

    @Override
    public Result<QuestionVo> getByQuestionId(Long id) {
        LambdaQueryWrapper<Question> wrapper =new LambdaQueryWrapper<>();
        wrapper.eq(Question::getId,id);

        Question question = questionMapper.selectOne(wrapper);
//        System.out.println(question);
        if(question==null){
            throw new BusinessException(430,"题目不存在");
        }
        QuestionVo questionVo = new QuestionVo();

        BeanUtils.copyProperties(question,questionVo);
        String judgeCase = question.getJudgeCase();
        String judgeConfig = question.getJudgeConfig();
        String tags = question.getTags();

        /**
         * 将JSON字符串数据处理为对象
         */
        Gson gson = new Gson();
        List<String> tag = gson.fromJson(tags, new TypeToken<List<String>>() {
        }.getType());
        List<JudgeCase> jCase = gson.fromJson(judgeCase, new TypeToken<List<JudgeCase>>() {
        }.getType());
        JudgeConfig jConfig = gson.fromJson(judgeConfig, JudgeConfig.class);

        questionVo.setTags(tag);
        questionVo.setJudgeCase(jCase);
        questionVo.setJudgeConfig(jConfig);
//        System.out.println(questionVo);
        return Result.ok(questionVo);
    }

    @Override
    public Wrapper<Question> getQueryWrapper(QuestionRequest questionRequest) {
        QueryWrapper<Question> Wrapper = new QueryWrapper<>();
        if (questionRequest == null) {
            return Wrapper;
        }
        //标题
        String title = questionRequest.getTitle();
        //内容
        String content = questionRequest.getContent();
        //标签
        List<String> tags = questionRequest.getTags();
        //userId
        Long userId = questionRequest.getUserId();
        Long id = questionRequest.getId();

        String sortField = questionRequest.getSortField();
        String sortOrder = questionRequest.getSortOrder();

        // 拼接查询条件
        if (StringUtils.isNotBlank(title)) {
            Wrapper.and(qw -> qw.like("title", title).or().like("content", title));
        }
        Wrapper.like(StringUtils.isNotBlank(title), "title", title);
        Wrapper.like(StringUtils.isNotBlank(content), "content", content);
        if (CollUtil.isNotEmpty(tags)) {
            for (String tag : tags) {
                Wrapper.like("tags", "\"" + tag + "\"");
            }
        }
        Wrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        Wrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        return Wrapper;
    }

    @Override
    public Page<QuestionVo> getPostVOPage(Page<Question> questionPage) {
        List<Question> questionList = questionPage.getRecords();
        Page<QuestionVo> page = new Page<>(questionPage.getCurrent(), questionPage.getSize(), questionPage.getTotal());
        if(CollUtil.isEmpty(questionList)){
            return page;
        }
        List<QuestionVo> questionVos = questionList.stream().map(question -> {
            QuestionVo questionVo = new QuestionVo();
            BeanUtils.copyProperties(question,questionVo);

            Gson gson = new Gson();
            List<String> tag = gson.fromJson(question.getTags(), new TypeToken<List<String>>() {
            }.getType());
            List<JudgeCase> jCase = gson.fromJson(question.getJudgeCase(), new TypeToken<List<JudgeCase>>() {
            }.getType());
            JudgeConfig jConfig = gson.fromJson(question.getJudgeConfig(), JudgeConfig.class);

            questionVo.setJudgeConfig(jConfig);
            questionVo.setJudgeCase(jCase);
            questionVo.setTags(tag);
            return questionVo;
        }).collect(Collectors.toList());

//        System.out.println("QuestionVo:"+questionVos);
        page.setRecords(questionVos);
        return page;
    }

    @Override
    public Result<List<QuestionVo>> selectByPage(long current, long size) {
        Page<User> page=new Page<>();
        page.setCurrent(current);
        page.setSize(size);

        List<Question> questionList = questionMapper.selectPageVo(page);
        if(questionList==null){
            return Result.fail();
        }
//        System.out.println("questionList:="+questionList);

        List<QuestionVo> questionVos =new ArrayList<>();
        for (int i = 0; i < questionList.size(); i++) {
            QuestionVo questionVo = new QuestionVo();
            BeanUtils.copyProperties(questionList.get(i),questionVo);

            Gson gson = new Gson();

            List<String> tag = gson.fromJson(questionList.get(i).getTags(),
                    new TypeToken<List<String>>() {}.getType());
            List<JudgeCase> jCase = gson.fromJson(questionList.get(i).getJudgeCase(),
                    new TypeToken<List<JudgeCase>>() {}.getType());

            JudgeConfig jConfig = gson.fromJson(questionList.get(i).getJudgeConfig(), JudgeConfig.class);

            questionVo.setTags(tag);
            questionVo.setJudgeCase(jCase);
            questionVo.setJudgeConfig(jConfig);

            questionVos.add(questionVo);
        }
        return Result.ok(questionVos);
    }

    @Override
    public List<Question> selectByPage2(long current, long size) {
        Page<User> page=new Page<>();
        page.setCurrent(current);
        page.setSize(size);

        List<Question> questionList = questionMapper.selectPageVo(page);
        return questionList;
    }

    @Override
    public Result<String> deleteById(Long id) {
        int i = questionMapper.deleteById(id);
        if(i!=1){
            return Result.fail();
        }
        return Result.ok();
    }

    @Override
    public Result<String> MyUpdate(QuestionRequest questionRequest) {
        Question question = toQuestion(questionRequest);
        question.setUpdateTime(LocalDateTime.now());
        int i = questionMapper.updateById(question);
        if(i==0){
            return Result.fail(302,"修改失败");
        }
        return Result.ok();
    }

    @Override
    public Page<Question> listByPageTest(QuestionRequest questionRequest) {
        Page<Question> page = new Page<>(questionRequest.getCurrent(),questionRequest.getPageSize());
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        List<String> tags = questionRequest.getTags();


        if (CollUtil.isNotEmpty(tags)) {
            for (String tag : tags) {
                wrapper.like("tags", "\"" + tag + "\"");
            }
        }
        wrapper.orderByAsc("id");
        wrapper.like(StringUtils.isNotBlank(questionRequest.getTitle()),"title",questionRequest.getTitle());

        Page<Question> result = questionMapper.selectPage(page, wrapper);
        return result;
    }
}


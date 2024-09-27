package com.ojBacked.judge.codesandbox.strategy;

import cn.hutool.json.JSONUtil;
import com.ojBacked.comment.dto.JudgeConfig;
import com.ojBacked.judge.codesandbox.model.JudgeInfo;
import com.ojBacked.entity.Question;


import java.util.ArrayList;
import java.util.List;

public class DefaultJudgeStrategy implements JudgeStrategy {
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        //拿到执行结果，进行判断
        //判断运行结果输出数量是否和预期输出数量是否一致
        //依次判断每一项出和预期输出是否相等
        //判断题目限制是否和输出的限制是否一致
        //整体输出数量是否一致
        List<String> details =new ArrayList<>();
        List<String> outputList = judgeContext.getOutputList();
        List<String> inputList = judgeContext.getInputList();
        JudgeInfo outputJudgeInfo = judgeContext.getJudgeInfo();
        outputJudgeInfo.setMessage("success");
        if(outputList.size()<inputList.size()){
//            throw new BusinessException(ErrorCode.OPERATION_ERROR,"某个用例运行失败");
            details.add("输出结果数量与预计输出结果数量不等，某个用例运行失败");
            outputJudgeInfo.setMessage("error");
        }
        //输出结果是否一致
        for (int i = 0; i < outputList.size(); i++) {
            if (!inputList.get(i).equals(outputList.get(i))) {
                outputJudgeInfo.setMessage("error");
                details.add("用例"+i+"异常"+",输出为"+outputList.get(i)+",预计输出为"+inputList.get(i));
            }else{
                details.add("用例"+i+",输出为"+outputList.get(i)+",预计输出为"+inputList.get(i));
            }
        }

//        判断题目限制

        Question question = judgeContext.getQuestion();
        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        if(judgeConfig.getTimeLimit()<outputJudgeInfo.getTime()
                || judgeConfig.getMemoryLimit()< outputJudgeInfo.getTime()){
            details.add("内存或者空间暴了");
            outputJudgeInfo.setMessage("error");
        }

        outputJudgeInfo.setDetails(details);
        return outputJudgeInfo;
    }
}

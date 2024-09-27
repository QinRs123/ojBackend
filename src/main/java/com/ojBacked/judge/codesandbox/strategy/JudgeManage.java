package com.ojBacked.judge.codesandbox.strategy;

import com.ojBacked.judge.codesandbox.model.JudgeInfo;

public class JudgeManage {

    JudgeInfo judgeStrategy( JudgeContext judgeContext){
        JudgeStrategy judgeStrategy =new DefaultJudgeStrategy();
        String Language =judgeContext.getQuestionSubmit().getLanguage();
        if(Language.equals("java")){
            // todo: 别的策略逻辑....
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}

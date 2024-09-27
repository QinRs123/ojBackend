package com.ojBacked.judge.codesandbox.strategy;

import com.ojBacked.judge.codesandbox.model.JudgeInfo;

public interface JudgeStrategy {

    JudgeInfo doJudge(JudgeContext judgeContext);
}

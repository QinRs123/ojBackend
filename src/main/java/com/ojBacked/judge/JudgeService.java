package com.ojBacked.judge;

import com.ojBacked.entity.QuestionSubmit;

/**
 * 判题服务。。
 */
public interface JudgeService {

    /**
     * 判题
     * @param questionSubmitId
     * @return
     */
    QuestionSubmit doJudge(Long questionSubmitId);
}


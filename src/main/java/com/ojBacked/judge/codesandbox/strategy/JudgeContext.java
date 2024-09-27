package com.ojBacked.judge.codesandbox.strategy;

import com.ojBacked.judge.codesandbox.model.JudgeInfo;
import com.ojBacked.entity.Question;
import com.ojBacked.entity.QuestionSubmit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JudgeContext {

    /**
     * 执行输入的结果
     */
    List<String> inputList;

    /**
     * 执行输出的结果
     */
    List<String> outputList;

    /**
     * 提交记录的信息
     */
    QuestionSubmit questionSubmit;

    /**
     * 相关问题的信息
     */
    Question question;

    /**
     * 输出执行信息
     */
    JudgeInfo judgeInfo;

}

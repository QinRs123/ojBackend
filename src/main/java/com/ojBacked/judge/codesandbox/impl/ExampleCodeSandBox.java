package com.ojBacked.judge.codesandbox.impl;

import com.ojBacked.judge.codesandbox.model.JudgeInfo;
import com.ojBacked.judge.codesandbox.CodeSandBox;
import com.ojBacked.judge.codesandbox.model.ExecuteCodeRequest;
import com.ojBacked.judge.codesandbox.model.ExecuteCodeResponse;

import java.util.List;

/**
 * 示例代码，用于跑通 业务
 */
public class ExampleCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("示例代码沙箱测试");
        String code = executeCodeRequest.getCode();
        List<String> inputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("success");
        executeCodeResponse.setMessage(code);
        executeCodeResponse.setJudgeInfo(new JudgeInfo("success",2000L,2000L,null));
        return executeCodeResponse;
    }
}

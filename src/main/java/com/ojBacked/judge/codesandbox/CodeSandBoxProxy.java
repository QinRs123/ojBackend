package com.ojBacked.judge.codesandbox;

import com.ojBacked.judge.codesandbox.model.ExecuteCodeRequest;
import com.ojBacked.judge.codesandbox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CodeSandBoxProxy implements CodeSandBox{

    public CodeSandBoxProxy(CodeSandBox codeSendBox){
        this.codeSandBox=codeSendBox;
    }

    private final CodeSandBox codeSandBox;
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱请求信息"+executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        log.info("代码沙箱响应信息"+executeCodeResponse.toString());
        return executeCodeResponse;
    }
}

package com.ojBacked.judge.codesandbox.impl;

import com.ojBacked.judge.codesandbox.CodeSandBox;
import com.ojBacked.judge.codesandbox.model.ExecuteCodeRequest;
import com.ojBacked.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 调用第三方代码沙箱。
 */
public class ThirdPartyCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱测试");
        return null;
    }
}

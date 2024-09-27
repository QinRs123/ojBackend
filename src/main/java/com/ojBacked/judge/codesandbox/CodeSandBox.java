package com.ojBacked.judge.codesandbox;

import com.ojBacked.judge.codesandbox.model.ExecuteCodeRequest;
import com.ojBacked.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 定义代码沙箱接口，提高通用性
 */
public interface CodeSandBox {
    /**
     * 执行代码
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}

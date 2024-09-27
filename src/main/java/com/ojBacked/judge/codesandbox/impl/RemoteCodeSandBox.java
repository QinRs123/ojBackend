package com.ojBacked.judge.codesandbox.impl;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.ojBacked.exception.BusinessException;
import com.ojBacked.judge.codesandbox.CodeSandBox;
import com.ojBacked.judge.codesandbox.model.ExecuteCodeRequest;
import com.ojBacked.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 远程调用代码沙箱，实际调用接口的
 */
public class RemoteCodeSandBox implements CodeSandBox {


    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱测试");
        String url = "http://localhost:8022/executeCode";
        String jsonStr = JSONUtil.toJsonStr(executeCodeRequest);
        String body = HttpUtil.createPost(url)
                .body(jsonStr)
                .execute()
                .body();
        ExecuteCodeResponse response = JSONUtil.toBean(body, ExecuteCodeResponse.class);
        if(response==null){
            throw new BusinessException(510,"代码执行异常");
        }
        return response;
    }
}

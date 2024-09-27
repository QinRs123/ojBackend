package com.ojBacked.judge.codesandbox;

import com.ojBacked.judge.codesandbox.impl.ExampleCodeSandBox;
import com.ojBacked.judge.codesandbox.impl.RemoteCodeSandBox;
import com.ojBacked.judge.codesandbox.impl.ThirdPartyCodeSandBox;

/**
 * 代码沙箱工厂，（根据字符串生成不同的代码沙箱实例）
 */
public class CodeSandBoxFactory {
    public static CodeSandBox newInstance(String type){
        switch(type){
            case"example":
                return new ExampleCodeSandBox();
            case"remote":
                return new RemoteCodeSandBox();
            case"third":
                return new ThirdPartyCodeSandBox();
            default:
                return new ExampleCodeSandBox();
        }
    }
}

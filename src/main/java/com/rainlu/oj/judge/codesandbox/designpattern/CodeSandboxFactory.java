package com.rainlu.oj.judge.codesandbox.designpattern;

import com.rainlu.oj.judge.codesandbox.CodeSandBox;
import com.rainlu.oj.judge.codesandbox.impl.ExampleCodeSandbox;
import com.rainlu.oj.judge.codesandbox.impl.RemoteCodeSandbox;
import com.rainlu.oj.judge.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * 代码沙箱工厂（根据字符串参数创建指定的代码沙箱实例）
 */
public class CodeSandboxFactory {

    /**
     * 创建代码沙箱示例
     *
     * @param type 沙箱类型
     * @return
     */
    public static CodeSandBox newInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandbox();
            case "remote":
                return new RemoteCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            default:
                return new ExampleCodeSandbox();
        }
    }
}

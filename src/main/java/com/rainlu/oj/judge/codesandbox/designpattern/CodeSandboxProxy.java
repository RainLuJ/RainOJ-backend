package com.rainlu.oj.judge.codesandbox.designpattern;

import com.rainlu.oj.judge.codesandbox.CodeSandBox;
import com.rainlu.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.rainlu.oj.judge.codesandbox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @description 使用代理模式，给代码沙箱执行时添加日志
 * @author Jun Lu
 * @date 2023-09-04 16:19:29
 */
@Slf4j
public class CodeSandboxProxy implements CodeSandBox {

    // 代理的目标类
    private CodeSandBox codeSandBox;

    public CodeSandboxProxy(CodeSandBox codeSandBox) {
        this.codeSandBox = codeSandBox;
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱请求信息：" + executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        log.info("代码沙箱响应信息：" + executeCodeResponse.toString());
        return executeCodeResponse;
    }
}

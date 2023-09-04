package com.rainlu.oj.judge.codesandbox.impl;

import com.rainlu.oj.judge.codesandbox.CodeSandBox;
import com.rainlu.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.rainlu.oj.judge.codesandbox.model.ExecuteCodeResponse;
import com.rainlu.oj.judge.codesandbox.model.JudgeInfo;
import com.rainlu.oj.model.enums.JudgeInfoMessageEnum;
import com.rainlu.oj.model.enums.QuestionSubmitStatusEnum;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 示例代码沙箱（仅为了跑通业务流程）
 */
@Service
public class ExampleCodeSandbox implements CodeSandBox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}

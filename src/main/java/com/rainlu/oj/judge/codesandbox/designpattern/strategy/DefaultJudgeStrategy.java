package com.rainlu.oj.judge.codesandbox.designpattern.strategy;

import cn.hutool.json.JSONUtil;
import com.rainlu.oj.judge.codesandbox.model.JudgeInfo;
import com.rainlu.oj.model.dto.question.QuestionJudgeCase;
import com.rainlu.oj.model.dto.question.QuestionJudgeConfig;
import com.rainlu.oj.model.entity.Question;
import com.rainlu.oj.model.enums.JudgeInfoMessageEnum;

import java.util.List;

/**
 * 默认判题策略
 */
public class DefaultJudgeStrategy implements JudgeStrategy {
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {

        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        Long memory = judgeInfo.getMemory();
        Long time = judgeInfo.getTime();
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        List<QuestionJudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        Question question = judgeContext.getQuestion();

        /* 执行判题 */
        // 1. 判题信息中的消息
        JudgeInfoMessageEnum judgeInfoStatusEnum = JudgeInfoMessageEnum.ACCEPTED;
        // 在判题策略类中就是根据程序的执行结果（在沙箱中执行）执行判题逻辑，然后返回一个判题信息
        JudgeInfo judgeInfoResp = new JudgeInfo();
        judgeInfoResp.setMemory(memory);
        judgeInfoResp.setTime(time);

        // 2. 判断 输入&输出用例(沙箱根据`输入`执行后的返回值) 是否与题目所配套的判题用例(QuestionJudgeCase)相匹配
        if (inputList.size() != outputList.size()) {
            judgeInfoStatusEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfoResp.setMessage(judgeInfoStatusEnum.getText());
            return judgeInfoResp;
        }
        // 依次判断每一项输入对应的输出和预期的输出是否相等
        for (int i = 0; i < judgeCaseList.size(); i++) {
            QuestionJudgeCase judgeCase = judgeCaseList.get(i);
            if (judgeCase.getOutput().equals(outputList.get(i))) {
                judgeInfoStatusEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfoResp.setMessage(judgeInfoStatusEnum.getText());
                return judgeInfoResp;
            }
        }

        // 3. 判断 消耗内存与响应时间
        String judgeConfigJsonStr = question.getJudgeConfig();
        QuestionJudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigJsonStr, QuestionJudgeConfig.class);
        Long timeLimit = judgeConfig.getTimeLimit();
        Long memoryLimit = judgeConfig.getMemoryLimit();
        if (time > timeLimit) {  // 程序执行时间超过限制
            judgeInfoStatusEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResp.setMessage(judgeInfoStatusEnum.getText());
            return judgeInfoResp;
        }
        if (memory > memoryLimit) {  // 程序执行所耗费的内存超过限制
            judgeInfoStatusEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResp.setMessage(judgeInfoStatusEnum.getText());
            return judgeInfoResp;
        }

        judgeInfoResp.setMessage(judgeInfoStatusEnum.getText());
        return judgeInfoResp;
    }
}

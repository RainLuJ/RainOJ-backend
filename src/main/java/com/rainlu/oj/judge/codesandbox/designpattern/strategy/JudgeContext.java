package com.rainlu.oj.judge.codesandbox.designpattern.strategy;

import com.rainlu.oj.judge.codesandbox.model.JudgeInfo;
import com.rainlu.oj.model.dto.question.QuestionJudgeCase;
import com.rainlu.oj.model.entity.Question;
import com.rainlu.oj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 上下文（用于定义判题策略在判题时所需的参数信息）
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<QuestionJudgeCase> judgeCaseList;

    private Question question;

    private QuestionSubmit questionSubmit;

}


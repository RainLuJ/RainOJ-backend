package com.rainlu.oj.model.dto.question;

import lombok.Data;

/**
 * 题目所配套的`题目用例`。包括输入、输出用例
 */
@Data
public class QuestionJudgeCase {

    /**
     * 输入用例
     */
    private String input;

    /**
     * 输出用例
     */
    private String output;
}

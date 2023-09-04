package com.rainlu.oj.model.dto.question;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description 添加题目的请求
 * @author Jun Lu
 * @date 2023-09-03 19:18:20
 */
@Data
public class QuestionAddRequest implements Serializable {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表
     */
    private List<String> tags;

    /**
     * 题目答案
     */
    private String answer;

    /**
     * 判题用例
     */
    private List<QuestionJudgeCase> questionJudgeCase;

    /**
     * 判题配置
     */
    private QuestionJudgeConfig questionJudgeConfig;

    private static final long serialVersionUID = 1L;
}


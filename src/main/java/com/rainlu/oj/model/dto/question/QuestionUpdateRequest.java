package com.rainlu.oj.model.dto.question;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description 更新题目的请求：区别于QuestionEditRequest。这是给管理员使用的，可以更新更多的业务字段
 * @author Jun Lu
 * @date 2023-09-03 19:29:11
 */
@Data
public class QuestionUpdateRequest implements Serializable {


    /**
     * id
     */
    private Long id;

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


package com.rainlu.oj.model.dto.questionsubmit;

import lombok.Data;

import java.io.Serializable;

/**
 * @description 题目提交信息-创建请求
 * @author Jun Lu
 * @date 2023-09-03 20:04:38
 */
@Data
public class QuestionSubmitAddRequest implements Serializable {

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 题目 id
     */
    private Long questionId;

    private static final long serialVersionUID = 1L;
}


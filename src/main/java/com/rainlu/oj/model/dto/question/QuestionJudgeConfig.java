package com.rainlu.oj.model.dto.question;

import lombok.Data;

/**
 * 在判题时的一些配置信息
 *  - 之所以设置为DTO对象，是因为这些对象本身就是在DTO对象中使用到的一些方便前端传值的对象
 *  - DTO中的一些属性接收完用户传参后，再将其转换为JSON格式的字符串
 */
@Data
public class QuestionJudgeConfig {

    /**
     * 时间限制（ms）
     */
    private Long timeLimit;

    /**
     * 内存限制（KB）
     */
    private Long memoryLimit;

    /**
     * 堆栈限制（KB）
     */
    private Long stackLimit;
}

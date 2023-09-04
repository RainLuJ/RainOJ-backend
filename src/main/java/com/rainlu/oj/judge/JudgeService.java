package com.rainlu.oj.judge;

import com.rainlu.oj.model.entity.QuestionSubmit;

/**
 * 判题服务
 */
public interface JudgeService {

    /**
     * 判题
     */
    QuestionSubmit doJudge(long questionSubmitId);
}
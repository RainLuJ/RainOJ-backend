package com.rainlu.oj.judge.codesandbox.designpattern.strategy;


import com.rainlu.oj.judge.codesandbox.model.JudgeInfo;

/**
 * 判题策略应当遵循的规范
 */
public interface JudgeStrategy {

    /**
     * 执行判题逻辑
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}

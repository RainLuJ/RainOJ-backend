package com.rainlu.oj.judge;

import com.rainlu.oj.judge.codesandbox.designpattern.strategy.DefaultJudgeStrategy;
import com.rainlu.oj.judge.codesandbox.designpattern.strategy.JavaLanguageJudgeStrategy;
import com.rainlu.oj.judge.codesandbox.designpattern.strategy.JudgeContext;
import com.rainlu.oj.judge.codesandbox.designpattern.strategy.JudgeStrategy;
import com.rainlu.oj.judge.codesandbox.model.JudgeInfo;
import com.rainlu.oj.model.entity.QuestionSubmit;
import com.rainlu.oj.model.enums.QuestionSubmitLanguageEnum;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化调用）
 *  - 避免在**调用方（判题服务中）**去[选择]更换`判题策略`，将更换判题策略的逻辑写在此类中，简化调用
 */
@Service
public class JudgeManager {

    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();

        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if (QuestionSubmitLanguageEnum.JAVA.getValue().equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }

        return judgeStrategy.doJudge(judgeContext);
    }

}

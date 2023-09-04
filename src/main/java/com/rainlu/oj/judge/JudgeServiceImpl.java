package com.rainlu.oj.judge;

import cn.hutool.json.JSONUtil;
import com.rainlu.oj.common.ErrorCode;
import com.rainlu.oj.exception.BusinessException;
import com.rainlu.oj.judge.codesandbox.CodeSandBox;
import com.rainlu.oj.judge.codesandbox.designpattern.CodeSandboxFactory;
import com.rainlu.oj.judge.codesandbox.designpattern.CodeSandboxProxy;
import com.rainlu.oj.judge.codesandbox.designpattern.strategy.JudgeContext;
import com.rainlu.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.rainlu.oj.judge.codesandbox.model.ExecuteCodeResponse;
import com.rainlu.oj.judge.codesandbox.model.JudgeInfo;
import com.rainlu.oj.model.dto.question.QuestionJudgeCase;
import com.rainlu.oj.model.entity.Question;
import com.rainlu.oj.model.entity.QuestionSubmit;
import com.rainlu.oj.model.enums.QuestionSubmitStatusEnum;
import com.rainlu.oj.service.QuestionService;
import com.rainlu.oj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private JudgeManager judgeManager;

    // `:example`的意思是：codesandbox.type如果没有进行配置，则使用默认值`example`
    @Value("${codesandbox.type:example}")
    private String type;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {

        // 1）传入题目的提交 id，获取到对应的题目、提交信息（包含代码、编程语言等）
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }

        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目信息不存在");
        }

        // 2）如果题目提交状态不为等待中，就不用重复执行了（只读取`等待中`的题目进行判题，节省代码沙箱的资源）
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "正在判题中");
        }

        // 3）更改判题（题目提交）的状态为 “判题中”，防止重复执行
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmit.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean isUpdate = questionSubmitService.updateById(questionSubmitUpdate);
        if (!isUpdate) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新失败");
        }

        // 4）根据配置调用指定类型的沙箱，获取到执行结果
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        // 获取输入用例
        String judgeCaseJsonStr = question.getJudgeCase();
        List<QuestionJudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseJsonStr, QuestionJudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(QuestionJudgeCase::getInput).collect(Collectors.toList());

        // 将 输入用例&用户提交 的代码给代码沙箱执行，获取执行后的结果
        CodeSandBox codeSandBox = CodeSandboxFactory.newInstance(type);
        codeSandBox = new CodeSandboxProxy(codeSandBox);
        ExecuteCodeRequest executeCodeRequest = new ExecuteCodeRequest().builder()
                .inputList(inputList)
                .code(code)
                .language(language)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        List<String> outputList = executeCodeResponse.getOutputList();


        // 5）根据沙箱的执行结果，进行后续的判题，并设置题目的判题状态和信息
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);
        // judgeManager会根据不同的语言来选择不同的判题策略
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);


        // 6）修改数据库中题目提交信息的判题结果
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setQuestionId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        isUpdate = questionSubmitService.updateById(questionSubmitUpdate);
        if (!isUpdate) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }

        QuestionSubmit questionSubmitResult = questionSubmitService.getById(questionId);
        return questionSubmitResult;
    }
}

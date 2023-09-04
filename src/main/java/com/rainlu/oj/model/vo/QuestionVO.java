package com.rainlu.oj.model.vo;

import cn.hutool.json.JSONUtil;
import com.rainlu.oj.model.dto.question.QuestionJudgeConfig;
import com.rainlu.oj.model.entity.Question;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

/**
 * @description 封装后端应该回传给前端的题目信息
 * @author Jun Lu
 * @date 2023-09-03 19:01:38
 */
@Data
public class QuestionVO {

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
     * 题目提交数
     */
    private Integer submitNum;

    /**
     * 题目通过数
     */
    private Integer acceptedNum;

    /**
     * 判题配置（json 对象）
     */
    private QuestionJudgeConfig questionJudgeConfig;

    /**
     * 点赞数
     */
    private Integer thumbNum;

    /**
     * 收藏数
     */
    private Integer favourNum;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建题目人的信息
     */
    private UserVO userVO;

    /**
     * VO类转实体类
     *
     * @param questionVO
     * @return
     */
    public static Question voToObj(QuestionVO questionVO) {
        if (questionVO == null) {
            return null;
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionVO, question);
        List<String> tagList = questionVO.getTags();
        if (tagList != null) {
            question.setTags(JSONUtil.toJsonStr(tagList));
        }
        QuestionJudgeConfig voQuestionJudgeConfig = questionVO.getQuestionJudgeConfig();
        if (voQuestionJudgeConfig != null) {
            question.setJudgeConfig(JSONUtil.toJsonStr(voQuestionJudgeConfig));
        }
        return question;
    }

    /**
     * 实体类转VO类
     *
     * @param question
     * @return
     */
    public static QuestionVO objToVo(Question question) {
        if (question == null) {
            return null;
        }
        QuestionVO questionVO = new QuestionVO();
        BeanUtils.copyProperties(question, questionVO);
        List<String> tagList = JSONUtil.toList(question.getTags(), String.class);
        questionVO.setTags(tagList);
        String judgeConfigStr = question.getJudgeConfig();
        questionVO.setQuestionJudgeConfig(JSONUtil.toBean(judgeConfigStr, QuestionJudgeConfig.class));
        return questionVO;
    }

    private static final long serialVersionUID = 1L;

}

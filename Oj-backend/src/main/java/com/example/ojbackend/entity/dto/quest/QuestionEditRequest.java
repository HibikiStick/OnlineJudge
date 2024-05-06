package com.example.ojbackend.entity.dto.quest;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

@Data
public class QuestionEditRequest {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
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
     * 答案
     */
    private String answer;

    /**
     * 判题Json数组
     */
    private List<JudgeConfig> judgeConfig;

    /**
     * 题目用例配置Json
     */
    private List<JudgeCase> judegCase;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

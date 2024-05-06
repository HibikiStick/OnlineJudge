package com.example.ojbackend.entity.dto.quest;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class QuestionAddRequest {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表（json 数组）
     */
    private List<String> tags;


    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 答案
     */
    private String answer;

    /**
     * 判题Json数组
     */
    private JudgeConfig judgeConfig;

    /**
     * 题目用例配置Json
     */
    private List<JudgeCase> judgeCase;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

package com.example.ojbackend.entity.dto.quest;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.ojbackend.constant.CommonConstant;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class QuestionQueryRequest {
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
     * 提交数
     */
    private Integer submitNum;

    /**
     * 题目通过数
     */
    private Integer acceptedNum;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    private String sortField;

    private String sortOrder = CommonConstant.SORT_ORDER_ASC;;


    private long current;

    private long pageSize;
}

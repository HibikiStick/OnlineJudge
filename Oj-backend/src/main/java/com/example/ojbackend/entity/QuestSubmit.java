package com.example.ojbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 帖子点赞
 * @TableName quest_submit
 */
@TableName(value ="quest_submit")
@Data
public class QuestSubmit implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 题目 id
     */
    private Long questionId;

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
     * 用户代码
     */
    private String code;

    /**
     * 判题配置Json
     */
    private String judgeInfo;

    /**
     * 判题状态（0 待判断，1 判题中，2 成功，3失败）
     */
    private Integer status;

    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 选择的语言
     */
    private String language;
}
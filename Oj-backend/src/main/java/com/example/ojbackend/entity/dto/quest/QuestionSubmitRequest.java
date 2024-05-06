package com.example.ojbackend.entity.dto.quest;

import lombok.Data;

import java.io.Serializable;
@Data
public class QuestionSubmitRequest implements Serializable {
    //选择的语言
    private String language;

    //用户的代码
    private String code;

    //题目Id
    private Long questId;
}

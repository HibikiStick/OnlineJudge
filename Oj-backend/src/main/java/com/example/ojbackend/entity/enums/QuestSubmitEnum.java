package com.example.ojbackend.entity.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum QuestSubmitEnum {

    //待判题 0 判题中 1 成功 2 失败 3
    WAITING("等待中",0),
    RUNNING("判题中",1),

    SUCCEED("成功",2),

    FAILED("失败",3);


    private final String text;
    private final Integer value;

    QuestSubmitEnum(String text, Integer value) {
        this.text = text;
        this.value = value;
    }

    public static List<Integer> getEnumByValue(){
        return Arrays.stream(values()).map(item ->
            item.value
        ).collect(Collectors.toList());
    }
    public static QuestSubmitEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (QuestSubmitEnum anEnum : QuestSubmitEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public Integer getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}

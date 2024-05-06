package com.example.ojbackend.entity.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 判题配置输出
 */
public enum JudgeInfoMessageEnum {
    /**
     * 成功
     */
    ACCEPTED("成功","Accepted"),
    SYSTEM_ERROR("SystemError","系统错误"),
    RUNTIME_ERROR("RuntimeError","运行时异常"),
    DANGEROUS_ERROR("DangerousError","危险操作"),

    OUTPUT_LIMIT("OutputLimit","输出错误"),

    WAITING("Waiting","等待中"),

    PRESENTATION_ERROR("PresentationError","展示错误"),

    TIME_LIMIT("TimeLimit","超时"),

    MEMORY_LIMIT("MemoryLimit","内存溢出"),

    COMPILE_ERROR("CompileError","编译错误"),

    WRONG_ANSWER("WrongAnwer","答案错误");





    private final String text;
    private final String value;

    private JudgeInfoMessageEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }


    public static List<String> getEnumByValue(){
        return Arrays.stream(values()).map(item ->
                item.value
        ).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static JudgeInfoMessageEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (JudgeInfoMessageEnum anEnum : JudgeInfoMessageEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public String getText(){
        return text;
    }
    public String getValue() {
        return value;
    }
}

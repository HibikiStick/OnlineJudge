package com.example.ojbackend.entity.dto.quest;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class JudgeConfig {
    /**
     * 时间限制（ms）
     */
    private int timeLimit;

    /**
     * 内存限制(kb)
     */
    private int memoryLimit;
    /**
     * 堆栈限制(kb)
     */
    private int stackLimit;
}

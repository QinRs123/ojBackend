package com.ojBacked.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 题目限制
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JudgeConfig {

    /**
     * 时间限制ms
     */
    private Long timeLimit;

    /**
     * 空间限制kb
     */
    private Long memoryLimit;


    /**
     * 堆栈限制kb
     */
    private Long stackLimit;
}

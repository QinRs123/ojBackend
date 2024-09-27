package com.ojBacked.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 题目用例
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JudgeCase {

    private String input;
    private String output;
}

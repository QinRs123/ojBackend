package com.ojBacked.comment.vo;

import com.ojBacked.judge.codesandbox.model.JudgeInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionSubmitVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编程语言")
    private String language;

    @ApiModelProperty(value = "用户代码")
    private String code;

    @ApiModelProperty(value = "判题信息（json 对象）")
    private JudgeInfo judgeInfo;

    @ApiModelProperty(value = "判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）")
    private Integer status;

    @ApiModelProperty(value = "题目 id")
    private Long questionId;

    @ApiModelProperty(value = "创建用户 id")
    private Long userId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}

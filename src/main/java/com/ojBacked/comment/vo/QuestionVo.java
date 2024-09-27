package com.ojBacked.comment.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ojBacked.comment.com.PageRequest;
import com.ojBacked.comment.dto.JudgeCase;
import com.ojBacked.comment.dto.JudgeConfig;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionVo  implements  Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "标签列表（json 数组）")
    private List<String> tags;

    @ApiModelProperty(value = "题目答案")
    private String answer;

    @ApiModelProperty(value = "题目提交数")
    private Integer submitNum;

    @ApiModelProperty(value = "题目通过数")
    private Integer acceptedNum;

    /**
     * 这里换成自定义对象
     */
    private List<JudgeCase> judgeCase;

    /**
     * 这里换成自定义对象
     */
    @ApiModelProperty(value = "判题配置（json 对象）")
    private JudgeConfig judgeConfig;

    @ApiModelProperty(value = "点赞数")
    private Integer thumbNum;

    @ApiModelProperty(value = "收藏数")
    private Integer favourNum;

    @ApiModelProperty(value = "创建用户 id")
    private Long userId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}

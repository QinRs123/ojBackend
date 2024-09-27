package com.ojBacked.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 题目提交
 * </p>
 *
 * @author author
 * @since 2024-09-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("question_submit")
@ApiModel(value="QuestionSubmit对象", description="题目提交")
public class QuestionSubmit implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "编程语言")
    @TableField("language")
    private String language;

    @ApiModelProperty(value = "用户代码")
    @TableField("code")
    private String code;

    @ApiModelProperty(value = "判题信息（json 对象）")
    @TableField("judgeInfo")
    private String judgeInfo;

    @ApiModelProperty(value = "判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "题目 id")
    @TableField("questionId")
    private Long questionId;

    @ApiModelProperty(value = "创建用户 id")
    @TableField("userId")
    private Long userId;

    @ApiModelProperty(value = "创建时间")
    @TableField("createTime")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("updateTime")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除")
    @TableField("isDelete")
    private Integer isDelete;


}

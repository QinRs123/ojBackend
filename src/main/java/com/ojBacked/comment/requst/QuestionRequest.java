package com.ojBacked.comment.requst;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ojBacked.comment.com.PageRequest;
import com.ojBacked.comment.dto.JudgeCase;
import com.ojBacked.comment.dto.JudgeConfig;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequest  implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 当前页号
     */
    private int current = 1;

    /**
     * 页面大小
     */
    private int pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序（默认升序）
     */
    private String sortOrder = "ascend";

    private Long id;

    private String title;

    private String content;

    private List<String> tags;

    private String answer;

    /**
     * 这里换成自定义对象
     */
    private List<JudgeCase> judgeCase;

    /**
     * 这里换成自定义对象
     */
    private JudgeConfig judgeConfig;

    private Long userId;

}

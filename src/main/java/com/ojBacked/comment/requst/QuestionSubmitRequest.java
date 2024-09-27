package com.ojBacked.comment.requst;

import com.ojBacked.comment.com.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionSubmitRequest extends PageRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private String language;

    private String code;


    private Long questionId;

    private Long userId;

}

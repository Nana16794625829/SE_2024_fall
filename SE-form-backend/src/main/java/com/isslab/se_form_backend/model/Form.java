package com.isslab.se_form_backend.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class Form {
    private String studentId;  // 評分者學
    private List<Review> reviews;  // 對各個報告者的評分(A,B,C)，為一陣列
    private String comment;  // 其他回饋
}

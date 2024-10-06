package com.isslab.se_form_backend.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Review {
    private String presenterId;  // 報告者學號
    private String score;  // 報告者從某評分者得到的評分(A,B,C)
}

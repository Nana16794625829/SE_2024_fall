package com.isslab.se_form_backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReviewEntity {

    private Long id;

    private Long formId;
    private String reviewerId;
    private String score;
    private String presenterId;
}

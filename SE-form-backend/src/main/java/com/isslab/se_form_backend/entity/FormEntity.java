package com.isslab.se_form_backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class FormEntity {

    private Long id;

    private String reviewerId;
    private Date reviewDate;
    private String comment;
}

package com.isslab.se_form_backend.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class Form {
    private String reviewerId;
    private Date reviewDate;
    private String comment;
}

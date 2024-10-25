package com.isslab.se_form_backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/*
 * 這個 model 是用來承載前端回傳的表單回應用的
 * 內容會被 FormEntity 和 ReviewEntity 調用
 */

@Getter
@Setter
@Builder
@ToString
public class FormResponse {
    private String reviewerId;
    private String reviewerName;
    private String reviewerEmail;
    private List<String> scoreList;
    private String comment;
    private Date reviewDate;
    private String week;
}

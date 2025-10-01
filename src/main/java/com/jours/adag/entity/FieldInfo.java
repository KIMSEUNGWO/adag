package com.jours.adag.entity;

import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Setter
public class FieldInfo {
    private String name;
    private String type;
    private boolean required;
    private List<FieldInfo> nestedFields;  // 중첩 객체용

    private String summary;        // JavaDoc 첫 줄 (간단 설명)
    private String description;    // JavaDoc 전체 내용


}

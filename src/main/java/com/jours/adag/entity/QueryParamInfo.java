package com.jours.adag.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class QueryParamInfo {
    private String name;
    private Class<?> type;
    private boolean required;
    private String defaultValue;
    private String description;
}

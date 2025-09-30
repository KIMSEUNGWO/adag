package com.jours.adag.entity;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class QueryParamInfo {
    private String name;
    private String type;
    private boolean required;
    private String defaultValue;
}

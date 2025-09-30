package com.jours.adag.entity;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class PathVariableInfo {
    private String name;
    private String type;
    private boolean required;
}

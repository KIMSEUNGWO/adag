package com.jours.adag.config.configEntity;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class TagInfo {

    private Class<?> controller;
    private String name;
    private String description;
}

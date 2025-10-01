package com.jours.adag.config.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class TagInfo {

    private Class<?> controller;
    private String tagName;
    private String summary;
    private String description;
}

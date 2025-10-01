package com.jours.adag.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class PathVariableInfo {
    private String name;

    @JsonIgnore
    private Class<?> type;

    private boolean required;
    private String description;

    @JsonProperty("type")
    public String getTypeAsString() {
        return type != null ? type.getSimpleName() : null;
    }
}

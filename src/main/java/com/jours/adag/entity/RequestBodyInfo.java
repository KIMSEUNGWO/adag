package com.jours.adag.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RequestBodyInfo {
    private String typeName;
    private Class<?> type;
    private boolean required;
    private List<FieldInfo> fields;

    public RequestBodyInfo(String typeName, Class<?> type, boolean required) {
        this.typeName = typeName;
        this.type = type;
        this.required = required;
    }
}

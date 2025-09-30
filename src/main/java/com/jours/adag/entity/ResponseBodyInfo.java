package com.jours.adag.entity;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@ToString
@NoArgsConstructor
public class ResponseBodyInfo {
    private String typeName;
    private Class<?> type;
    private List<FieldInfo> fields;

    public ResponseBodyInfo(String typeName, Class<?> type) {
        this.typeName = typeName;
        this.type = type;
    }
}

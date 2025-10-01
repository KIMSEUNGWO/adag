package com.jours.adag.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ResponseBodyInfo {
    private String typeName;

    @JsonIgnore
    private Class<?> type;

    private List<FieldInfo> fields;

    public ResponseBodyInfo(String typeName, Class<?> type) {
        this.typeName = typeName;
        this.type = type;
    }

    @JsonProperty("type")
    public String getTypeAsString() {
        return type != null ? type.getSimpleName() : null;
    }
}

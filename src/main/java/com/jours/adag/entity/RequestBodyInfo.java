package com.jours.adag.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public abstract class RequestBodyInfo {

    @JsonIgnore
    private final Class<?> type;
    private final boolean required;

    @JsonProperty("type")
    public String getTypeAsString() {
        return type != null ? type.getSimpleName() : null;
    }
}

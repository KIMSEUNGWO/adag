package com.jours.adag.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public abstract class RequestBodyInfo {

    private final Class<?> type;
    private final boolean required;

}

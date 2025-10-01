package com.jours.adag.javadoc.parser;

import lombok.*;

@AllArgsConstructor
@ToString
@Getter
public class FieldDoc {

    private final String name;
    private final String summary;
    private final String description;

}

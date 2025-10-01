package com.jours.adag.javadoc;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class BlockTag {

    private final JavaDocTag tag;
    private final String name;
    private final String description;

}
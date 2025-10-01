package com.jours.adag.javadoc.parser;

import com.jours.adag.javadoc.BlockTag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@AllArgsConstructor
public class MethodDoc {

    private final String name;
    private final String summary;
    private final String description;
    private final List<BlockTag> blockTags;
}

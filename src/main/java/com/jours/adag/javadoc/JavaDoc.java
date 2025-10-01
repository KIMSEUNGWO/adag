package com.jours.adag.javadoc;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class JavaDoc {

    private String name;
    private String summary;
    private String description;
    private List<MetaData> metaDatas;

}

package com.jours.adag.javadoc;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@ToString
public class ClassDoc {
    private String className;
    private String description;
    private Map<String, FieldDoc> fieldDocs = new HashMap<>();

    public void addFieldDoc(String fieldName, String summary, String description) {
        fieldDocs.put(fieldName, new FieldDoc(summary, description));
    }
}

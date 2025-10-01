package com.jours.adag.javadoc.parser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ToString
@AllArgsConstructor
public class ClassDoc {

    @Getter
    private final Class<?> aClass;
    private final String description;
    private final Map<String, FieldDoc> fieldDocs = new HashMap<>();
    private final Map<String, MethodDoc> methodDocs = new HashMap<>();


    void addFieldDoc(String fieldName, FieldDoc javaDoc) {
        fieldDocs.put(fieldName, javaDoc);
    }

    void addMethodDoc(String methodName, MethodDoc javaDoc) {
        methodDocs.put(methodName, javaDoc);
    }

    public Optional<FieldDoc> getFieldDoc(String fieldName) {
        return Optional.ofNullable(fieldDocs.get(fieldName));
    }
    public Optional<MethodDoc> getMethodDoc(String methodName) {
        return Optional.ofNullable(methodDocs.get(methodName));
    }

    public boolean isNotEmpty() {
        return description != null || !fieldDocs.isEmpty() || !methodDocs.isEmpty();
    }
}

package com.jours.adag.javadoc;

import com.jours.adag.javadoc.parser.ClassDoc;
import com.jours.adag.javadoc.parser.FieldDoc;
import com.jours.adag.javadoc.parser.JavaDocParser;
import com.jours.adag.javadoc.parser.MethodDoc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JavadocRepository {

    private final JavaDocParser parser;
    private Map<Class<?>, ClassDoc> javadocs;

    public void init() {
        javadocs = parser.load();
    }

    public Optional<ClassDoc> getClassDoc(Class<?> clazz) {
        return Optional.ofNullable(javadocs.get(clazz));
    }

    public Optional<FieldDoc> getFieldDoc(Class<?> clazz, String fieldName) {
        return getClassDoc(clazz).flatMap(classDoc -> classDoc.getFieldDoc(fieldName));
    }
    public Optional<MethodDoc> getMethodDoc(Class<?> clazz, String methodName) {
        return getClassDoc(clazz).flatMap(classDoc -> classDoc.getMethodDoc(methodName));
    }

}

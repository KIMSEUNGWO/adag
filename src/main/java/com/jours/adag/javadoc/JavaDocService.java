package com.jours.adag.javadoc;

import com.jours.adag.javadoc.parser.FieldDoc;
import com.jours.adag.javadoc.parser.MethodDoc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JavaDocService {

    private final JavadocRepository repository;

    public String getFieldSummary(Class<?> clazz, String fieldName) {
        return repository.getFieldDoc(clazz, fieldName).map(FieldDoc::getSummary).orElse(null);
    }
    public String getFieldDescription(Class<?> clazz, String fieldName) {
        return repository.getFieldDoc(clazz, fieldName).map(FieldDoc::getDescription).orElse(null);
    }

    public String getMethodSummary(Class<?> clazz, String methodName) {
        Optional<MethodDoc> methodDoc = repository.getMethodDoc(clazz, methodName);
        return repository.getMethodDoc(clazz, methodName).map(MethodDoc::getSummary).orElse(null);
    }
    public String getMethodDescription(Class<?> clazz, String methodName) {
        return repository.getMethodDoc(clazz, methodName).map(MethodDoc::getDescription).orElse(null);
    }
}

package com.jours.adag.javadoc.parser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;
import com.jours.adag.config.ApiDocsConfigurer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class JavaDocParserImpl implements JavaDocParser {

    @Value("${adag.source-path:src/main/java}")
    private String sourcePath;

    @Override
    public Map<Class<?>, ClassDoc> load() {
        File sourceDir = new File(sourcePath);
        Map<Class<?>, ClassDoc> map = new HashMap<>();
        if (!sourceDir.exists()) {
            System.out.println("Warning: Source directory not found: " + sourcePath);
            return null;
        }
        try(Stream<Path> paths = Files.walk(sourceDir.toPath())) {
            List<ClassDoc> list = paths.filter(path -> path.toString().endsWith(".java"))
                .map(this::parseJavaFile)
                .filter(classDoc -> classDoc != null && classDoc.isNotEmpty()).toList();
            for (ClassDoc classDoc : list) {
                map.put(classDoc.getAClass(), classDoc);
            }
            System.out.println("Loaded JavaDocs for " + list.size() + " classes");
            return map;
        } catch (IOException e) {
            System.err.println("Failed to parse source files: " + e.getMessage());
            return null;
        }
    }

    private ClassDoc parseJavaFile(Path javaFile)  {
        JavaParser javaParser = new JavaParser();
        JavaDocParserHelper helper = new JavaDocParserHelper();
        AtomicReference<ClassDoc> classDoc = new AtomicReference<>();
        try {
            CompilationUnit cu = javaParser.parse(javaFile).getResult().orElseThrow();
            cu.findAll(ClassOrInterfaceDeclaration.class).forEach(classDecl -> {
                String className = cu.getPackageDeclaration()
                    .map(pd -> pd.getNameAsString() + ".")
                    .orElse("") + classDecl.getNameAsString();

                Class<?> aClass = toClass(className);
                String description = classDecl.getJavadocComment().map(this::extractDescription).orElse(null);

                classDoc.set(new ClassDoc(aClass, description));

                // Field JavaDoc
                classDecl.getFields().forEach(field -> {
                    field.getJavadocComment().ifPresent(javadoc -> {
                        field.getVariables().forEach(variable -> {
                            String fieldName = variable.getNameAsString();
                            FieldDoc fieldDoc = helper.parseToFieldDoc(fieldName, javadoc);
                            classDoc.get().addFieldDoc(fieldName, fieldDoc);
                        });
                    });
                });

                // Method JavaDoc
                classDecl.getMethods().forEach(method -> {
                    method.getJavadocComment().ifPresent(javadoc -> {
                        String methodName = method.getNameAsString();
                        MethodDoc methodDoc = helper.parseToMethodDoc(methodName, javadoc);
                        classDoc.get().addMethodDoc(methodName, methodDoc);
                    });
                });
            });

        } catch (Exception e) {
            System.err.println("Failed to parse file " + javaFile + ": " + e.getMessage());
        }
        return classDoc.get();
    }

    public Class<?> toClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private String extractDescription(JavadocComment javaDoc) {
        return javaDoc.parse().getDescription().toText().trim();
    }
}

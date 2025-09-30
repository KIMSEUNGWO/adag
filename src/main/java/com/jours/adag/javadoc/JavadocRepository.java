package com.jours.adag.javadoc;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Component
public class JavadocRepository {

    private final Map<String, ClassDoc> javadocs = new HashMap<>();
    private final Map<String, Map<String, FieldDoc>> methodDocs = new HashMap<>();
    private final JavaParser javaParser = new JavaParser();

    @Value("${adag.source-path:src/main/java}")
    private String sourcePath;

    @PostConstruct
    public void init() {
        File sourceDir = new File(sourcePath);
        if (!sourceDir.exists()) {
            System.out.println("Warning: Source directory not found: " + sourcePath);
            return;
        }

        try {
            parseSourceFiles(sourceDir.toPath());
            System.out.println("Loaded JavaDocs for " + javadocs.size() + " classes");
        } catch (Exception e) {
            System.err.println("Failed to parse source files: " + e.getMessage());
        }
    }

    private void parseSourceFiles(Path sourceDir) throws IOException {
        try (Stream<Path> paths = Files.walk(sourceDir)) {
            paths.filter(path -> path.toString().endsWith(".java"))
                .forEach(this::parseJavaFile);
        }
    }

    private void parseJavaFile(Path javaFile) {
        try {
            CompilationUnit cu = javaParser.parse(javaFile).getResult().orElse(null);
            if (cu == null) return;

            cu.findAll(ClassOrInterfaceDeclaration.class).forEach(classDecl -> {
                String className = cu.getPackageDeclaration()
                    .map(pd -> pd.getNameAsString() + ".")
                    .orElse("") + classDecl.getNameAsString();

                ClassDoc classDoc = new ClassDoc();
                classDoc.setClassName(className);

                // 클래스 JavaDoc 추출
                classDecl.getJavadocComment().ifPresent(javadoc -> {
                    classDoc.setDescription(extractDescription(javadoc));
                });

                // 필드 JavaDoc 추출
                classDecl.getFields().forEach(field -> {
                    field.getJavadocComment().ifPresent(javadoc -> {
                        field.getVariables().forEach(variable -> {
                            String fieldName = variable.getNameAsString();
                            String summary = extractSummary(javadoc);
                            String description = extractDescription(javadoc);
                            System.out.println("summary = " + summary + " description = " + description);
                            classDoc.addFieldDoc(fieldName, summary, description);
                        });
                    });
                });

                // 메소드 JavaDoc 추출
                Map<String, FieldDoc> methodDocMap = new HashMap<>();
                classDecl.getMethods().forEach(method -> {
                    method.getJavadocComment().ifPresent(javadoc -> {
                        String methodName = method.getNameAsString();
                        String summary = extractSummary(javadoc);
                        String description = extractDescription(javadoc);

                        FieldDoc methodDoc = new FieldDoc();
                        methodDoc.setSummary(summary);
                        methodDoc.setDescription(description);
                        methodDocMap.put(methodName, methodDoc);
                    });
                });

                if (!methodDocMap.isEmpty()) {
                    methodDocs.put(className, methodDocMap);
                }

                if (!classDoc.getFieldDocs().isEmpty() || classDoc.getDescription() != null) {
                    javadocs.put(className, classDoc);
                }
            });
        } catch (Exception e) {
            System.err.println("Failed to parse file " + javaFile + ": " + e.getMessage());
        }
    }

    private String extractSummary(JavadocComment javadoc) {
        String content = javadoc.parse().getDescription().toText().trim();

        // 첫 번째 비어있지 않은 줄을 summary로 사용
        return content.lines()
            .map(String::trim)
            .filter(line -> !line.isEmpty())
            .findFirst()
            .orElse(null);
    }

    private String extractDescription(JavadocComment javadoc) {
        String content = javadoc.parse().getDescription().toText().trim();

        // 첫 줄(summary) 이후의 내용을 description으로 사용
        String[] lines = content.split("\n", 2);

        if (lines.length < 2) {
            // 한 줄만 있으면 description은 null
            return null;
        }

        // 두 번째 부분(빈 줄 이후)을 trim해서 반환
        String description = lines[1].trim();
        return description.isEmpty() ? null : description;
    }

    /**
     * 필드의 요약 (첫 줄)
     */
    public String getFieldSummary(Class<?> clazz, String fieldName) {
        ClassDoc classDoc = javadocs.get(clazz.getName());
        if (classDoc == null) return null;

        FieldDoc fieldDoc = classDoc.getFieldDocs().get(fieldName);
        return fieldDoc != null ? fieldDoc.getSummary() : null;
    }

    /**
     * 필드의 전체 설명
     */
    public String getFieldDescription(Class<?> clazz, String fieldName) {
        ClassDoc classDoc = javadocs.get(clazz.getName());
        if (classDoc == null) return null;

        FieldDoc fieldDoc = classDoc.getFieldDocs().get(fieldName);
        return fieldDoc != null ? fieldDoc.getDescription() : null;
    }

    /**
     * 클래스 설명
     */
    public String getClassDoc(Class<?> clazz) {
        ClassDoc classDoc = javadocs.get(clazz.getName());
        return classDoc != null ? classDoc.getDescription() : null;
    }

    /**
     * 메소드의 요약 (첫 줄)
     */
    public String getMethodSummary(Class<?> clazz, String methodName) {
        Map<String, FieldDoc> methods = methodDocs.get(clazz.getName());
        if (methods == null) return null;

        FieldDoc methodDoc = methods.get(methodName);
        return methodDoc != null ? methodDoc.getSummary() : null;
    }

    /**
     * 메소드의 전체 설명
     */
    public String getMethodDescription(Class<?> clazz, String methodName) {
        Map<String, FieldDoc> methods = methodDocs.get(clazz.getName());
        if (methods == null) return null;

        FieldDoc methodDoc = methods.get(methodName);
        return methodDoc != null ? methodDoc.getDescription() : null;
    }
}

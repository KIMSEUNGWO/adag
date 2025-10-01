package com.jours.adag.javadoc;

import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.JavadocBlockTag;

import java.util.List;
import java.util.stream.Stream;

public class JavaDocParser {

    public static JavaDoc parse(String name, JavadocComment javadoc) {
        Javadoc parse = javadoc.parse();

        JavaDoc javaDoc = new JavaDoc();
        javaDoc.setName(name);
        // 첫 줄(summary) 이후의 내용을 description으로 사용
        String[] lines = parse.getDescription().toText().trim().split("\n", 2);

        if (lines.length < 2) {
            javaDoc.setSummary(lines[0].trim());
        } else {
            javaDoc.setSummary(lines[0].trim());
            javaDoc.setDescription(lines[1].trim());
        }

        List<MetaData> list = parse.getBlockTags().stream().map(blockTag -> {
            String tag = blockTag.getType().toString();
            String arguName = blockTag.getName().orElse(null);
            String description = blockTag.getContent().toText();
            return new MetaData(JavaDocTag.valueOf(tag), arguName, description);
        }).toList();
        if (!list.isEmpty()) {
            javaDoc.setMetaDatas(list);
        }
        return javaDoc;
    }

}

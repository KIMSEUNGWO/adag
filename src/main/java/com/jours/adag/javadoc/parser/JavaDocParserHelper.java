package com.jours.adag.javadoc.parser;

import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.javadoc.Javadoc;
import com.jours.adag.javadoc.BlockTag;
import com.jours.adag.javadoc.JavaDocTag;

import java.util.List;

public class JavaDocParserHelper {

    public FieldDoc parseToFieldDoc(String name, JavadocComment javadoc) {
        Javadoc parse = javadoc.parse();

        // 첫 줄(summary) 이후의 내용을 description으로 사용
        String[] lines = parse.getDescription().toText().trim().split("\n", 2);

        String summary = lines[0].trim();
        String description = lines.length >= 2 ? lines[1].trim() : null;

        return new FieldDoc(
            name,
            summary,
            description
        );
    }

    public MethodDoc parseToMethodDoc(String name, JavadocComment javadoc) {
        Javadoc parse = javadoc.parse();

        // 첫 줄(summary) 이후의 내용을 description으로 사용
        String[] lines = parse.getDescription().toText().trim().split("\n", 2);

        String summary = lines[0].trim();
        String description = lines.length >= 2 ? lines[1].trim() : null;

        List<BlockTag> list = parse.getBlockTags().stream().map(blockTag -> {
            String tag = blockTag.getType().toString();
            String arguName = blockTag.getName().orElse(null);
            String blockTagDescription = blockTag.getContent().toText();
            return new BlockTag(JavaDocTag.valueOf(tag), arguName, blockTagDescription);
        }).toList();

        return new MethodDoc(
            name,
            summary,
            description,
            list
        );
    }
}

package com.jours.adag;

import com.jours.adag.generator.ApiDocGenerator;
import com.jours.adag.javadoc.JavadocRepository;
import com.jours.adag.javadoc.parser.JavaDocParser;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApiDocsStarter {

    private final JavadocRepository javadocRepository;
    private final ApiDocGenerator apiDocGenerator;

    @PostConstruct
    public void init() {
        javadocRepository.init();
        apiDocGenerator.generateDocs();
    }
}

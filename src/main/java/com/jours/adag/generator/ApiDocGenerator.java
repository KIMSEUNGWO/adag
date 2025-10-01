package com.jours.adag.generator;

import com.jours.adag.config.ApiDocsConfigurer;
import com.jours.adag.config.configEntity.TagRegistry;
import com.jours.adag.entity.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Component
@RequiredArgsConstructor
public class ApiDocGenerator {

    private final RequestMappingHandlerMapping handlerMapping;
    private final ApiDocGeneratorHelper helper;
    private final ApiDocsConfigurer configurer;

    @PostConstruct
    public void generateDocs() {

        TagRegistry tagRegistry = configurer.getTagRegistry();

        handlerMapping.getHandlerMethods().forEach((mapping, handlerMethod) -> {

            Class<?> beanType = handlerMethod.getBeanType();
            if (tagRegistry.isRegistered(beanType)) {
                ApiDocInfo info = helper.getInfo(mapping, handlerMethod);
                info.setTagInfo(tagRegistry.getTagInfo(beanType));
                System.out.println(info);
            }

        });
    }

}

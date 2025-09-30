package com.jours.adag.generator;

import com.jours.adag.config.configEntity.ApiDocsConfigurer;
import com.jours.adag.config.configEntity.TagInfo;
import com.jours.adag.config.configEntity.TagRegistry;
import com.jours.adag.entity.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ApiDocGenerator {

    private final RequestMappingHandlerMapping handlerMapping;
    private final ApiDocGeneratorHelper helper;
    private final ApiDocsConfigurer configurer;

    @PostConstruct
    public void generateDocs() {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();

        TagRegistry tagRegistry = configurer.getTagRegistry();

        handlerMethods.forEach((mapping, handlerMethod) -> {

            Class<?> beanType = handlerMethod.getBeanType();
            if (!tagRegistry.isRegistered(beanType)) {
                return;
            }

            ApiDocInfo info = helper.getInfo(mapping, handlerMethod);
            info.setTagInfo(tagRegistry.getTagInfo(beanType));
            System.out.println(info);
        });
    }

}

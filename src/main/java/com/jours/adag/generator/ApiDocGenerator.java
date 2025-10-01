package com.jours.adag.generator;

import com.jours.adag.config.ApiDocsConfigurer;
import com.jours.adag.config.tag.TagRegistry;
import com.jours.adag.entity.*;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ApiDocGenerator {

    private final RequestMappingHandlerMapping handlerMapping;
    private final ApiDocGeneratorHelper helper;
    private final ApiDocsConfigurer configurer;
    @Getter
    private Map<Class<?>, List<ApiDocInfo>> docs = new HashMap<>();

    public void generateDocs() {

        TagRegistry tagRegistry = configurer.getTagRegistry();

        handlerMapping.getHandlerMethods().forEach((mapping, handlerMethod) -> {

            Class<?> beanType = handlerMethod.getBeanType();
            if (tagRegistry.isRegistered(beanType)) {
                ApiDocInfo info = helper.getInfo(beanType, mapping, handlerMethod);
                info.setTagInfo(tagRegistry.getTagInfo(beanType));
                if (!docs.containsKey(beanType)) {
                    docs.put(beanType, new ArrayList<>());
                };
                docs.get(beanType).add(info);
                System.out.println(info);
            }

        });
    }

}

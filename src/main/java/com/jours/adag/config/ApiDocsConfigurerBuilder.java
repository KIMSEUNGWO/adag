package com.jours.adag.config;

import com.jours.adag.config.tag.TagRegistry;

import java.util.function.Consumer;

public class ApiDocsConfigurerBuilder {

    private TagRegistry tagRegistry = new TagRegistry();
    private String baseUrl = "/adag";

    public ApiDocsConfigurerBuilder tag(Consumer<TagRegistry> tagRegistryConsumer) {
        tagRegistryConsumer.accept(tagRegistry);
        return this;
    }

    public ApiDocsConfigurerBuilder baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public ApiDocsConfigurer build() {
        return ApiDocsConfigurer.builder()
            .tagRegistry(tagRegistry)
            .baseUrl(baseUrl)
            .build();
    }
}

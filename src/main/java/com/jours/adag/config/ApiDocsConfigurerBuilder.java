package com.jours.adag.config;

import com.jours.adag.config.group.GroupConfigurer;
import com.jours.adag.config.tag.TagRegistry;

import java.util.function.Consumer;

public class ApiDocsConfigurerBuilder {

    private GroupConfigurer groupConfigurer = new GroupConfigurer();
    private TagRegistry tagRegistry = new TagRegistry();
    private String baseUrl = "/adag";

    public ApiDocsConfigurerBuilder tag(Consumer<TagRegistry> tagRegistryConsumer) {
        tagRegistryConsumer.accept(tagRegistry);
        return this;
    }

    public ApiDocsConfigurerBuilder group(Consumer<GroupConfigurer> groupConfigurerConsumer) {
        groupConfigurerConsumer.accept(groupConfigurer);
        return this;
    }

    public ApiDocsConfigurerBuilder baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public ApiDocsConfigurer build() {
        return ApiDocsConfigurer.builder()
            .tagRegistry(tagRegistry)
            .groupConfigurer(groupConfigurer)
            .baseUrl(baseUrl)
            .build();
    }
}

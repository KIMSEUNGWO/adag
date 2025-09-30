package com.jours.adag.config.configEntity;

public class ApiDocsConfigurer {

    private TagRegistry tagRegistry;

    public ApiDocsConfigurer(TagRegistry tagRegistry) {
        this.tagRegistry = tagRegistry;
    }

    public TagRegistry getTagRegistry() {
        return tagRegistry;
    }
}

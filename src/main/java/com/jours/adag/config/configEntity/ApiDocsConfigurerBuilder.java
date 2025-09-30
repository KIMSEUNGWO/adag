package com.jours.adag.config.configEntity;

import java.util.function.Consumer;

public class ApiDocsConfigurerBuilder {

    private TagRegistry tagRegistry = new TagRegistry();

    public ApiDocsConfigurerBuilder setTagRegistry(Consumer<TagRegistry> tagRegistryConsumer) {
        tagRegistryConsumer.accept(tagRegistry);
        return this;
    }

    public ApiDocsConfigurer build() {
        return new ApiDocsConfigurer(
            tagRegistry
        );
    }
}

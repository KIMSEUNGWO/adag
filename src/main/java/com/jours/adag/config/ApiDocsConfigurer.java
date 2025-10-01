package com.jours.adag.config;

import com.jours.adag.config.group.GroupConfigurer;
import com.jours.adag.config.tag.TagRegistry;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiDocsConfigurer {

    private final TagRegistry tagRegistry;
    private final GroupConfigurer groupConfigurer;
    private final String baseUrl;

}

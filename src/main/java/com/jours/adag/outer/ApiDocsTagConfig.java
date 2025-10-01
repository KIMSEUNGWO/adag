package com.jours.adag.outer;

import com.jours.adag.config.ApiDocsConfigurer;
import com.jours.adag.config.ApiDocsConfigurerBuilder;
import com.jours.adag.config.group.GroupConfigurer;
import com.jours.adag.config.group.Sort;
import com.jours.adag.config.group.SortType;
import com.jours.adag.config.group.SortTypeConfig;
import com.jours.adag.outer.server.TestController;
import com.jours.adag.outer.server.scanTestServer.ScanTestController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiDocsTagConfig {

    @Bean
    ApiDocsConfigurerBuilder apiDocsConfigurerBuilder() {
        return new ApiDocsConfigurerBuilder();
    }


    @Bean
    ApiDocsConfigurer apiDocsConfigurer(ApiDocsConfigurerBuilder builder) {
        builder
            .baseUrl("/k")
            .group(group -> group
                .type(GroupConfigurer.GroupType.NONE)
                .sort(sort -> sort.config(SortType.URL, Sort.ASC))
            )
            .tag(tagRegistry -> tagRegistry
                .register(TestController.class, "테스트", "테스트 내용입니다.")
                .register(ScanTestController.class, "두번째 테스트", "두번째 테스트 내용입니다."))

        ; return builder.build();
    }

}

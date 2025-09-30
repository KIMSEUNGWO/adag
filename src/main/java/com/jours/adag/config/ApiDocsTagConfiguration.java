package com.jours.adag.config;

import com.jours.adag.config.configEntity.ApiDocsConfigurer;
import com.jours.adag.config.configEntity.ApiDocsConfigurerBuilder;
import com.jours.adag.server.TestController;
import com.jours.adag.server.scanTestServer.ScanTestController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiDocsTagConfiguration {

    @Bean
    ApiDocsConfigurerBuilder apiDocsConfigurerBuilder() {
        return new ApiDocsConfigurerBuilder();
    }


    @Bean
    ApiDocsConfigurer config(ApiDocsConfigurerBuilder builder) {

        builder.setTagRegistry(tagRegistry -> tagRegistry
            .register(TestController.class, "테스트", "테스트 내용입니다.")
            .register(ScanTestController.class, "두번째 테스트", "두번째 테스트 내용입니다.")
        );
        return builder.build();
    }

}

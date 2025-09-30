//package com.jours.adag.config;
//
//import com.jours.adag.generator.ApiDocGenerator;
//import com.jours.adag.generator.ApiDocGeneratorHelper;
//import com.jours.adag.generator.ApiDocProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
//
//@Configuration
//@EnableConfigurationProperties(ApiDocProperties.class)
//public class ApiDocsAutoConfiguration {
//
//    @Bean
//    public ApiDocGenerator apiDocGenerator(
//        RequestMappingHandlerMapping handlerMapping,
//        ApiDocGeneratorHelper helper,
//        ApiDocProperties properties) {
//        return new ApiDocGenerator(handlerMapping, helper, properties);
//    }
//
//    @Bean
//    public ApiDocGeneratorHelper apiDocGeneratorHelper() {
//        return new ApiDocGeneratorHelper();
//    }
//}

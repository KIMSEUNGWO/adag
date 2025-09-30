# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

ADAG is a Spring Boot-based API documentation generator that automatically extracts API information from Spring MVC controllers and generates documentation by analyzing request mappings, parameters, and JavaDoc comments.

## Build and Run Commands

```bash
# Build the project
./gradlew build

# Run the application
./gradlew bootRun

# Run tests
./gradlew test

# Run a single test
./gradlew test --tests AdagApplicationTests

# Clean build artifacts
./gradlew clean
```

## Architecture

### Core Documentation Generation Flow

1. **Tag Registration** (`ApiDocsTagConfiguration`): Controllers are registered with tags (name and description) via `ApiDocsConfigurerBuilder`
2. **API Scanning** (`ApiDocGenerator`): At application startup (`@PostConstruct`), scans all `RequestMappingHandlerMapping` endpoints
3. **Information Extraction** (`ApiDocGeneratorHelper`): Extracts URL patterns, HTTP methods, path variables, query parameters, request/response bodies, and nested field structures
4. **JavaDoc Integration** (`JavadocRepository`): Loads pre-compiled JavaDoc metadata from `api-docs-javadoc.json` to enrich field descriptions

### JavaDoc Processing

- **Compile-time**: `ApiDocProcessor` (annotation processor) extracts JavaDoc comments from classes and fields during compilation and generates `api-docs-javadoc.json`
- **Runtime**: `JavadocRepository` loads the JSON file and provides field descriptions to the documentation generator

### Key Components

- **`ApiDocGenerator`**: Main orchestrator that filters registered controllers and generates documentation
- **`ApiDocGeneratorHelper`**: Analyzes Spring MVC mappings and Java reflection to extract API details, including recursive analysis of nested objects and collections
- **`ApiDocsConfigurer`**: Configuration holder that stores the `TagRegistry`
- **`TagRegistry`**: Maps controller classes to their tag metadata (name/description)
- **`ApiDocInfo`**: Unified data structure representing all API endpoint information
- **Entity classes** (`PathVariableInfo`, `QueryParamInfo`, `RequestBodyInfo`, `ResponseBodyInfo`, `FieldInfo`): Type-safe representations of API components

### Configuration Pattern

To document APIs, implement an `@Configuration` class that uses `ApiDocsConfigurerBuilder`:

```java
@Bean
ApiDocsConfigurer config(ApiDocsConfigurerBuilder builder) {
    builder.setTagRegistry(tagRegistry -> tagRegistry
        .register(ControllerClass.class, "Tag Name", "Description")
    );
    return builder.build();
}
```

Only controllers registered in the `TagRegistry` will be processed for documentation.

## Technology Stack

- Java 21
- Spring Boot 3.5.6
- Spring Web MVC
- Thymeleaf
- Lombok
- Gradle with Gradle Wrapper
- Jackson (for JSON processing in annotation processor)
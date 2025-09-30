package com.jours.adag.entity;

import com.jours.adag.config.configEntity.TagInfo;
import lombok.Builder;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Set;

@ToString
@Builder
public class ApiDocInfo {

    private String summary;
    private String description;

    private String url;
    private String httpMethod;
    private List<PathVariableInfo> pathVariables;
    private List<QueryParamInfo> queryParams;
    private RequestBodyInfo requestBody;
    private ResponseBodyInfo responseBody;
    private Set<MediaType> consumes;
    private Set<MediaType> produces;
    private String controllerClass;
    private String methodName;
    @Setter
    private TagInfo tagInfo;
}

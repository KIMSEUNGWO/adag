package com.jours.adag.entity;

import com.jours.adag.config.tag.TagInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Set;

@Getter
@ToString
@Builder
public class ApiDocInfo {

    // 컨트롤러 태그
    @Setter
    private TagInfo tagInfo;

    // 메소드 기본정보
    private String methodName;
    private String summary;
    private String description;

    // 메소드 메타데이터
    private String url;
    private HttpMethod method;
    private List<PathVariableInfo> pathVariables;
    private List<QueryParamInfo> queryParams;
    private RequestBodyInfo requestBody;
    private ResponseBodyInfo responseBody;
    private Set<MediaType> consumes;
    private Set<MediaType> produces;
}

package com.jours.adag.generator;

import com.jours.adag.entity.*;
import com.jours.adag.javadoc.BlockTag;
import com.jours.adag.javadoc.JavaDocService;
import com.jours.adag.javadoc.parser.FieldDoc;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.util.pattern.PathPattern;

import java.lang.reflect.*;
import java.util.*;

@Component
@RequiredArgsConstructor
public class ApiDocGeneratorHelper {

    private final JavaDocService javaDocService;

    public ApiDocInfo getInfo(Class<?> beanType, RequestMappingInfo mapping, HandlerMethod handlerMethod) {

        String url = getUrl(mapping);
        HttpMethod httpMethod = getHttpMethod(mapping);
        Set<MediaType> consumes = getContentTypes(mapping);
        Set<MediaType> produces = getProduces(mapping);

        Method method = handlerMethod.getMethod();
        Parameter[] parameters = method.getParameters();

        String summary = javaDocService.getMethodSummary(beanType, method.getName());
        String description = javaDocService.getMethodDescription(beanType, method.getName());

        List<PathVariableInfo> pathVariables = getPathVariables(parameters);
        List<QueryParamInfo> queryParams = getQueryParams(parameters);
        RequestBodyInfo requestBody = getRequestBodyInfo(beanType, method.getName(), parameters);
        ResponseBodyInfo responseBody = getResponseBodyInfo(method);

        return ApiDocInfo.builder()
            .url(url)
            .method(httpMethod)
            .pathVariables(pathVariables)
            .queryParams(queryParams)
            .requestBody(requestBody)
            .responseBody(responseBody)
            .consumes(consumes)
            .produces(produces)
            .methodName(method.getName())
            .summary(summary)
            .description(description)
            .build();
    }

    String getUrl(RequestMappingInfo mapping) {
        Set<PathPattern> patterns = mapping.getPathPatternsCondition() != null
            ? mapping.getPathPatternsCondition().getPatterns()
            : Collections.emptySet();
        return patterns.isEmpty() ? "" : patterns.iterator().next().getPatternString();
    }

    private HttpMethod getHttpMethod(RequestMappingInfo mapping) {
        Set<RequestMethod> methods = mapping.getMethodsCondition().getMethods();
        return methods.isEmpty() ? HttpMethod.GET : methods.iterator().next().asHttpMethod();
    }

    private Set<MediaType> getContentTypes(RequestMappingInfo mapping) {
        return mapping.getConsumesCondition().getConsumableMediaTypes();
    }

    private Set<MediaType> getProduces(RequestMappingInfo mapping) {
        return mapping.getProducesCondition().getProducibleMediaTypes();
    }

    private List<PathVariableInfo> getPathVariables(Parameter[] parameters) {
        List<PathVariableInfo> pathVariables = new ArrayList<>();
        for (Parameter param : parameters) {
            PathVariable pathVar = param.getAnnotation(PathVariable.class);
            if (pathVar != null) {
                String name = pathVar.value().isEmpty() ? param.getName() : pathVar.value();

                pathVariables.add(new PathVariableInfo(
                    name,
                    param.getType(),
                    pathVar.required(),
                    "미완성"
                ));
            }
        }
        return pathVariables;
    }

    private List<QueryParamInfo> getQueryParams(Parameter[] parameters) {
        List<QueryParamInfo> queryParams = new ArrayList<>();
        for (Parameter param : parameters) {
            RequestParam reqParam = param.getAnnotation(RequestParam.class);
            if (reqParam != null) {
                String name = reqParam.value().isEmpty() ? param.getName() : reqParam.value();
                queryParams.add(new QueryParamInfo(
                    name,
                    param.getType(),
                    reqParam.required(),
                    reqParam.defaultValue(),
                    "미완성"
                ));
            }
        }
        return queryParams;
    }

    private RequestBodyInfo getRequestBodyInfo(Class<?> beanType, String methodName, Parameter[] parameters) {
        RequestBodyInfo requestBody = null;
        for (Parameter param : parameters) {
            RequestBody body = param.getAnnotation(RequestBody.class);
            if (body != null) {
                Class<?> type = param.getType();
                if (isSimpleType(type)) {
//                    List<BlockTag> blockTags = javaDocService.getBlockTags(beanType, methodName);

                }
//                requestBody = new RequestBodyInfo(
//                    param.getType(),
//                    body.required()
//                );
            }
        }
        if (requestBody != null) {
            List<FieldInfo> fields = analyzeClassFields(requestBody.getType());
//            requestBody.setFields(fields);
        }
        return requestBody;
    }

    private ResponseBodyInfo getResponseBodyInfo(Method method) {
        Class<?> returnType = method.getReturnType();

        // ResponseEntity 처리
        if (returnType.equals(ResponseEntity.class)) {
            Type genericReturnType = method.getGenericReturnType();
            if (genericReturnType instanceof ParameterizedType paramType) {
                Type[] actualTypes = paramType.getActualTypeArguments();
                if (actualTypes.length > 0 && actualTypes[0] instanceof Class) {
                    returnType = (Class<?>) actualTypes[0];
                }
            }
        }

        ResponseBodyInfo responseBody = new ResponseBodyInfo(
            returnType.getSimpleName(),
            returnType
        );

        if (!returnType.equals(void.class) && !returnType.equals(String.class) && !isSimpleType(returnType)) {
            List<FieldInfo> fields = analyzeClassFields(returnType);
            responseBody.setFields(fields);
        }
        return responseBody;
    }

    // 클래스의 필드 구조 분석
    private List<FieldInfo> analyzeClassFields(Class<?> clazz) {
        List<FieldInfo> fields = new ArrayList<>();

        for (Field field : clazz.getDeclaredFields()) {
            // static, transient 필드는 제외
            if (Modifier.isStatic(field.getModifiers()) || Modifier.isTransient(field.getModifiers())) {
                continue;
            }

            String fieldName = field.getName();
            String fieldType = field.getType().getSimpleName();

            FieldInfo fieldInfo = new FieldInfo();
            fieldInfo.setName(fieldName);
            fieldInfo.setType(fieldType);

            // JavaDoc 추출
            String summary = javaDocService.getFieldSummary(clazz, fieldName);
            String description = javaDocService.getFieldDescription(clazz, fieldName);
            fieldInfo.setSummary(summary);
            fieldInfo.setDescription(description);

            // 중첩 객체 처리
            if (!isSimpleType(field.getType()) && !isCollectionType(field.getType())) {

                List<FieldInfo> nestedFields = analyzeClassFields(field.getType());
                fieldInfo.setNestedFields(nestedFields);
            }

            // List, Set 등 컬렉션 타입 처리
            if (isCollectionType(field.getType())) {
                if (field.getGenericType() instanceof ParameterizedType paramType) {
                    Type[] actualTypes = paramType.getActualTypeArguments();
                    if (actualTypes.length > 0 && actualTypes[0] instanceof Class<?> elementType) {
                        if (!isSimpleType(elementType)) {
                            List<FieldInfo> nestedFields = analyzeClassFields(elementType);
                            fieldInfo.setNestedFields(nestedFields);
                        }
                    }
                }
            }

            fields.add(fieldInfo);
        }

        return fields;
    }

    private boolean isCollectionType(Class<?> type) {
        return Collection.class.isAssignableFrom(type) ||
            type.isArray();
    }
    // 단순 타입인지 체크 (String, Integer, int, Boolean 등)
    private boolean isSimpleType(Class<?> clazz) {
        return clazz.isPrimitive() ||
            clazz.equals(String.class) ||
            clazz.equals(Integer.class) ||
            clazz.equals(Long.class) ||
            clazz.equals(Double.class) ||
            clazz.equals(Float.class) ||
            clazz.equals(Boolean.class) ||
            clazz.equals(Character.class) ||
            clazz.equals(Byte.class) ||
            clazz.equals(Short.class);
    }

}

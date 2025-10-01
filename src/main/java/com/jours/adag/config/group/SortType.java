package com.jours.adag.config.group;

import com.jours.adag.entity.ApiDocInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Comparator;

@AllArgsConstructor
public enum SortType {
    METHOD_NAME(Comparator.comparing(ApiDocInfo::getMethodName)),
    HTTP_METHOD(Comparator.comparing(ApiDocInfo::getMethod)),
    URL(Comparator.comparing(ApiDocInfo::getUrl)),;

    private final Comparator<ApiDocInfo> comparator;

    public Comparator<ApiDocInfo> sort(Sort sort) {
        if (sort == Sort.DESC) {
            return comparator.reversed();
        }
        return comparator;
    }
}

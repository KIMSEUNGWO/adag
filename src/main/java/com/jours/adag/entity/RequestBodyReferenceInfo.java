package com.jours.adag.entity;

import java.util.List;

public class RequestBodyReferenceInfo extends RequestBodyInfo {

    private List<FieldInfo> fields;


    public RequestBodyReferenceInfo(Class<?> type, boolean required, List<FieldInfo> fields) {
        super(type, required);
        this.fields = fields;
    }
}

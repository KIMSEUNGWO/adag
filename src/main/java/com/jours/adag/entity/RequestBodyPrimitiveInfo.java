package com.jours.adag.entity;

public class RequestBodyPrimitiveInfo extends RequestBodyInfo {

    private final String summary;
    private final String description;

    public RequestBodyPrimitiveInfo(String summary, String description, Class<?> type, boolean required) {
        super(type, required);
        this.summary = summary;
        this.description = description;
    }
}

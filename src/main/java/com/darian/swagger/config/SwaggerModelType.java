package com.darian.swagger.config;


public enum SwaggerModelType {
    STRING("String"),
    INTEGER("Integer"),
    LONG("Long"),
    DATE("Date"),
    BYTE("Byte");

    SwaggerModelType(String modelType) {
        this.modelType = modelType;
    }

    private String modelType;

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }
}
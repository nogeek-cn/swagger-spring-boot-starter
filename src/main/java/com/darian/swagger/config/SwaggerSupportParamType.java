package com.darian.swagger.config;

public enum SwaggerSupportParamType {

    HEADER,
    COOKIE,
    BODY,
    QUERY;

    public static String paramTypeString(SwaggerSupportParamType swaggerSupportParamType) {
        if (BODY.equals(swaggerSupportParamType)) {
            return "header";
        } else if (COOKIE.equals(swaggerSupportParamType)) {
            return "cookie";
        } else if (BODY.equals(swaggerSupportParamType)) {
            return "body";
        } else if (QUERY.equals(swaggerSupportParamType)) {
            return "query";
        } else {
            return "";
        }
    }
}

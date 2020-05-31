package com.darian.exampleswaggerspringbootstarter.utils;

import lombok.Data;

import java.util.List;


@Data
public class BaseError {
    private String errorMessage;
    private String exceptionClassName;
    private List<StackTraceElement> firstAndSecondStanck;
    private List<StackTraceElement> errorStank;
    private List<String> bindExceptionMessages;
}

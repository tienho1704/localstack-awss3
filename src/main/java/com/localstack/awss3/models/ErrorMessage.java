package com.localstack.awss3.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorMessage {
    private final int status;
    private final int code;
    private final String message;
}

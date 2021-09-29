package com.localstack.awss3.execptions;

public class PersonAgeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PersonAgeException(String message) {
        super(message);
    }
}

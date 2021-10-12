package com.jjcdutra2015.libraryapi.exception;

import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

public class ApiErrors {
    private List<String> errors;

    public ApiErrors(BindingResult bindingResult) {
        this.errors = new ArrayList<>();
        bindingResult.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
    }

    public List<String> getErrors() {
        return errors;
    }
}

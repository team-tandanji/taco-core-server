package com.tandanji.taco_core_server.application;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ValidationService {
    public <T> void checkValid(@NotNull @Valid T validationTarget) {
        //do nothing
    }

    public <T extends CharSequence> void checkValidBlank(@NotBlank T validationTarget) {

    }
}

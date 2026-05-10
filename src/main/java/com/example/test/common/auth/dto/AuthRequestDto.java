package com.example.test.common.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AuthRequestDto {

    @NotBlank(message = "userEmail 비면 안됩니다.")
    private String userEmail;

    @NotBlank(message = "userPassword 비면 안됩니다.")
    private String userPassword;
}

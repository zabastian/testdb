package com.example.test.common.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthRequestDto {

/*    @NotBlank(message = "userId가 빈값입니다.")
    private Long userId;*/

    @NotBlank(message = "userEmail 비면 안됩니다.")
    private String userEmail;

    @NotBlank(message = "userPassword 비면 안됩니다.")
    private String userPassword;

    @NotBlank(message = " recaptcha 비면 안됩니다.")
    private String recaptcha;
}

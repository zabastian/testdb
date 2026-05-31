package com.example.test.common.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class SignupResponseDto {

    private String userEmail;

    private String userPassword;

}

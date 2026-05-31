package com.example.test.common.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    private  String userEmail;

    private String userPassword;
}

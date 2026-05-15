package com.example.test.common.auth.dto;

import com.example.test.common.user.entity.UserRole;
import jakarta.servlet.http.Cookie;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class AuthResponseDto {

    @NotBlank(message = "userEmail 오류")
    private String userEmail;

    @NotBlank(message = "userRole 오류")
    private String userPassword;

    private String accessToken;

    private Cookie cookie;

    public AuthResponseDto(String userEmail, String userPassword) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public AuthResponseDto(String userEmail, String accessToken, Cookie cookie) {
        this.userEmail = userEmail;
        this.accessToken = accessToken;
        this.cookie = cookie;
    }

}

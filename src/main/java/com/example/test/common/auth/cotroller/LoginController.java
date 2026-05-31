package com.example.test.common.auth.cotroller;

import com.example.test.common.auth.dto.AuthRequestDto;
import com.example.test.common.auth.dto.AuthResponseDto;
import com.example.test.common.auth.dto.SignupRequestDto;
import com.example.test.common.auth.dto.SignupResponseDto;
import com.example.test.common.auth.service.LoginService;
import com.example.test.common.user.dto.UserResponse;
import com.example.test.cookie.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {

    private final LoginService loginService;
    // final은 한번 연결된 객체 연결 못하게 하는 것

    @PostMapping("/signup")
    ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto signupRequestDto) {
        SignupResponseDto signupResponseDto = loginService.signup(signupRequestDto);
        return ResponseEntity.ok(signupResponseDto);
    }

    @PostMapping("/login")
    ResponseEntity<AuthResponseDto> login(
            @RequestBody AuthRequestDto authRequestDto,
            HttpServletResponse response
            ) {
        AuthResponseDto authResponseDto = loginService.login(authRequestDto);

        Cookie cookie = CookieUtil.createCookie(authResponseDto.getAccessToken());

        response.addCookie(cookie);

        return ResponseEntity.ok(authResponseDto);
    }
}

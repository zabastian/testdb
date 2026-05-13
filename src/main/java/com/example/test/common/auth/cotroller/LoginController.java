package com.example.test.common.auth.cotroller;

import com.example.test.common.auth.dto.AuthRequestDto;
import com.example.test.common.auth.dto.AuthResponseDto;
import com.example.test.common.auth.service.LoginService;
import com.example.test.common.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;
    // final은 한번 연결된 객체 연결 못하게 하는 것

    @PostMapping("/signup")
    ResponseEntity<AuthResponseDto> signup(@RequestBody AuthRequestDto authRequestDto) {
        AuthResponseDto authResponseDto = loginService.signup(authRequestDto);
        return ResponseEntity.ok(authResponseDto);
    }

    @PostMapping("/login")
    ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto) {
        AuthResponseDto authResponseDto = loginService.login(authRequestDto);
        return ResponseEntity.ok(authResponseDto);
    }


}

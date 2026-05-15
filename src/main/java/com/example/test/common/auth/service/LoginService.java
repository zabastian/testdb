package com.example.test.common.auth.service;

import com.example.test.common.auth.dto.AuthRequestDto;
import com.example.test.common.auth.dto.AuthResponseDto;
import com.example.test.common.auth.repostitory.LoginRepository;
import com.example.test.common.user.entity.User;
import com.example.test.common.user.entity.UserRole;
import com.example.test.security.PasswordEncoder;
import com.example.test.token.TokenService;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;
    private final TokenService tokenService;

    public AuthResponseDto signup(AuthRequestDto authRequestDto) {

        /*User user = new User();
        user.setUserEmail(authRequestDto.getUserEmail());
        user.setUserPassword(authRequestDto.getUserPassword());
        user.setUserRole(UserRole.OWNER);*/
        String encodedPassword = PasswordEncoder.encode(authRequestDto.getUserPassword());


        User user = new User(authRequestDto.getUserEmail(), encodedPassword, UserRole.OWNER);

        User savedUser = loginRepository.save(user);

        return new AuthResponseDto(savedUser.getUserEmail(), savedUser.getUserPassword());
    }

    public AuthResponseDto login(AuthRequestDto authRequestDto) {

        User user = loginRepository.findByUserEmail(authRequestDto.getUserEmail())
                .orElseThrow(()-> new RuntimeException("유저없음"));

        if (!PasswordEncoder.matches(authRequestDto.getUserPassword(), user.getUserPassword())) {
            throw new RuntimeException("LOGIN_FAILED");
        }

        String accessToken = tokenService.createAccessToken(user.getId(), user.getUserRole());

        Cookie cookie = new Cookie("accessToken", accessToken);

        cookie.setPath("/");
        cookie.setMaxAge(3600);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);

        return new AuthResponseDto(user.getUserEmail(), accessToken, cookie);
    }
}

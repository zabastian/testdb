package com.example.test.common.auth.service;

import com.example.test.common.auth.dto.AuthRequestDto;
import com.example.test.common.auth.dto.AuthResponseDto;
import com.example.test.common.auth.repostitory.LoginRepository;
import com.example.test.common.user.entity.User;
import com.example.test.common.user.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;

    public AuthResponseDto signup(AuthRequestDto authRequestDto) {

        /*User user = new User();
        user.setUserEmail(authRequestDto.getUserEmail());
        user.setUserPassword(authRequestDto.getUserPassword());
        user.setUserRole(UserRole.OWNER);*/

        User user = new User(authRequestDto.getUserEmail(), authRequestDto.getUserPassword(), UserRole.OWNER);

        User savedUser = loginRepository.save(user);

        return new AuthResponseDto(savedUser.getUserEmail(), savedUser.getUserPassword());
    }
}

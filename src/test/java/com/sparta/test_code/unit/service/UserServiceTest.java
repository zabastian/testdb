package com.sparta.test_code.unit.service;


import ch.qos.logback.classic.Logger;
import com.sparta.test_code.dto.UserCreationDto;
import com.sparta.test_code.dto.UserResponseDto;
import com.sparta.test_code.entity.User;
import com.sparta.test_code.respository.UserRepository;
import com.sparta.test_code.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Test
    void 유저_저장_성공_테스트() {
        //given
        UserCreationDto userCreationDto = new UserCreationDto("김현준", "email", "password");
        User user = new User("김현준", "email", "password");

        when(userRepository.save(any(User.class))).thenReturn();

        //when
        UserResponseDto userResponseDto = userService.saveUser(userCreationDto);

        //then
        assertThat(actualResult.getName()).isEqualTo("김현준");
        assertThat(actualResult.getEmail()).isEqualTo("김현준");


        //행위검증
        verify(userRepository, times(1)).save(any(User.class));
    }

}

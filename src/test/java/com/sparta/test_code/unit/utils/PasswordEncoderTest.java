package com.sparta.test_code.unit.utils;

import com.sparta.test_code.utils.PasswordEncoder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class PasswordEncoderTest {

    @Test
    void 비밀번호_검사_성공() {
        //given
        String rawPassword = "12356";
        String encodePassword = PasswordEncoder.encode(rawPassword);

        //when
        boolean actualResult = PasswordEncoder.matches(rawPassword, encodePassword);
        //then
        boolean expectedResult = true;
        assertThat(actualResult).isEqualTo(expectedResult);
        assertThat(encodePassword);

    }
}

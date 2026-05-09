package com.sparta.test_code.unit.repository;

import com.sparta.test_code.entity.User;
import com.sparta.test_code.respository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;

@DataJpaTest

public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    void 유저_저장_성공_테스() {
        //given
        User user = new User("김현준", "email.com", "password");
        //when
        User actualResult = userRepository.save(user);
        //then
        assertThat(actualResult)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(user);


    }

}

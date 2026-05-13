package com.example.test.common.auth.repostitory;

import com.example.test.common.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);

    Optional<User> findByUserEmail(String userEmail);
 }

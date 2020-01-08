package com.proiect.inginerie.software.repository;


import com.proiect.inginerie.software.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByUsernameOrEmail(String username,String email);



    User getUserByUsername(String username);

}

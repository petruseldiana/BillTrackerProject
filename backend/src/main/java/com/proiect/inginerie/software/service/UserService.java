package com.proiect.inginerie.software.service;


import com.proiect.inginerie.software.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> getUserByUsername(String username);

    User findOne(String username);

    Optional<User> findById(Long id);

    User save(User user);

    User update(User user);

    List<User> findAll();
}

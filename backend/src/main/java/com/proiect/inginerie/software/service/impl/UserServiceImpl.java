package com.proiect.inginerie.software.service.impl;

import com.proiect.inginerie.software.repository.UserRepository;
import com.proiect.inginerie.software.entity.User;
import com.proiect.inginerie.software.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public Optional<User>  findById(Long id) {

        return userRepository.findById(id);
    }

    @Override
    public User findOne(String username) {
        return userRepository.getUserByUsername(username);
    }


    @Override
    public User save(User user) {
        Optional<User> optionalUser;

        optionalUser = userRepository.findUserByUsernameOrEmail(user.getUsername(),user.getEmail());

        User user1 = optionalUser.isPresent() ? null : userRepository.save(user);

        return ofNullable(user1)
                .orElseThrow(() -> new EntityExistsException("User already exist: " + user.getUsername()));
    }

    @Override
    public User update(User user) {
        User user1 = userRepository.save(user);

        return ofNullable(user1)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find user with ID: " + user.getId()));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}

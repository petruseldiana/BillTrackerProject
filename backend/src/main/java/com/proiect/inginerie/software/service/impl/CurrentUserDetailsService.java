package com.proiect.inginerie.software.service.impl;


import com.proiect.inginerie.software.entity.CurrentUser;
import com.proiect.inginerie.software.entity.User;
import com.proiect.inginerie.software.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CurrentUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public CurrentUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CurrentUser loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email=%s was not found", username)));
        return new CurrentUser(user);
    }

    // This method is used by JWTAuthenticationFilter
    @Transactional
    public CurrentUser loadUserById(Long id)throws UsernameNotFoundException {
        User user = userService.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return new CurrentUser(user);
    }
}

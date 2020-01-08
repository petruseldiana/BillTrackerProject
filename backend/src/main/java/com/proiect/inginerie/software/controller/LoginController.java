package com.proiect.inginerie.software.controller;

import com.proiect.inginerie.software.controller.dtos.LoginUserDTO;
import com.proiect.inginerie.software.controller.dtos.UserDTO;
import com.proiect.inginerie.software.config.JwtTokenUtil;
import com.proiect.inginerie.software.entity.*;
import com.proiect.inginerie.software.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Api
@Controller
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @CrossOrigin
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody LoginUserDTO loginUserDTO) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserDTO.getUsername(), loginUserDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenUtil.generateToken(authentication);
        CurrentUser userPrincipal = (CurrentUser) authentication.getPrincipal();

        return ResponseEntity.ok(new AuthToken(jwt, userPrincipal.getUsername(), userPrincipal.getId()));
    }

    @CrossOrigin
    @RequestMapping(value = "/getCurrentUser", method = RequestMethod.GET)
    @ModelAttribute("currentUser")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CurrentUser userPrincipal = (CurrentUser) authentication.getPrincipal();
        return (userPrincipal == null) ? (ResponseEntity<?>) ResponseEntity.badRequest() : ResponseEntity.ok(new ApiResponse(userPrincipal.getUsername(), userPrincipal.getId()));
    }


    @CrossOrigin
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<?> logout() throws AuthenticationException {
        Authentication authentication = new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean b) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return null;
            }
        };
        authentication.setAuthenticated(false);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication());
    }

    @RequestMapping(value = "/public/register", method = RequestMethod.POST)
    public @ResponseBody
    User register(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setAddress(userDTO.getAddress());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPasswordHash(encryptedPassword);
        user.setRole(Role.USER);

        return userService.save(user);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
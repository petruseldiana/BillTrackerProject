package com.proiect.inginerie.software.controller.impl;

import com.proiect.inginerie.software.controller.UserRestApi;
import com.proiect.inginerie.software.entity.User;
import com.proiect.inginerie.software.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
public class UserRestController implements UserRestApi {

    @Autowired
    private UserService userService;

//    @PreAuthorize("hasAuthority('USER')")
//    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
//    @Override
//    public User findById(@PathVariable("id") Long id) {
//        return userService.findById(id);
//    }

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(value = "/users/update", method = RequestMethod.POST)
    @Override
    public User update(@RequestBody User user) {
        return userService.update(user);
    }

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(value = "/users/get", method = RequestMethod.GET)
    @Override
    public List<User> findAll() {
        return userService.findAll();
    }

}

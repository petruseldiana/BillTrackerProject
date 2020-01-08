package com.proiect.inginerie.software.controller;

import com.proiect.inginerie.software.entity.User;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Api
@RequestMapping("/api")
public interface UserRestApi {

//    @GetMapping("/{id}")
//    User findById(@PathVariable Long id);

    @PostMapping("/update")
    User update(final User user);

    @GetMapping("/findAll")
    List<User> findAll();

}

package com.proiect.inginerie.software.controller.impl;

import com.proiect.inginerie.software.entity.CurrentUser;
import io.swagger.annotations.Api;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Api
@RequestMapping("/api")
public class CurrentUserController {

    @GetMapping("/currentUser")
    @ModelAttribute("currentUser")
    public CurrentUser getCurrentUser(Authentication authentication) {
        return (authentication == null) ? null : (CurrentUser) authentication.getPrincipal();
    }
}

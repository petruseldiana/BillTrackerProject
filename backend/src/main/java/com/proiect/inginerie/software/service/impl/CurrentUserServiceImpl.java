//package com.proiect.inginerie.software.service.impl;
//
//import com.proiect.inginerie.software.entity.CurrentUser;
//import com.proiect.inginerie.software.entity.Role;
//import com.proiect.inginerie.software.service.CurrentUserService;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CurrentUserServiceImpl implements CurrentUserService {
//    @Override
//    public boolean canAccessUser(CurrentUser currentUser, Long userId) {
//
//        return currentUser != null
//                && (currentUser.getRole() == Role.USER || currentUser.getId().equals(userId));
//    }
//
//}

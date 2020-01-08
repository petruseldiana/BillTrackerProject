package com.proiect.inginerie.software.service;

import com.proiect.inginerie.software.entity.CurrentUser;

public interface CurrentUserService {

    boolean canAccessUser(CurrentUser currentUser, Long userId);

}

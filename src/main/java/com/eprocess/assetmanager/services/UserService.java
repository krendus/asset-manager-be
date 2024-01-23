package com.eprocess.assetmanager.services;

import com.eprocess.assetmanager.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    public User getCurrentUser() {
        return getUserDetails();
    }

    static User getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if(principal instanceof User) {
            return (User) principal;
        }
        return null;
    }
}

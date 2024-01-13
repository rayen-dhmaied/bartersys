package com.horizon.bartersys.config;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SessionManagement {
        public static String getCurrentLoggedUser(){
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (!(authentication instanceof AnonymousAuthenticationToken)) {
                        String currentUserName = authentication.getName();
                        return currentUserName;
                }else{
                        throw new RuntimeException("No User");
                }
        }
}
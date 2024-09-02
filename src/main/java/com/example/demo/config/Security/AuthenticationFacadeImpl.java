package com.example.demo.config.Security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    @Override
    public AuthenticatedUser getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof AuthenticatedUser) {
            return (AuthenticatedUser) authentication.getPrincipal();
        }
        throw new RuntimeException("No authenticated user found");
        //return (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication();
    }
}

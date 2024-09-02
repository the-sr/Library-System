package com.example.demo.config.Security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    @Override
    public AuthenticatedUser getAuthentication() {
        return (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication();
    }
}

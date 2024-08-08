package com.example.demo.config.Security;

import com.example.demo.models.User;
import com.example.demo.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isEmpty()) throw new UsernameNotFoundException("User not found");
        User userDetails = user.get();
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(userDetails.getRole().name()));
        return new AuthenticatedUser(userDetails.getId(), userDetails.getEmail(), userDetails.getPassword(), authorities);
        //return new org.springframework.security.core.userdetails.User(userDetails.getEmail(), userDetails.getPassword(), authorities);
    }
}

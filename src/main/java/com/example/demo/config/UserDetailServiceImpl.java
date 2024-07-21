package com.example.demo.config;

import com.example.demo.models.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser=userRepo.findByUsername(username);
        User user=optionalUser.orElseThrow(()->new UsernameNotFoundException("User not found"));
        String role=(!user.isAdmin() && !user.isLibrarian()) ? "User": (!user.isAdmin()  ? "Librarian" : "Admin");
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(role)
                .build();
    }
}

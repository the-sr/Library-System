package library.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import library.config.jwt.JwtUtil;
import library.config.security.AuthenticationFacade;
import library.dto.req.LoginReq;
import library.dto.res.LoginRes;
import library.services.UserService;

@RestController
@AllArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final AuthenticationFacade facade;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq loginReq) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginReq.getUsername());
        final String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok().body(new LoginRes(token));
    }

    @GetMapping("/logged-in-user")
    public ResponseEntity<?> getLoggedInUser() {
        long userId = facade.getAuthentication().getUserId();
        return ResponseEntity.ok().body(userService.findById(userId));
    }
}

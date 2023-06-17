package com.examportal.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.examportal.entity.User;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticateController {

    private final AuthenticationService service;
    private final UserDetailsService userDetailsService;

    // generateToken
    @PostMapping("/register")
    public ResponseEntity<JwtResponse> registerUser(
            @RequestBody RegisterRequest request) throws Exception {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/generate-token")
    public ResponseEntity<JwtResponse> authenticate(@RequestBody JwtRequest request) {
        try {
            return ResponseEntity.ok(service.authenticate(request));
        } catch (Exception e) {
            System.out.println("User : " + e.getLocalizedMessage());
            throw new UsernameNotFoundException("User not found. ");
        }
    }

    /**
     * returns the details of current user
     */
    @GetMapping("/current-user")
    public User getCurrentUser(Principal principal) {
        return (User) this.userDetailsService.loadUserByUsername(principal.getName());
    }
}

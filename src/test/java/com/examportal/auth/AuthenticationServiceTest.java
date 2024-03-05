package com.examportal.auth;

import com.examportal.entity.User;
import com.examportal.repository.UserRepository;
import com.examportal.token.Token;
import com.examportal.token.TokenRepository;
import com.examportal.token.TokenType;
import com.examportal.config.JwtService;
import com.examportal.controller.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserController userController;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRegister() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        // set properties for registerRequest

        User user = new User();
        // set properties for user

        when(userController.createUser(any())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any())).thenReturn("dummyToken");

        JwtResponse response = authenticationService.register(registerRequest);

        assertNotNull(response);
        assertEquals("dummyToken", response.getToken());

        // Verify that userController.createUser() is called once
        verify(userController, times(1)).createUser(any());
        // Verify that tokenRepository.save() is called once
        verify(tokenRepository, times(1)).save(any());
    }

    @Test
    void testAuthenticate_ValidCredentials() throws Exception {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername("username");
        jwtRequest.setPassword("password");

        User user = new User();
        user.setUsername("username");
        user.setPassword("password");

        when(authenticationManager.authenticate(any())).thenReturn(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any())).thenReturn("dummyToken");

        JwtResponse response = authenticationService.authenticate(jwtRequest);

        assertNotNull(response);
        assertEquals("dummyToken", response.getToken());

        // Verify that userRepository.findByUsername() is called once
        verify(userRepository, times(1)).findByUsername("username");
        // Verify that tokenRepository.save() is called once
        verify(tokenRepository, times(1)).save(any());
    }

    @Test
    void testAuthenticate_InvalidCredentials() {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername("username");
        jwtRequest.setPassword("password");

        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Invalid credentials"));

        assertThrows(Exception.class, () -> authenticationService.authenticate(jwtRequest));

        // Verify that userRepository.findByUsername() is not called
        verify(userRepository, never()).findByUsername("username");
        // Verify that tokenRepository.save() is not called
        verify(tokenRepository, never()).save(any());
    }
}

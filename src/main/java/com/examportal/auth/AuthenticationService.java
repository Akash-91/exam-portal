package com.examportal.auth;

import com.examportal.config.JwtService;
import com.examportal.controller.UserController;
import com.examportal.repository.UserRepository;
import com.examportal.token.Token;
import com.examportal.token.TokenRepository;
import com.examportal.entity.*;
import com.examportal.token.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserController userController;

    public JwtResponse register(RegisterRequest request) throws Exception {
        var user = User.builder()
                .username(request.getUsername())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .profile("default.png")
                .enabled(true)
                .password(request.getPassword())
                .build();
        var savedUser = userController.createUser(user);
        var jwtToken = jwtService.generateToken(user);

        savedUser.ifPresent(su -> saveUserToken(su, jwtToken));
        return JwtResponse.builder()
                .token(jwtToken)
                .build();
    }

    public JwtResponse authenticate(JwtRequest request) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (DisabledException e) {
            throw new Exception("User disabled: " + e.getLocalizedMessage());
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid credentials: " + e.getLocalizedMessage());
        }

        var user = repository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return JwtResponse.builder().token(jwtToken).build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}

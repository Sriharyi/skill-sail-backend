package com.sriharyi.skillsail.authentication.service;


import com.sriharyi.skillsail.authentication.dto.AuthenticationRequest;
import com.sriharyi.skillsail.authentication.dto.AuthenticationResponse;
import com.sriharyi.skillsail.authentication.dto.RegisterRequest;
import com.sriharyi.skillsail.authentication.dto.UserDto;
import com.sriharyi.skillsail.authentication.model.Token;
import com.sriharyi.skillsail.authentication.model.User;
import com.sriharyi.skillsail.authentication.model.enums.TokenType;
import com.sriharyi.skillsail.authentication.repository.TokenRepository;
import com.sriharyi.skillsail.authentication.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepo;

    private final TokenRepository tokenRepo;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    public AuthenticationResponse register(RegisterRequest request) {
        UserDto userDto = UserDto.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(request.getRoles())
                .build();
        if (userRepo.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        User user = userService.addUser(userDto);
        var jwt = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, jwt);
        return AuthenticationResponse.builder()
                .accessToken(jwt)
                .refreshToken(refreshToken)
                .build();
    }

    private void revokeAllUserTokens(User user)
    {
        List<Token> validUserTokens = tokenRepo.findActiveTokensByUserId(user.getId());
        
        if(validUserTokens.isEmpty())
        {
                return;
        }

        validUserTokens.forEach(
                t -> {
                        t.setExpired(true);
                        t.setRevoked(true);
                }
        );
        tokenRepo.saveAll(validUserTokens); 
    }

    private void saveUserToken(User user, String token) {
        var usertoken = Token.builder()
                .user(user)
                .token(token)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepo.save(usertoken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var user = userRepo.findByEmail(request.getEmail()).orElseThrow();

        var jwt = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        revokeAllUserTokens(user);

        saveUserToken(user, jwt);

        return AuthenticationResponse.builder()
                .accessToken(jwt)
                .refreshToken(refreshToken)
                .build();
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if(authHeader == null || !authHeader.startsWith("Bearer "))
        {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUserName(refreshToken);    //todo  extract the UserEmail from jwt token
        if(userEmail != null)
        {
            User user = this.userRepo.findByEmail(userEmail).orElseThrow(()-> new UsernameNotFoundException("User not found"));

            if(jwtService.isTokenvalid(refreshToken, user) )
            {
                String accessToken = jwtService.generateToken(user);

                revokeAllUserTokens(user);

                saveUserToken(user, accessToken);

                AuthenticationResponse authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();

                // Manually construct the JSON string
                String json = String.format("{\"accessToken\":\"%s\", \"refreshToken\":\"%s\"}",
                        authResponse.getAccessToken(),
                        authResponse.getRefreshToken());

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);


            }
        }
    }
}

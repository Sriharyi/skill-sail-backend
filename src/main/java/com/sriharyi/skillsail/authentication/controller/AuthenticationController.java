package com.sriharyi.skillsail.authentication.controller;


import com.sriharyi.skillsail.authentication.dto.AuthenticationRequest;
import com.sriharyi.skillsail.authentication.dto.AuthenticationResponse;
import com.sriharyi.skillsail.authentication.dto.RegisterRequest;
import com.sriharyi.skillsail.authentication.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping(path = "/api/v1/auth")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    //Register Method call
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    //Validation Method call or authentication method call
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authentication(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.refreshToken(request, response);
    }

//    @GetMapping("/profile")
//    public ResponseEntity<UserDto> getProfile(HttpServletRequest request) {
//        return ResponseEntity.ok(service.getProfile(request));
//    }

//    @GetMapping("/get/{id}")
//    public ResponseEntity<UserDto> getUserById(@PathVariable("id") String id) {
//        return ResponseEntity.ok(service.getUserById(id));
//    }
//
}

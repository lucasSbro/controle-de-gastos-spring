package com.controle.controllers;


import com.controle.auth.auth.AuthenticationRequest;
import com.controle.auth.auth.AuthenticationResponse;
import com.controle.auth.auth.AuthenticationService;
import com.controle.auth.auth.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/registro")
    public ResponseEntity<AuthenticationResponse> registrar(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/autenticacao")
    public ResponseEntity<AuthenticationResponse> autenticar(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}

package com.khomsi.grid.main.authentication.controller;

import com.khomsi.grid.main.authentication.model.reponse.AuthenticationResponse;
import com.khomsi.grid.main.authentication.model.request.AuthenticationRequest;
import com.khomsi.grid.main.authentication.model.request.RegistrationRequest;
import com.khomsi.grid.main.authentication.service.impl.AuthenticationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
@Tag(name = "Game", description = "CRUD operation for Auth Controller")
public class AuthenticationController {
    private AuthenticationServiceImpl service;

    //FIXME SWAGGER fix
    @PostMapping("/login")
    @Operation(summary = "Log")
    public ResponseEntity<AuthenticationResponse> addNewUser(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

    @PostMapping("/registration")
    @Operation(summary = "Reg")
    public ResponseEntity<AuthenticationResponse> addNewUser(@RequestBody RegistrationRequest userInfo) {
        log.info("user={}", userInfo);
        return ResponseEntity.ok(service.registerUser(userInfo));
    }
}


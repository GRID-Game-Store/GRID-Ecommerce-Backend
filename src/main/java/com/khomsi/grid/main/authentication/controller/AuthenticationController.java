package com.khomsi.grid.main.authentication.controller;

import com.khomsi.grid.main.authentication.model.reponse.MessageResponse;
import com.khomsi.grid.main.authentication.model.request.AuthenticationRequest;
import com.khomsi.grid.main.authentication.model.request.RegistrationRequest;
import com.khomsi.grid.main.authentication.service.impl.AuthenticationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    //FIXME
    @PostMapping("/login")
    @Operation(summary = "Log")
    public ResponseEntity<?> singUp(@RequestBody AuthenticationRequest request) {
        //todo change this
        return service.login(request);
    }

    @PostMapping("/registration")
    @Operation(summary = "Reg")
    public ResponseEntity<MessageResponse> registerUser(@RequestBody RegistrationRequest userInfo) {
        log.info("user={}", userInfo);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registerUser(userInfo));
    }

    @PostMapping("/signout")
    @Operation(summary = "logout")
    public ResponseEntity<?> logout() {
        return service.logoutUser();
    }
}


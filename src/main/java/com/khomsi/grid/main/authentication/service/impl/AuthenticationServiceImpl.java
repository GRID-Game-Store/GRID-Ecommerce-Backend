package com.khomsi.grid.main.authentication.service.impl;

import com.khomsi.grid.main.authentication.exception.EmailException;
import com.khomsi.grid.main.authentication.model.enums.AuthProvider;
import com.khomsi.grid.main.authentication.model.reponse.MessageResponse;
import com.khomsi.grid.main.authentication.model.request.AuthenticationRequest;
import com.khomsi.grid.main.authentication.model.request.RegistrationRequest;
import com.khomsi.grid.main.security.jwt.JwtProvider;
import com.khomsi.grid.main.security.service.UserDetailsImpl;
import com.khomsi.grid.main.user.RoleRepository;
import com.khomsi.grid.main.user.UserInfoRepository;
import com.khomsi.grid.main.user.model.entity.ERole;
import com.khomsi.grid.main.user.model.entity.UserInfo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl {
    //FIXME
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    //    private final MailSender mailSender;
    private final PasswordEncoder passwordEncoder;
    private final UserInfoRepository userInfoRepository;
    private final RoleRepository roleRepository;

    public ResponseEntity<?> login(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtProvider.generateJwtCookie(userDetails);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).build();
    }

    @Transactional
    public MessageResponse registerUser(RegistrationRequest request) {
//        if (user.password() != null) {
//            //TODO
//            throw new PasswordException("PASSWORDS_DO_NOT_MATCH");
//        }
        //FIXME currently sends 401 instead of 409 in postman
        if (userInfoRepository.findByEmail(request.email()).isPresent()) {
            throw new EmailException("EMAIL_IN_USE");
        }
        //TODO
        UserInfo userInfo = UserInfo.builder()
                .email(request.email())
                .username(request.username()) // Set the username
                .password(passwordEncoder.encode(request.password()))
//                .firstName(request.firstName())
//                .lastName(request.lastName())
                .balance(BigDecimal.valueOf(0))
                .build();

        userInfo.setActive(true);
        //TODO
        userInfo.setRoles(Collections.singleton(roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."))));
        userInfo.setProvider(AuthProvider.LOCAL);
//        userInfo.setActivationCode(UUID.randomUUID().toString());
        userInfoRepository.save(userInfo);

        return MessageResponse.builder().response("User successfully registered.").build();
    }

    public ResponseEntity<?> logoutUser() {
        ResponseCookie jwtCookie = jwtProvider.getCleanJwtCookie();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }
}
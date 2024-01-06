package com.khomsi.grid.main.authentication.service.impl;

import com.khomsi.grid.main.authentication.exception.EmailException;
import com.khomsi.grid.main.authentication.model.enums.AuthProvider;
import com.khomsi.grid.main.authentication.model.reponse.MessageResponse;
import com.khomsi.grid.main.authentication.model.request.AuthenticationRequest;
import com.khomsi.grid.main.authentication.model.request.RegistrationRequest;
import com.khomsi.grid.main.security.exception.TokenRefreshException;
import com.khomsi.grid.main.security.jwt.JwtProvider;
import com.khomsi.grid.main.security.service.UserDetailsImpl;
import com.khomsi.grid.main.user.RoleRepository;
import com.khomsi.grid.main.user.UserInfoRepository;
import com.khomsi.grid.main.user.model.entity.ERole;
import com.khomsi.grid.main.user.model.entity.RefreshToken;
import com.khomsi.grid.main.user.model.entity.UserInfo;
import com.khomsi.grid.main.user.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
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
import java.util.Objects;

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
    private final RefreshTokenService refreshTokenService;

    public ResponseEntity<?> login(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtProvider.generateJwtCookie(userDetails);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        ResponseCookie jwtRefreshCookie = jwtProvider.generateRefreshJwtCookie(refreshToken.getToken());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString()).build();
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
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!Objects.equals(principle.toString(), "anonymousUser")) {
            Long userId = ((UserDetailsImpl) principle).getId();
            refreshTokenService.deleteByUserId(userId);
        }

        ResponseCookie jwtCookie = jwtProvider.getCleanJwtCookie();
        ResponseCookie jwtRefreshCookie = jwtProvider.getCleanJwtRefreshCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }

    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String refreshToken = jwtProvider.getJwtRefreshFromCookies(request);

        if ((refreshToken != null) && (refreshToken.length() > 0)) {
            return refreshTokenService.findByToken(refreshToken)
                    .map(refreshTokenService::verifyExpiration)
                    .map(RefreshToken::getUser)
                    .map(user -> {
                        ResponseCookie jwtCookie = jwtProvider.generateJwtCookie(user);
                        //TODO change everything here
                        return ResponseEntity.ok()
                                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                                .body(new MessageResponse("Token is refreshed successfully!"));
                    })
                    .orElseThrow(() -> new TokenRefreshException(refreshToken,
                            "Refresh token is not in database!"));
        }

        return ResponseEntity.badRequest().body(new MessageResponse("Refresh Token is empty!"));
    }
}
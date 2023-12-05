package com.khomsi.grid.main.authentication.service.impl;

import com.khomsi.grid.main.authentication.exception.ApiRequestException;
import com.khomsi.grid.main.authentication.exception.EmailException;
import com.khomsi.grid.main.authentication.model.enums.AuthProvider;
import com.khomsi.grid.main.authentication.model.enums.Role;
import com.khomsi.grid.main.authentication.model.reponse.AuthenticationResponse;
import com.khomsi.grid.main.authentication.model.request.AuthenticationRequest;
import com.khomsi.grid.main.authentication.model.request.RegistrationRequest;
import com.khomsi.grid.main.security.jwt.JwtService;
import com.khomsi.grid.main.user.UserInfoRepository;
import com.khomsi.grid.main.user.model.entity.UserInfo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl {
    //FIXME
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtProvider;
    //    private final MailSender mailSender;
    private final PasswordEncoder passwordEncoder;
    private final UserInfoRepository userInfoRepository;

    public AuthenticationResponse login(AuthenticationRequest request) {
        try {
            String email = request.email();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, request.password()));
            UserInfo user = userInfoRepository.findByEmail(email)
                    //TODO
                    .orElseThrow(() -> new ApiRequestException("EMAIL_NOT_FOUND"));
            String userRole = user.getRoles().iterator().next().name();
            String token = jwtProvider.generateToken(email, userRole);

            return AuthenticationResponse.builder()
                    .response(token)
                    .build();
        } catch (AuthenticationException e) {
            //TODO
            throw new ApiRequestException("INCORRECT_PASSWORD");
        }
    }

    @Transactional
    public AuthenticationResponse registerUser(RegistrationRequest user) {
//        if (user.password() != null) {
//            //TODO
//            throw new PasswordException("PASSWORDS_DO_NOT_MATCH");
//        }

        if (userInfoRepository.findByEmail(user.email()).isPresent()) {
            //TODO
            throw new EmailException("EMAIL_IN_USE");
        }
        //TODO
        UserInfo userInfo = UserInfo.builder()
                .email(user.email())
                .password(user.password())
                .username(user.userName()) // Set the username
                .firstName(user.firstName())
                .lastName(user.lastName())
                .balance(BigDecimal.valueOf(0))
                .build();

        userInfo.setActive(true);
        userInfo.setRoles(Collections.singleton(Role.USER));
        userInfo.setProvider(AuthProvider.LOCAL);
        userInfo.setActivationCode(UUID.randomUUID().toString());
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo);

//        sendEmail(user, "Activation code", "registration-template", "registrationUrl", "/activate/" + user.getActivationCode());
        return AuthenticationResponse.builder().response("User successfully registered.").build();
    }
}

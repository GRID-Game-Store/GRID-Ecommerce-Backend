//package com.khomsi.grid.main.authentication.service.impl;
//
//import com.khomsi.grid.main.authentication.model.enums.AuthProvider;
//import com.khomsi.grid.main.authentication.model.reponse.MessageResponse;
//import com.khomsi.grid.main.authentication.model.request.AuthenticationRequest;
//import com.khomsi.grid.main.authentication.model.request.RegistrationRequest;
//import com.khomsi.grid.main.handler.exception.GlobalServiceException;
//import com.khomsi.grid.main.security.jwt.JwtProvider;
//import com.khomsi.grid.main.security.oauth2.OAuth2UserInfo;
//import com.khomsi.grid.main.security.service.UserPrincipal;
//import com.khomsi.grid.main.user.RoleRepository;
//import com.khomsi.grid.main.user.UserInfoRepository;
//import com.khomsi.grid.main.user.model.entity.ERole;
//import com.khomsi.grid.main.user.model.entity.UserInfo;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseCookie;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.Collections;
//
//@Service
//@RequiredArgsConstructor
//public class AuthenticationServiceImpl {
//    private final JwtProvider jwtProvider;
//    private final AuthenticationManager authenticationManager;
//    //FIXME
//    //    private final MailSender mailSender;
//    private final PasswordEncoder passwordEncoder;
//    private final UserInfoRepository userInfoRepository;
//    private final RoleRepository roleRepository;
//
//    public ResponseEntity<?> signInUser(AuthenticationRequest request) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(request.email(), request.password())
//        );
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
//
//        ResponseCookie jwtCookie = jwtProvider.generateJwtCookie(userDetails);
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
//                .body(new MessageResponse("You've been signed in!"));
//    }
//
//    @Transactional
//    public MessageResponse registerUser(RegistrationRequest request) {
//        if (userInfoRepository.findByEmail(request.email()).isPresent()) {
//            throw new GlobalServiceException(HttpStatus.CONFLICT,
//                    "This email is already used. Use another one and try again.");
//        }
//        //TODO
//        UserInfo userInfo = UserInfo.builder()
//                .email(request.email())
//                .username(request.username()) // Set the username
//                .password(passwordEncoder.encode(request.password()))
////                .firstName(request.firstName())
////                .lastName(request.lastName())
//                .balance(BigDecimal.valueOf(0))
//                .build();
//
//        userInfo.setActive(true);
//        userInfo.setRoles(Collections.singleton(roleRepository.findByName(ERole.ROLE_USER)
//                .orElseThrow(() -> new GlobalServiceException(HttpStatus.NOT_FOUND, "Role is not found."))));
//        userInfo.setProvider(AuthProvider.LOCAL);
////        userInfo.setActivationCode(UUID.randomUUID().toString());
//        userInfoRepository.save(userInfo);
//
//        return MessageResponse.builder().response("User was successfully registered!").build();
//    }
//
//    //TODO check the code below
//    @Transactional
//    public UserInfo registerOauth2User(String provider, OAuth2UserInfo oAuth2UserInfo) {
//        UserInfo user = new UserInfo();
//        user.setEmail(oAuth2UserInfo.getEmail());
//        user.setPassword("Test password");
//        user.setUsername("Test username");
//
//        user.setBalance(BigDecimal.valueOf(0));
//        user.setFirstName(oAuth2UserInfo.getFirstName());
//        user.setLastName(oAuth2UserInfo.getLastName());
//        user.setActive(true);
//        user.setRoles(Collections.singleton(roleRepository.findByName(ERole.ROLE_USER)
//                .orElseThrow(() -> new GlobalServiceException(HttpStatus.NOT_FOUND, "Role is not found."))));
//        user.setProvider(AuthProvider.valueOf(provider.toUpperCase()));
//        return userInfoRepository.save(user);
//    }
//
//    @Transactional
//    public UserInfo updateOauth2User(UserInfo user, String provider, OAuth2UserInfo oAuth2UserInfo) {
//        user.setFirstName(oAuth2UserInfo.getFirstName());
//        user.setLastName(oAuth2UserInfo.getLastName());
//        user.setProvider(AuthProvider.valueOf(provider.toUpperCase()));
//
//        return userInfoRepository.save(user);
//    }
//
//    public ResponseEntity<?> signOutUser() {
//        ResponseCookie jwtCookie = jwtProvider.getCleanJwtCookie();
//        return ResponseEntity.ok()
//                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
//                .body(new MessageResponse("You've been signed out!"));
//    }
//}
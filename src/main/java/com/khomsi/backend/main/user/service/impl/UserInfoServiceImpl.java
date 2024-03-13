package com.khomsi.backend.main.user.service.impl;

import com.khomsi.backend.main.game.model.entity.Game;
import com.khomsi.backend.main.handler.exception.GlobalServiceException;
import com.khomsi.backend.main.user.model.entity.UserGames;
import com.khomsi.backend.main.user.repository.UserInfoRepository;
import com.khomsi.backend.main.user.model.dto.FullUserInfoDTO;
import com.khomsi.backend.main.user.model.entity.UserInfo;
import com.khomsi.backend.main.user.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoRepository userRepository;

    @Override
    public FullUserInfoDTO getCurrentUser() {
        Jwt jwt = getJwt();
        if (jwt == null) {
            throw new GlobalServiceException(HttpStatus.UNAUTHORIZED, "User is not authenticated.");
        }
        UserInfo existingUser = getExistingUser(jwt.getSubject());
        if (existingUser == null) {
            throw new GlobalServiceException(HttpStatus.BAD_REQUEST,
                    "User is empty in external database to view full profile.");
        }
        return getUserInfo(existingUser, jwt);
    }

    //Get credential of auth user through keycloak
    @Override
    public Jwt getJwt() {
        return (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    private FullUserInfoDTO getUserInfo(UserInfo existingUser, Jwt jwt) {
        return FullUserInfoDTO.builder()
                .externalId(jwt.getSubject())
                .email(jwt.getClaimAsString("email"))
                .givenName(jwt.getClaimAsString("given_name"))
                .familyName(jwt.getClaimAsString("family_name"))
                .gender(jwt.getClaimAsString("gender"))
                .birthdate(jwt.getClaimAsString("birthdate"))
                .balance(existingUser.getBalance())
                // Add other user information based on JWT claims or user in db
                .build();
    }
    @Override
    public void checkPermissionToAction(String userId) {
        FullUserInfoDTO currentUser = getCurrentUser();
        if (!Objects.equals(userId, currentUser.externalId())) {
            throw new GlobalServiceException(HttpStatus.FORBIDDEN, "User with " + currentUser.externalId()
                    + "doesn't have authorities to perform this action.");
        }
    }

    @Override
    public UserInfo getExistingUser(String userInfo) {
        return userRepository.findUserInfoByExternalId(userInfo);
    }
    @Override
    public boolean checkIfGameIsOwnedByCurrentUser(Game game) {
        // Fetch user and info about his games
        UserInfo currentUser = getUserInfo();
        Set<Game> userGames = currentUser.getUserGames().stream()
                .map(UserGames::getGame)
                .collect(Collectors.toSet());

        // Check if this game is contacting for this user
        return userGames.contains(game);
    }
    @Override
    public UserInfo getUserInfo() {
        Jwt jwt = getJwt();
        if (jwt == null) {
            throw new GlobalServiceException(HttpStatus.UNAUTHORIZED, "User is not authenticated.");
        }
        return getExistingUser(jwt.getSubject());
    }
}

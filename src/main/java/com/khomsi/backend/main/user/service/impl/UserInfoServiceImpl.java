package com.khomsi.backend.main.user.service.impl;

import com.khomsi.backend.main.game.model.entity.Game;
import com.khomsi.backend.main.handler.exception.GlobalServiceException;
import com.khomsi.backend.main.user.model.dto.BalanceUserInfoDTO;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoRepository userRepository;

    @Override
    public FullUserInfoDTO getCurrentUser() {
        try {
            Jwt jwt = getJwt();
            UserInfo existingUser = getExistingUser(jwt.getSubject());
            if (existingUser == null) {
                throw new GlobalServiceException(HttpStatus.BAD_REQUEST,
                        "User is empty in external database to view full profile.");
            }
            return getUserInfo(existingUser, jwt);
        } catch (GlobalServiceException ignored) {
            return null;
        }
    }

    //Get credential of auth user through keycloak
    @Override
    public Jwt getJwt() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Jwt jwt) {
            return jwt;
        }
        throw new GlobalServiceException(HttpStatus.I_AM_A_TEAPOT, "Unsupported authentication method.");
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
    public BalanceUserInfoDTO getUserBalance() {
        return BalanceUserInfoDTO.builder().balance(getCurrentUser().balance()).build();
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
        FullUserInfoDTO currentUser = getCurrentUser();
        if (currentUser == null)
            return false;
        // Check if this game is contacting for this user
        return userRepository.gameExistsInUserGames(currentUser.externalId(), game.getId()) > 0;
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

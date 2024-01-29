package com.khomsi.backend.main.user.service;

import com.khomsi.backend.main.handler.exception.GlobalServiceException;
import com.khomsi.backend.main.user.UserInfoRepository;
import com.khomsi.backend.main.user.model.dto.FullUserInfoDTO;
import com.khomsi.backend.main.user.model.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserInfoServiceImpl implements UserInfoService{
    private final UserInfoRepository userRepository;

    @Override
    public FullUserInfoDTO getCurrentUser() {
        final OidcUserInfo userInfo = getOidcUser();
        if (userInfo == null) {
            throw new GlobalServiceException(HttpStatus.UNAUTHORIZED, "User is not authenticated.");
        }
        UserInfo existingUser = getExistingUser(userInfo);
        if (existingUser == null) {
            throw new GlobalServiceException(HttpStatus.BAD_REQUEST,
                    "User is empty in external database to view full profile.");
        }
        return getUserInfo(existingUser, userInfo);
    }

    //Get credential of auth user through keycloak
    private OidcUserInfo getOidcUser() {
        OidcUser principal = (OidcUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return principal.getUserInfo();
    }

    private FullUserInfoDTO getUserInfo(UserInfo existingUser, OidcUserInfo userInfo) {
        return FullUserInfoDTO.builder()
                .externalId(userInfo.getSubject())
                .email(userInfo.getEmail())
                .balance(existingUser.getBalance())
                .givenName(userInfo.getGivenName())
                .familyName(userInfo.getFamilyName())
                .gender(userInfo.getGender())
                .birthdate(userInfo.getBirthdate())
                .build();
    }

    protected UserInfo getExistingUser(OidcUserInfo userInfo) {
        return userRepository.findUserInfoByExternalId(userInfo.getSubject());
    }
}

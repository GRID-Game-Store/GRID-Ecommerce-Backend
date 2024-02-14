package com.khomsi.backend.main.user.service;

import com.khomsi.backend.main.user.model.dto.FullUserInfoDTO;
import com.khomsi.backend.main.user.model.entity.UserInfo;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.jwt.Jwt;

public interface UserInfoService {
    FullUserInfoDTO getCurrentUser();

    //Get credential of auth user through keycloak
    Jwt getJwt();

    void checkPermissionToAction(String userId);

    UserInfo getExistingUser(String userInfo);

    UserInfo getUserInfo();
}

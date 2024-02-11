package com.khomsi.backend.main.user.service;

import com.khomsi.backend.main.user.model.dto.FullUserInfoDTO;
import com.khomsi.backend.main.user.model.entity.UserInfo;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

public interface UserInfoService {
    FullUserInfoDTO getCurrentUser();

    //Get credential of auth user through keycloak
    OidcUserInfo getOidcUser();

    void checkPermissionToAction(String userId);

    UserInfo getExistingUser(OidcUserInfo userInfo);

    UserInfo getUserInfo();
}

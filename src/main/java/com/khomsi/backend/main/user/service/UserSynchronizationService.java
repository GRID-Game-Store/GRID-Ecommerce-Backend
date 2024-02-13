package com.khomsi.backend.main.user.service;

import com.khomsi.backend.main.user.UserInfoRepository;
import com.khomsi.backend.main.user.model.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class UserSynchronizationService {
//    private final UserInfoRepository userRepository;
//    private final UserInfoServiceImpl userInfoServiceImpl;
//
//    //TODO temporary problem, can't track when user in keycloak was deleted.
//    // Due to this, user in external db remains undeleted.
//    // Current solution: disable user in keycloak instead of deleting it
//    // Normal solution: track keycloak user db when any user was deleted and
//    // refresh external db with event listener plugin or User Federation
//    private void syncWithDatabase(final OidcUserInfo userInfo) {
//        UserInfo user = userInfoServiceImpl.getExistingUser(userInfo);
//        if (user == null) {
//            user = createUserInfoToDB(userInfo);
//        }
//        //Note: Deletes user in keycloak, but not in external db
//        //If user that was deleted tries to enter the login again with the email that was before
//        // Mysql will through exception due to problem with unique key for email (the primary key is sub, but
//        // email is unique)
//        user.setEmail(userInfo.getEmail());
//        userRepository.save(user);
//    }
//
//    @EventListener(AuthenticationSuccessEvent.class)
//    public void onAuthenticationSuccessEvent(final AuthenticationSuccessEvent event) {
//        final OidcUser oidcUser = ((OidcUser) event.getAuthentication().getPrincipal());
//        syncWithDatabase(oidcUser.getUserInfo());
//    }
//    private UserInfo createUserInfoToDB(OidcUserInfo userInfo) {
//        UserInfo user = new UserInfo();
//        user.setExternalId(userInfo.getSubject());
//        user.setBalance(BigDecimal.ZERO);
//        // add other fields in future
//        return user;
//    }
//}
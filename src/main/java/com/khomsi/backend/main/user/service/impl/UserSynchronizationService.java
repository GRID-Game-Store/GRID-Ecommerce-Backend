package com.khomsi.backend.main.user.service.impl;

import com.khomsi.backend.main.user.repository.UserInfoRepository;
import com.khomsi.backend.main.user.model.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSynchronizationService {
    private final UserInfoRepository userRepository;
    private final UserInfoServiceImpl userInfoServiceImpl;

    //TODO temporary problem, can't track when user in keycloak was deleted.
    // Due to this, user in external db remains undeleted.
    // Current solution: disable user in keycloak instead of deleting it
    // Normal solution: track keycloak user db when any user was deleted and
    // refresh external db with event listener plugin or User Federation
    private void syncWithDatabase(final Jwt jwt) {
        String userId = jwt.getSubject();
        UserInfo user = userInfoServiceImpl.getExistingUser(userId);
        //Create user
        if (user == null) {
            user = createUserInfoToDB(jwt);
        }
        user.setEmail(jwt.getClaimAsString("email"));
        // Save user information
        userRepository.save(user);
    }

    @EventListener(AuthenticationSuccessEvent.class)
    public void onAuthenticationSuccessEvent(final AuthenticationSuccessEvent event) {
        Jwt jwt = (Jwt) event.getAuthentication().getPrincipal();
        syncWithDatabase(jwt);
    }

    private UserInfo createUserInfoToDB(Jwt jwt) {
        UserInfo user = new UserInfo();
        user.setExternalId(jwt.getSubject());
        user.setUsername(jwt.getClaimAsString("preferred_username"));
        user.setBalance(BigDecimal.ZERO);
        // Set other user attributes based on JWT claims
        return user;
    }
}
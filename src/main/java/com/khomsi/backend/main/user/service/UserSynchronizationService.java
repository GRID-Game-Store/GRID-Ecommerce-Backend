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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSynchronizationService {
    private final UserInfoRepository userRepository;

    //TODO temporary problem, can't track when user in keycloak was deleted.
    // Due to this, user in external db remains undeleted.
    // Current solution: leave user in external db with this logic
    // Upcoming solution: track keycloak user db when any user was deleted and refresh external db.
    private void syncWithDatabase(final OidcUserInfo userInfo) {
        // Search user in db
        Optional<UserInfo> existingUser = userRepository.findUserInfoByExternalId(userInfo.getSubject());

        if (existingUser.isPresent()) {
            // If present, change id
            UserInfo user = existingUser.get();
            user.setExternalId(userInfo.getSubject());
            userRepository.save(user);
        } else {
            // If not present, create a new one
            UserInfo newUser = new UserInfo();
            newUser.setExternalId(userInfo.getSubject());
            newUser.setEmail(userInfo.getEmail());
            userRepository.save(newUser);
        }
    }

    @EventListener(AuthenticationSuccessEvent.class)
    public void onAuthenticationSuccessEvent(final AuthenticationSuccessEvent event) {
        final OidcUser oidcUser = ((OidcUser) event.getAuthentication().getPrincipal());
        syncWithDatabase(oidcUser.getUserInfo());
    }
}
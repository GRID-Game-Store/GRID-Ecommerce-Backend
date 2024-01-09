package com.khomsi.grid.main.security.oauth2;

import com.khomsi.grid.main.authentication.service.impl.AuthenticationServiceImpl;
import com.khomsi.grid.main.security.service.UserPrincipal;
import com.khomsi.grid.main.user.UserInfoRepository;
import com.khomsi.grid.main.user.model.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final AuthenticationServiceImpl authenticationService;
    private final UserInfoRepository userInfoRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String provider = userRequest.getClientRegistration().getRegistrationId();
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserFactory.getOAuth2UserInfo(provider, oAuth2User.getAttributes());
        UserInfo user = userInfoRepository.findByEmail(oAuth2UserInfo.getEmail())
                .orElseGet(() -> authenticationService.registerOauth2User(provider, oAuth2UserInfo));
        log.info("user={}", user);
        if (user != null) {
            user = authenticationService.updateOauth2User(user, provider, oAuth2UserInfo);
        }
        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

}
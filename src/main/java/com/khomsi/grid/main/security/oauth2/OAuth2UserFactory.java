package com.khomsi.grid.main.security.oauth2;

import com.khomsi.grid.main.authentication.model.enums.AuthProvider;
import lombok.SneakyThrows;

import javax.naming.AuthenticationException;
import java.util.Map;

public class OAuth2UserFactory {

    @SneakyThrows
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthProvider.GOOGLE.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        }
//        else if (registrationId.equalsIgnoreCase(AuthProvider.FACEBOOK.toString())) {
//            return new FacebookOAuth2UserInfo(attributes);
//        }
        else {
            throw new AuthenticationException();
        }
    }
}
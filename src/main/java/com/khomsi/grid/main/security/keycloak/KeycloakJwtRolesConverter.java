package com.khomsi.grid.main.security.keycloak;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class KeycloakJwtRolesConverter {
    /**
     * Custom mapper to use OIDC claims as Spring Security authorities.
     */
    @Bean
    public GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return authorities -> {
            final Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            authorities.forEach(authority -> {
                if (authority instanceof OidcUserAuthority oidcAuth) {
                    mappedAuthorities.addAll(mapAuthorities(oidcAuth.getIdToken().getClaims()));
                } else if (authority instanceof OAuth2UserAuthority oauth2Auth) {
                    mappedAuthorities.addAll(mapAuthorities(oauth2Auth.getAttributes()));
                }
            });
            return mappedAuthorities;
        };
    }

    /**
     * Read claims from attribute realm_access.roles as SimpleGrantedAuthority.
     */
    private List<SimpleGrantedAuthority> mapAuthorities(final Map<String, Object> attributes) {
        final Map<String, Object> realmAccess = Optional.ofNullable(attributes.get("realm_access"))
                .map(map -> (Map<String, Object>) map)
                .orElse(Collections.emptyMap());
        final Collection<String> roles = Optional.ofNullable(realmAccess.get("roles"))
                .map(collection -> (Collection<String>) collection)
                .orElse(Collections.emptyList());
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}

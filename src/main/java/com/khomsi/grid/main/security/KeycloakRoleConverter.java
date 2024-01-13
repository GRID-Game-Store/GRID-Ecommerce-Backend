package com.khomsi.grid.main.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        Map<String, Object> realAccess = (Map<String, Object>) source.getClaims().get("realm_access");

        if (realAccess == null || realAccess.isEmpty()) {
            return new ArrayList<>();
        }
        return ((List<String>) realAccess.get("roles"))
                //take each role in list and add prefix since spring security works with prefix
                .stream().map(roleName -> "ROLE_" + roleName)
                //convert that objects to SimpleGrantedAuthority
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}

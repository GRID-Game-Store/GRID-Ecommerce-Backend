package com.khomsi.grid.main.security.service;

import com.khomsi.grid.main.user.UserInfoService;
import com.khomsi.grid.main.user.model.entity.UserInfo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserInfoService userInfoService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserInfo user = userInfoService.getUserInfo(email);
        //TODO add activation for account
   /*     if (user.getActivationCode() != null) {
            throw new LockedException("Email not activated");
        }*/
        return UserPrincipal.create(user);
    }
}
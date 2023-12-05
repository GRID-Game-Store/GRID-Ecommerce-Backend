package com.khomsi.grid.main.security.service;

import com.khomsi.grid.main.user.UserInfoRepository;
import com.khomsi.grid.main.user.model.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserInfoRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserInfo user = userRepository.findByEmail(email)
                //TODO
                .orElseThrow(() -> new UsernameNotFoundException(("User Not Found with username: " + email)));
        if (user.getActivationCode() != null) {
            throw new LockedException("Email not activated");
        }
        return UserDetailsImpl.create(user);
    }
}
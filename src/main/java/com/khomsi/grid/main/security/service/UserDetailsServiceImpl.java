package com.khomsi.grid.main.security.service;

import com.khomsi.grid.main.handler.exception.GlobalServiceException;
import com.khomsi.grid.main.user.UserInfoRepository;
import com.khomsi.grid.main.user.model.entity.UserInfo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserInfoRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserInfo user = userRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalServiceException(HttpStatus.NOT_FOUND,
                        "User is not found with email: " + email));
        //TODO add activation for account
   /*     if (user.getActivationCode() != null) {
            throw new LockedException("Email not activated");
        }*/
        return UserDetailsImpl.create(user);
    }
}
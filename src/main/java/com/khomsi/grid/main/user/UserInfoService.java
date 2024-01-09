package com.khomsi.grid.main.user;

import com.khomsi.grid.main.handler.exception.GlobalServiceException;
import com.khomsi.grid.main.user.model.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final UserInfoRepository userInfoRepository;

    public UserInfo getUserInfo(String email) {
        return userInfoRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalServiceException(HttpStatus.NOT_FOUND,
                        "User is not found with email: " + email));
    }
}

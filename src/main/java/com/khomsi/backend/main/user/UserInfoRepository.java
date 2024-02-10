package com.khomsi.backend.main.user;


import com.khomsi.backend.main.user.model.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    UserInfo findUserInfoByExternalId(String externalId);
//    UserInfo findUserInfoByEmail(String email);
//    Optional<UserInfo> findUserInfoByEmail(String email);
}

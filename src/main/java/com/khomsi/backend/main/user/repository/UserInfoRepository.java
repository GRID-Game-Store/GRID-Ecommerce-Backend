package com.khomsi.backend.main.user.repository;


import com.khomsi.backend.main.user.model.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    UserInfo findUserInfoByExternalId(String externalId);
}

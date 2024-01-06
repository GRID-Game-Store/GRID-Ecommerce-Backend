package com.khomsi.grid.main.user;

import com.khomsi.grid.main.user.model.entity.RefreshToken;
import com.khomsi.grid.main.user.model.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUser_Id(Long id);

    @Modifying
    Long deleteByUser(UserInfo user);
}

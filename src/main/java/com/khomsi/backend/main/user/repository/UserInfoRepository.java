package com.khomsi.backend.main.user.repository;


import com.khomsi.backend.main.user.model.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    UserInfo findUserInfoByExternalId(String externalId);

    @Query(nativeQuery = true, value = """
            SELECT COUNT(*) > 0 FROM user_has_games
            WHERE users_id = :userId AND games_id = :gameId
            """)
    Long gameExistsInUserGames(String userId, Long gameId);
    @Query(nativeQuery = true, value = """
            SELECT COUNT(DISTINCT users_id) FROM user_has_games
            """)
    Long countUsersWithAtLeastOneGame();
}

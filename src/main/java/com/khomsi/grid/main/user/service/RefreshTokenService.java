package com.khomsi.grid.main.user.service;

import com.khomsi.grid.main.security.exception.TokenRefreshException;
import com.khomsi.grid.main.security.jwt.JwtProperties;
import com.khomsi.grid.main.user.RefreshTokenRepository;
import com.khomsi.grid.main.user.UserInfoRepository;
import com.khomsi.grid.main.user.UserNotFoundException;
import com.khomsi.grid.main.user.model.entity.RefreshToken;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefreshTokenService {
    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserInfoRepository userInfoRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userId) {
        // Search for token
        Optional<RefreshToken> existingToken = refreshTokenRepository.findByUser_Id(userId);

        // Delete token if it exists.
        existingToken.ifPresent(refreshTokenRepository::delete);

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userInfoRepository.findById(userId).orElseThrow(UserNotFoundException::new));
        refreshToken.setExpiryDate(Instant.now().plusMillis(jwtProperties.jwtExpirationMs()));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            //TODO change the message
            throw new TokenRefreshException(token.getToken(),
                    "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Transactional
    public Long deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(userInfoRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new));
    }
}

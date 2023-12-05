package com.khomsi.grid.main.authentication.model.reponse;

import com.khomsi.grid.main.user.model.response.UserResponse;
import lombok.Builder;

@Builder
public record AuthenticationLoginResponse(
        UserResponse user,
        String token
) {
}

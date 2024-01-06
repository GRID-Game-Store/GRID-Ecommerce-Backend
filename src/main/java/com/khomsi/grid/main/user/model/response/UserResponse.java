package com.khomsi.grid.main.user.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record UserResponse(
        //Main
        Long id,
        String email,
        String firstName,
//        Set<Role> roles,
        String provider,
        //Additional
        String lastName,
        String city,
        String address,
        String phoneNumber,
        String postIndex
) {
}

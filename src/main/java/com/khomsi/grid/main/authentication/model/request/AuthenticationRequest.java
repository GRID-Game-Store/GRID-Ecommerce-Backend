package com.khomsi.grid.main.authentication.model.request;

import lombok.Builder;

@Builder
public record AuthenticationRequest(String email, String password) {

}
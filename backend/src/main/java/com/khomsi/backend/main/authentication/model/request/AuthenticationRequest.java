package com.khomsi.backend.main.authentication.model.request;

import lombok.Builder;

@Builder
public record AuthenticationRequest(String email, String password) {

}
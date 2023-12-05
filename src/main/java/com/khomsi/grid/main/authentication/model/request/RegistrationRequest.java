package com.khomsi.grid.main.authentication.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistrationRequest(
        @NotBlank
        String firstName,

        @NotBlank
        String lastName,
        @NotBlank
        String userName,
        @Size(min = 6, max = 16)
        String password,
        @Email
        @NotBlank
        String email
) {
}

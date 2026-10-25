package com.pablo.library_api.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "O nome do usuário é obrigatório")
        String username,

        @NotBlank(message = "A senha é obrigatória")
        String password
) {
}

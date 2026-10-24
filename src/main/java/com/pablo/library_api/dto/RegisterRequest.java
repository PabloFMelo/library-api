package com.pablo.library_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank(message = "O username é obrigatório")
        String username,

        @Size( min = 6)
        @NotBlank(message = "Digite uma senha")
        String password
        ) {
}

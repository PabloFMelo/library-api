package com.pablo.library_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MemberRequest(
       @NotBlank(message = "O nome é obrigatório")
        String name,

        @NotBlank(message = "O E-mail é obrigatório")
        @Email(message = "E-mail em formato inválido")
        String email
        ) {
}

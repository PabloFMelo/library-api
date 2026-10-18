package com.pablo.library_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record BookRequest(
        @NotBlank(message = "O título é obrigatório")
        String title,

        @NotBlank(message = "O autor é obrigatório")
        String author,

        @NotBlank(message = "Não pode ser vazio")
        String isbn,

        @Min(value = 1, message = "Deve ser no mínimo 1")
        int totalCopies,

        @Min(value = 0, message = "Deve ser no mínimo 0")
        int availableCopies
        ) {

}

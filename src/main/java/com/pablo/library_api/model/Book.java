package com.pablo.library_api.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class Book {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Não pode ser vazio")
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "Não pode ser vazio")
    private String author;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Não pode ser vazio")
    private String isbn;

    @Column(nullable = false)
    @Min(value = 1, message = "Deve ser no mínimo 1")
    private int totalCopies;

    @Column(nullable = false)
    @Min(value = 0, message = "Deve ser no mínimo 1")
    private int availableCopies;
}


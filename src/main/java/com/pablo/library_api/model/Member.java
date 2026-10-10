package com.pablo.library_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor

@Table(name = "members")
@Entity

public class Member {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    @NotBlank(message = "Não pode ser vazio")
    private String name;

    @Column (nullable = false, unique = true)
    @NotBlank(message = "Não pode ser vazio")
    @Email
    private String email;

    @CreationTimestamp
    private LocalDateTime registrationDate;


}

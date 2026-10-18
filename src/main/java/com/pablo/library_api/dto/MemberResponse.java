package com.pablo.library_api.dto;

import java.time.LocalDateTime;

public record MemberResponse(
        Long id,

        String name,

        String email,

        LocalDateTime registrationDate
) {
}

package com.pablo.library_api.dto;

import com.pablo.library_api.model.LoanStatus;
import java.time.LocalDateTime;

public record LoanResponse(
        Long id,
        Long bookId,
        String bookTitle,
        Long memberId,
        String memberName,
        LocalDateTime loanDate,
        LocalDateTime dueDate,
        LocalDateTime returnDate,
        LoanStatus status
) {
}

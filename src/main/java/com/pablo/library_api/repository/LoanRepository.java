package com.pablo.library_api.repository;
import com.pablo.library_api.model.Loan;
import com.pablo.library_api.model.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByStatusAndDueDateBefore(LoanStatus status, LocalDateTime limite);
}

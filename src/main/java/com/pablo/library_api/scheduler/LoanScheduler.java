package com.pablo.library_api.scheduler;

import com.pablo.library_api.service.LoanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LoanScheduler {
    private final LoanService loanService;

    public LoanScheduler(LoanService loanService) {
        this.loanService = loanService;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void atualizarEmprestimosAtrasados() {
        int total = loanService.marcarEmprestimosAtrasados();
        log.info("Empréstimos marcados como atrasados: {}", total);
    }
}

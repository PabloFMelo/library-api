package com.pablo.library_api.controller;

import com.pablo.library_api.model.Loan;
import com.pablo.library_api.service.LoanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;

     public LoanController(LoanService loanService){
         this.loanService = loanService;
     }

     @GetMapping
     public List<Loan> findAll(){
         return loanService.findAll();
     }

     @PostMapping
     public Loan criarEmprestimo(@RequestParam Long bookId, @RequestParam Long memberId){
        return loanService.criarEmprestimo(bookId, memberId);
     }
     @PutMapping("/{id}")
     public Loan devolverEmprestimo(@PathVariable Long id){
         return loanService.devolverEmprestimo(id);
     }

}

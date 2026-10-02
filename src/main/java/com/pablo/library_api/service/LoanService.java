package com.pablo.library_api.service;

import com.pablo.library_api.model.Book;
import com.pablo.library_api.model.Loan;
import com.pablo.library_api.model.LoanStatus;
import com.pablo.library_api.model.Member;
import com.pablo.library_api.repository.BookRepository;
import com.pablo.library_api.repository.LoanRepository;
import com.pablo.library_api.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public LoanService(LoanRepository loanRepository, BookRepository bookRepository, MemberRepository memberRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    public Loan criarEmprestimo(Long bookId, Long memberId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Membro não encontrado"));

        if (book.getAvailableCopies() <= 0){
            throw new RuntimeException("Não há exemplares para empréstimo");
        }

        book.setAvailableCopies(book.getAvailableCopies() -1);

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setMember(member);
        loan.setDueDate(LocalDateTime.now().plusDays(7));
        loan.setStatus(LoanStatus.ATIVO);
        return loanRepository.save(loan);

    }
}

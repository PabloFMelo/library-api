package com.pablo.library_api.service;

import com.pablo.library_api.exception.RecursoNaoEncontradoException;
import com.pablo.library_api.exception.RegraDeNegocioException;
import com.pablo.library_api.model.Book;
import com.pablo.library_api.model.Loan;
import com.pablo.library_api.model.LoanStatus;
import com.pablo.library_api.model.Member;
import com.pablo.library_api.repository.BookRepository;
import com.pablo.library_api.repository.LoanRepository;
import com.pablo.library_api.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {
    @Mock
    private LoanRepository loanRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private LoanService loanService;

    @Test
    void deveLancarExcecaoQuandoLivroNaoExiste() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class,
                () -> loanService.criarEmprestimo(99L, 1L));
    }
    @Test
    void deveLancarExcecaoQuandoNaoHaExemplaresDisponiveis() {
        // Arrange: prepara o cenário
        Book book = new Book();
        book.setAvailableCopies(0);
        Member member = new Member();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        // Act + Assert: executa e confere a exceção
        assertThrows(RegraDeNegocioException.class,
                () -> loanService.criarEmprestimo(1L, 1L));
    }

    @Test
    void deveCriarEmprestimoQuandoHaExemplaresDisponiveis() {
        // Arrange
        Book book = new Book();
        book.setAvailableCopies(2);
        Member member = new Member();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(loanRepository.save(any(Loan.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Loan resultado = loanService.criarEmprestimo(1L, 1L);

        // Assert
        assertEquals(LoanStatus.ATIVO, resultado.getStatus());
        assertEquals(1, book.getAvailableCopies());
        assertSame(book, resultado.getBook());
        assertSame(member, resultado.getMember());
        assertNotNull(resultado.getDueDate());
    }



}
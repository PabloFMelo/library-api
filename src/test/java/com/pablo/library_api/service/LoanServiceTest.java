package com.pablo.library_api.service;

import com.pablo.library_api.dto.LoanResponse;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.List;
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
        book.setId(10L);
        book.setAvailableCopies(2);
        Member member = new Member();
        member.setId(20L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(loanRepository.save(any(Loan.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        LoanResponse resultado = loanService.criarEmprestimo(1L, 1L);

        // Assert
        assertEquals(LoanStatus.ATIVO, resultado.status());
        assertEquals(1, book.getAvailableCopies());
        assertEquals(10L, resultado.bookId());
        assertEquals(20L, resultado.memberId());
        assertNotNull(resultado.dueDate());
    }

    @Test
    void deveDevolverEmprestimoAtivoERestaurarEstoque() {
        // Arrange
        Book book = new Book();
        book.setId(10L);
        book.setAvailableCopies(1);
        Member member = new Member();
        member.setId(20L);

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setMember(member);
        loan.setStatus(LoanStatus.ATIVO);

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any(Loan.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        LoanResponse resultado = loanService.devolverEmprestimo(1L);

        // Assert
        assertEquals(LoanStatus.DEVOLVIDO, resultado.status());
        assertEquals(2, book.getAvailableCopies());
        assertNotNull(resultado.returnDate());
    }

    @Test
    void naoDeveDevolverEmprestimoJaDevolvido() {
        // Arrange
        Book book = new Book();
        book.setAvailableCopies(2);

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setStatus(LoanStatus.DEVOLVIDO);

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));

        // Act + Assert
        assertThrows(RegraDeNegocioException.class, () -> loanService.devolverEmprestimo(1L));

        // O estoque não pode ter mudado e nada pode ter sido salvo
        assertEquals(2, book.getAvailableCopies());
        verify(loanRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoEmprestimoNaoExiste() {
        when(loanRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class,
                () -> loanService.devolverEmprestimo(1L));

        verify(loanRepository, never()).save(any());
    }

    @Test
    void deveMarcarEmprestimosVencidosComoAtrasados() {
        // Arrange
        Loan primeiro = new Loan();
        primeiro.setStatus(LoanStatus.ATIVO);
        Loan segundo = new Loan();
        segundo.setStatus(LoanStatus.ATIVO);
        List<Loan> vencidos = List.of(primeiro, segundo);

        when(loanRepository.findByStatusAndDueDateBefore(eq(LoanStatus.ATIVO), any(LocalDateTime.class)))
                .thenReturn(vencidos);

        // Act
        int total = loanService.marcarEmprestimosAtrasados();

        // Assert
        assertEquals(2L, total);
        assertEquals(LoanStatus.ATRASADO, primeiro.getStatus());
        assertEquals(LoanStatus.ATRASADO, segundo.getStatus());
        verify(loanRepository).saveAll(vencidos);
    }
    @Test
    void naoDeveMarcarNadaQuandoNaoHaEmprestimosVencidos() {
        // Arrange: o repositório não encontra nenhum empréstimo vencido
        List<Loan> vencidos = List.of();

        when(loanRepository.findByStatusAndDueDateBefore(eq(LoanStatus.ATIVO), any(LocalDateTime.class)))
                .thenReturn(vencidos);

        // Act
        int total = loanService.marcarEmprestimosAtrasados();

        // Assert
        assertEquals(0, total);
        verify(loanRepository).saveAll(vencidos);
    }
}
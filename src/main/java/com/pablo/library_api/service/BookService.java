package com.pablo.library_api.service;

import com.pablo.library_api.dto.BookRequest;
import com.pablo.library_api.dto.BookResponse;
import com.pablo.library_api.exception.RecursoNaoEncontradoException;
import com.pablo.library_api.model.Book;
import com.pablo.library_api.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookResponse save(BookRequest request) {
        Book book = new Book();
        book.setTitle(request.title().trim());
        book.setAuthor(request.author().trim());
        book.setIsbn(request.isbn().trim());
        book.setTotalCopies(request.totalCopies());
        book.setAvailableCopies(request.availableCopies());

        Book saved = bookRepository.save(book);
        return toResponse(saved);
    }
    private BookResponse toResponse(Book book){
        return new BookResponse(book.getId(), book.getTitle(),book.getAuthor(),
                book.getIsbn(),book.getTotalCopies(), book.getAvailableCopies());
    }

    public List<BookResponse> findAll(){
        return bookRepository.findAll().stream().map(this::toResponse).toList();
    }
    public BookResponse findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Livro não encontrado"));
        return toResponse(book);
    }
    public void delete(Long id){
        bookRepository.deleteById(id);
    }

}

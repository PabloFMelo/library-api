package com.pablo.library_api.service;

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
    public Book save(Book book) {
       return bookRepository.save(book);
    }
    public List<Book> findAll(){
        return bookRepository.findAll();
    }
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Livro não encontrado"));
    }
    public void delete(Long id){
        bookRepository.deleteById(id);
    }

}

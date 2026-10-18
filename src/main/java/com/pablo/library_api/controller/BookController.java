package com.pablo.library_api.controller;

import com.pablo.library_api.dto.BookRequest;
import com.pablo.library_api.dto.BookResponse;
import com.pablo.library_api.service.BookService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")

public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookResponse> findAll(){
        return bookService.findAll();
    }

    @PostMapping
    public BookResponse save(@Valid @RequestBody BookRequest request){
        return bookService.save(request);
    }

    @GetMapping("/{id}")
    public BookResponse findById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        bookService.delete(id);
    }

}

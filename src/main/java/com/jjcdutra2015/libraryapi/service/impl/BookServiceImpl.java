package com.jjcdutra2015.libraryapi.service.impl;

import com.jjcdutra2015.libraryapi.model.entity.Book;
import com.jjcdutra2015.libraryapi.model.entity.repository.BookRepository;
import com.jjcdutra2015.libraryapi.service.BookService;

public class BookServiceImpl implements BookService {

    private BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book save(Book book) {
        return repository.save(book);
    }
}

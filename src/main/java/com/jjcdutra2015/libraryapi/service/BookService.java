package com.jjcdutra2015.libraryapi.service;

import com.jjcdutra2015.libraryapi.model.entity.Book;

import java.util.Optional;

public interface BookService {
    Book save(Book book);

    Optional<Book> getById(Long id);

    void delete(Book book);
}
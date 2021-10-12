package com.jjcdutra2015.libraryapi.model.entity.repository;

import com.jjcdutra2015.libraryapi.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByIsbn(String isbn);
}

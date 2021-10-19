package com.jjcdutra2015.libraryapi.model.repository;

import com.jjcdutra2015.libraryapi.model.entity.Book;
import com.jjcdutra2015.libraryapi.model.entity.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    BookRepository repository;

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir um livro com o isbn informado")
    public void returnTrueWhenIsbnExists() {
        //cenario
        String isbn = "123";
        Book book = Book.builder().title("aventuras").author("Fulano").isbn(isbn).build();
        entityManager.persist(book);

        //execucao
        boolean existsByIsbn = repository.existsByIsbn(isbn);

        //verificacao
        assertThat(existsByIsbn).isTrue();
    }

    @Test
    @DisplayName("Deve retornar falso quando não existir um livro com o isbn informado")
    public void returnFalseWhenIsbnNotExists() {
        //cenario
        String isbn = "123";

        //execucao
        boolean existsByIsbn = repository.existsByIsbn(isbn);

        //verificacao
        assertThat(existsByIsbn).isFalse();
    }

    @Test
    @DisplayName("Deve obter um livro por id")
    public void findByIdTest() {
        //cenario
        Book book = Book.builder().title("aventuras").author("Fulano").isbn("123").build();
        entityManager.persist(book);

        //execucao
        Optional<Book> foundBook = repository.findById(book.getId());

        //verificacao
        assertThat(foundBook.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Deve salvar um livro")
    public void saveBookTest() {
        Book book = Book.builder().title("aventuras").author("Fulano").isbn("123").build();

        repository.save(book);

        assertThat(book.getId()).isNotNull();
    }

    @Test
    @DisplayName("Deve deletar um livro")
    public void deleteBookTest() {
        Book book = Book.builder().title("aventuras").author("Fulano").isbn("123").build();
        entityManager.persist(book);
        Book foundBook = entityManager.find(Book.class, book.getId());

        repository.delete(foundBook);

        assertThat(entityManager.find(Book.class, book.getId())).isNull();
    }
}

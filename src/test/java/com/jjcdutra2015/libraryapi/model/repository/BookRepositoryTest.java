package com.jjcdutra2015.libraryapi.model.repository;

import com.jjcdutra2015.libraryapi.model.entity.repository.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    BookRepository repository;

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir um isbn")
    public void returnTrueWhenExistsIsbn() {
        //cenario
        String isbn = "123";

        //execucao
        boolean existsByIsbn = repository.existsByIsbn(isbn);

        //verificacao
        assertThat(existsByIsbn).isTrue();
    }
}

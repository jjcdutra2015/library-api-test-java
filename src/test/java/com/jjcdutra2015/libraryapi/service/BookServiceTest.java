package com.jjcdutra2015.libraryapi.service;

import com.jjcdutra2015.libraryapi.model.entity.Book;
import com.jjcdutra2015.libraryapi.model.entity.repository.BookRepository;
import com.jjcdutra2015.libraryapi.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {

    BookService service;

    @MockBean
    BookRepository repository;

    @BeforeEach
    public void setup() {
        this.service = new BookServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve salvar um livro")
    public void saveBookTes() {
        //cenario
        Book book = Book.builder().title("As aventuras").author("Fulano").isbn("123").build();

        Mockito.when(repository.save(book)).thenReturn(
                Book.builder().id(1l ).title("As aventuras").author("Fulano").isbn("123").build()
        );

        //execucao
        Book savedBook = service.save(book);

        //verificacao
        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getTitle()).isEqualTo("As aventuras");
        assertThat(savedBook.getAuthor()).isEqualTo("Fulano");
        assertThat(savedBook.getIsbn()).isEqualTo("123");
    }
}

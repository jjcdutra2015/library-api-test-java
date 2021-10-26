package com.jjcdutra2015.libraryapi.service;

import com.jjcdutra2015.libraryapi.model.entity.Book;
import com.jjcdutra2015.libraryapi.model.entity.Loan;
import com.jjcdutra2015.libraryapi.model.entity.repository.BookRepository;
import com.jjcdutra2015.libraryapi.model.entity.repository.LoanRepository;
import com.jjcdutra2015.libraryapi.service.impl.BookServiceImpl;
import com.jjcdutra2015.libraryapi.service.impl.LoanServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class LoanServiceTest {

    LoanService service;

    @MockBean
    LoanRepository repository;

    @BeforeEach
    public void setup() {
        this.service = new LoanServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve salvar um emprestimo")
    public void saveLoanTest() {
        Book book = Book.builder().id(1l).isbn("123").build();
        Loan loan = Loan.builder().book(book).customer("Fulano").loanDate(LocalDate.now()).build();

        Loan loanSaved = Loan.builder().id(1l).book(book).customer("Fulano").loanDate(LocalDate.now()).build();

        Mockito.when(repository.save(loan)).thenReturn(loanSaved);

        loanSaved = service.save(loan);

        assertThat(loanSaved.getId()).isNotNull();
        assertThat(loanSaved.getBook()).isEqualTo(loan.getBook());
        assertThat(loanSaved.getCustomer()).isEqualTo(loan.getCustomer());
        assertThat(loanSaved.getLoanDate()).isEqualTo(loan.getLoanDate());
    }
}

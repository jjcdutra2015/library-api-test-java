package com.jjcdutra2015.libraryapi.service;

import com.jjcdutra2015.libraryapi.exception.BusinessException;
import com.jjcdutra2015.libraryapi.model.entity.Book;
import com.jjcdutra2015.libraryapi.model.entity.Loan;
import com.jjcdutra2015.libraryapi.model.entity.repository.LoanRepository;
import com.jjcdutra2015.libraryapi.service.impl.LoanServiceImpl;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

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

        Mockito.when(repository.existsByBookAndNotReturned(book)).thenReturn(false);
        Mockito.when(repository.save(loan)).thenReturn(loanSaved);

        loanSaved = service.save(loan);

        assertThat(loanSaved.getId()).isNotNull();
        assertThat(loanSaved.getBook()).isEqualTo(loan.getBook());
        assertThat(loanSaved.getCustomer()).isEqualTo(loan.getCustomer());
        assertThat(loanSaved.getLoanDate()).isEqualTo(loan.getLoanDate());
    }

    @Test
    @DisplayName("Deve lançar erro ao emprestar um livro já emprestado")
    public void loanedBookSaveTest() {
        Book book = Book.builder().id(1l).isbn("123").build();
        Loan loan = createLoan();

        Mockito.when(repository.existsByBookAndNotReturned(book)).thenReturn(true);

        Throwable exception = catchThrowable(() -> service.save(loan));

        Mockito.verify(repository, Mockito.never()).save(loan);
        assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessage("Book already loaned");
    }

    @Test
    @DisplayName("Deve obter detalhes do emprestimo pelo id")
    public void findByIdTest() {
        Long id = 1l;
        Loan loan = createLoan();
        loan.setId(id);

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(loan));

        Optional<Loan> result = service.getById(id);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getId()).isEqualTo(id);
        assertThat(result.get().getBook()).isEqualTo(loan.getBook());
        assertThat(result.get().getCustomer()).isEqualTo(loan.getCustomer());
        assertThat(result.get().getLoanDate()).isEqualTo(loan.getLoanDate());
    }

    private Loan createLoan() {
        Book book = Book.builder().id(1l).isbn("123").build();
        return Loan.builder().book(book).customer("Fulano").loanDate(LocalDate.now()).build();
    }
}

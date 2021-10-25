package com.jjcdutra2015.libraryapi.api.resource;

import com.jjcdutra2015.libraryapi.api.dto.LoandDTO;
import com.jjcdutra2015.libraryapi.model.entity.Book;
import com.jjcdutra2015.libraryapi.model.entity.Loan;
import com.jjcdutra2015.libraryapi.service.BookService;
import com.jjcdutra2015.libraryapi.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService service;
    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody LoandDTO dto) {
        Book book = bookService.getBookByIsbn(dto.getIsbn())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book not found to passed isbn")
                );
        Loan entity = Loan.builder().book(book).loanDate(LocalDate.now()).customer(dto.getCustomer()).build();

        entity = service.save(entity);
        return entity.getId();
    }
}

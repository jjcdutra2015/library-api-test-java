package com.jjcdutra2015.libraryapi.api.resource;

import com.jjcdutra2015.libraryapi.api.dto.BookDTO;
import com.jjcdutra2015.libraryapi.api.dto.LoanFilterDTO;
import com.jjcdutra2015.libraryapi.api.dto.LoandDTO;
import com.jjcdutra2015.libraryapi.api.dto.ReturnedLoanDTO;
import com.jjcdutra2015.libraryapi.model.entity.Book;
import com.jjcdutra2015.libraryapi.model.entity.Loan;
import com.jjcdutra2015.libraryapi.service.BookService;
import com.jjcdutra2015.libraryapi.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService service;
    private final BookService bookService;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody LoandDTO dto) {
        Book book = bookService.getBookByIsbn(dto.getIsbn())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book not found for passed isbn")
                );
        Loan entity = Loan.builder().book(book).loanDate(LocalDate.now()).customer(dto.getCustomer()).build();

        entity = service.save(entity);
        return entity.getId();
    }

    @PatchMapping("{id}")
    public void returnBook(@PathVariable Long id, @RequestBody ReturnedLoanDTO dto) {
        Loan loan = service.getById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        loan.setReturned(dto.getReturned());

        service.update(loan);
    }

    @GetMapping
    public Page<LoandDTO> find(LoanFilterDTO dto, Pageable pageable) {
        Page<Loan> result = service.find(dto, pageable);
        List<LoandDTO> loans = result.getContent().stream()
                .map(entity -> {
                            Book book = entity.getBook();
                            BookDTO bookDTO = modelMapper.map(book, BookDTO.class);
                            LoandDTO loandDTO = modelMapper.map(entity, LoandDTO.class);
                            loandDTO.setBook(bookDTO);
                            return loandDTO;
                        }
                ).collect(Collectors.toList());

        return new PageImpl<>(loans, pageable, result.getTotalElements());
    }
}

package com.jjcdutra2015.libraryapi.service;

import com.jjcdutra2015.libraryapi.api.dto.LoanFilterDTO;
import com.jjcdutra2015.libraryapi.model.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface LoanService {
    Loan save(Loan loan);

    Optional<Loan> getById(Long id);

    Loan update(Loan loan);

    Page<Loan> find(LoanFilterDTO filter, Pageable pageable);
}

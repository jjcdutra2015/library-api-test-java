package com.jjcdutra2015.libraryapi.service.impl;

import com.jjcdutra2015.libraryapi.exception.BusinessException;
import com.jjcdutra2015.libraryapi.model.entity.Loan;
import com.jjcdutra2015.libraryapi.model.entity.repository.LoanRepository;
import com.jjcdutra2015.libraryapi.service.LoanService;
import org.springframework.stereotype.Service;

@Service
public class LoanServiceImpl implements LoanService {

    private LoanRepository repository;

    public LoanServiceImpl(LoanRepository repository) {
        this.repository = repository;
    }

    @Override
    public Loan save(Loan loan) {
        if (repository.existsByBookAndNotReturned(loan.getBook())) {
            throw new BusinessException("Book already loaned");
        }
        return repository.save(loan);
    }
}

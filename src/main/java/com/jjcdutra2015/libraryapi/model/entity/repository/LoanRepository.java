package com.jjcdutra2015.libraryapi.model.entity.repository;

import com.jjcdutra2015.libraryapi.model.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}

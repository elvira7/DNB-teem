package com.bop.backend.service;

import com.bop.backend.embedded.UserPotId;
import com.bop.backend.model.Loan;
import com.bop.backend.model.Pot;

import java.util.List;

/**
 * Created by Robert on 08.03.2017.
 */
public interface LoanService {
    Loan save(Loan loan);
    List<Loan> getLoansByUserId(int userId);
    void delete(Integer id);
    Loan findLoanById(Integer id);
}

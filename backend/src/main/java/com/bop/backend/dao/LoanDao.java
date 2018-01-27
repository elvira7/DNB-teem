package com.bop.backend.dao;

import com.bop.backend.embedded.UserPotId;
import com.bop.backend.model.Loan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Robert on 08.03.2017.
 */
@Repository
public interface LoanDao extends CrudRepository<Loan, Long> {
    Loan save(Loan loan);
    Iterable<Loan> findAll();
    void deleteByLoanId(Integer id);
    Loan findByLoanId(Integer id);
}

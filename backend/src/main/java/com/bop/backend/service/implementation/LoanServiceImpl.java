package com.bop.backend.service.implementation;

import com.bop.backend.dao.LoanDao;
import com.bop.backend.model.Loan;
import com.bop.backend.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robert on 08.03.2017.
 */
@Service
//@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanDao loanDao;

    @Override
    public Loan save(Loan loan) {
            return loanDao.save(loan);

    }

    @Override
    public List<Loan> getLoansByUserId(int userId)
    {
        List<Loan> loanList = new ArrayList<>();
        Iterable<Loan> loans = findAll();

        for(Loan loan: loans)
        {
            if(loan.getUser().getTelephone() == userId)
            {
                loanList.add(loan);
            }
        }
        return loanList;
    }
    @Override
    public void delete(Integer id)
    {
        loanDao.deleteByLoanId(id);
    }
    @Override
    public Loan findLoanById(Integer id)
    {
        return loanDao.findByLoanId(id);
    }

    public Iterable<Loan> findAll()
    {
        return loanDao.findAll();
    }


}
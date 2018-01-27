package com.bop.backend.controller;

import com.bop.backend.dao.LoanDao;
import com.bop.backend.dao.PotSnapshotDao;
import com.bop.backend.exception.InsufficientFundsException;
import com.bop.backend.model.*;
import com.bop.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Robert on 08.03.2017.
 */
@RestController
@RequestMapping("/loan")
public class LoanController {
    @Autowired
    private LoanService loanService;
    @Autowired
    private PotService potService;
    @Autowired
    private UserService userService;
    @Autowired
    private PotSnapshotService potSnapshotService;
    @Autowired
    private UserPotService userPotService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void registerNewLoan(@RequestBody Map<String, String> json) throws ServletException {
        Pot pot = potService.findByPotId(Integer.parseInt(json.get("potId")));
        Loan loan = new Loan();
        float loanAmount = (float) Integer.parseInt(json.get("loan"));
        loan.setMoney(loanAmount);
        if(isMoneyAvailable(loan, pot)){
            User user = userService.findByTelephone(Integer.parseInt(json.get("telephone")));
            loan.setUser(user);
            loan.setPot(pot);
            loan.setCreatedDate(new Date());
            pot.setAvailableMoney(pot.getAvailableMoney() - loan.getMoney());
            user.setMoney(user.getMoney() + loanAmount);

            List<UserPot> userPots = userPotService.getUserPotListByPotId(Integer.parseInt(json.get("potId")));
            loanService.save(loan);
            for(UserPot userPot: userPots)
            {
                PotSnapshot investor = new PotSnapshot();
                investor.setLoan(loan);
                investor.setUser(userPot.getUser());
                float partOfPot = (userPot.getInvested() / pot.getTotalMoney()) * 100;

                if(partOfPot != 0f && user.getTelephone() != investor.getUser().getTelephone())
                {
                    investor.setPotPercentage(partOfPot);
                    investor.setUser(userPot.getUser());
                    potSnapshotService.save(investor);
                }
            }

            userService.save(user);
            potService.save(pot);
        }
        else{
            throw new ServletException("Not enough money available");
        }
    }

    @RequestMapping(value = "/getloans", method = RequestMethod.POST)
    public List<Loan> getLoansByUserId(@RequestBody Map<String, String> json) throws ServletException {
        return loanService.getLoansByUserId(Integer.parseInt(json.get("telephone")));
    }
    //Change me
    @RequestMapping(value = "/payback", method = RequestMethod.POST)
    public void paybackLoan(@RequestBody Map<String, String> json) throws ServletException, InsufficientFundsException {
        Loan loan = loanService.findLoanById(Integer.parseInt(json.get("loanId")));
        User user = userService.findByTelephone(loan.getUser().getTelephone());

        float interest = (float) (loan.getMoney() * 0.1);
        float loanAmount = loan.getMoney();
        float amountToPay = loanAmount + interest;

        if(user.getMoney() >= amountToPay)
        {
            Pot pot = potService.findByPotId(loan.getPot().getPotId());
            user.setMoney(user.getMoney() - amountToPay);
            pot.setAvailableMoney(pot.getAvailableMoney() + amountToPay);
            pot.setTotalMoney(pot.getTotalMoney() + interest);
            potService.save(pot);
            ArrayList<PotSnapshot> investors = potSnapshotService.getInvestorsByLoanId(Integer.parseInt(json.get("loanId")));

            for (PotSnapshot investor: investors)
            {
                UserPot userPot = userPotService.findByUserIdPotId(investor.getUser().getTelephone(), pot.getPotId());
                float myInterest = interest * investor.getPotPercentage() / 100;

                userPot.setInterest(userPot.getInterest() + myInterest);
                userPot.setInvested(userPot.getInvested() + myInterest);
                userPotService.save(userPot);

                potSnapshotService.delete(investor.getId());
            }
            loanService.delete(loan.getLoanId());

        }
        else throw new InsufficientFundsException("You don't have enough money to pay back your loan.");
    }

    private boolean isMoneyAvailable(Loan loan, Pot pot) {
        return pot.getAvailableMoney() >= loan.getMoney();
    }
}

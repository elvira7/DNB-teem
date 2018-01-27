package com.bop.backend.controller;

import com.bop.backend.model.Loan;
import com.bop.backend.model.PotSnapshot;
import com.bop.backend.model.UserPot;
import com.bop.backend.service.PotSnapshotService;
import com.bop.backend.service.UserPotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

/**
 * bop
 * NIK on 08/03/2017
 */
@RestController
@RequestMapping("/user/investment")
public class InvestmentController {
    @Autowired
    private UserPotService userPotService;
    @Autowired
    private PotSnapshotService potSnapshotService;

    @RequestMapping(value = "/invest", method = RequestMethod.POST)
//    public boolean sendInvitationToUser(@RequestBody int inviteToTelephone, int potId)
    public UserPot invest(@RequestBody Map<String, String> json) throws ServletException
    {
        UserPot userPot = null;
        if (!json.isEmpty() && json.containsKey("telephone") && json.containsKey("potId") && json.containsKey("investment"))
        {
            Integer telephone = Integer.parseInt(json.get("telephone"));
            Integer potId = Integer.parseInt(json.get("potId"));
            Integer investment = Integer.parseInt(json.get("investment"));
            userPot = userPotService.investMoney(telephone, potId, investment);
        }

        if (userPot == null) {
            throw new ServletException("You don't have enough money to make that investment.");
        }

        return userPot;
    }
    @RequestMapping(value = "/pendingincome", method = RequestMethod.POST)
//    public boolean sendInvitationToUser(@RequestBody int inviteToTelephone, int potId)
    public float getPendingIncome(@RequestBody Map<String, String> json)
    {
        ArrayList<PotSnapshot> investors =  potSnapshotService.getInvestorsByUserId(Integer.parseInt(json.get("telephone")));
        float pendingIncome = 0;

        for (PotSnapshot investor: investors)
        {
            Loan loan = investor.getLoan();
            pendingIncome += (float) (loan.getMoney() * investor.getPotPercentage() / 100);
        }
        return (float) (pendingIncome*0.1);
    }
}

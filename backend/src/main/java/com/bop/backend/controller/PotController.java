package com.bop.backend.controller;

import com.bop.backend.embedded.UserPotId;
import com.bop.backend.model.Loan;
import com.bop.backend.model.Pot;
import com.bop.backend.model.User;
import com.bop.backend.model.UserPot;
import com.bop.backend.service.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * bop
 * NIK on 04/03/2017
 */
@RestController
@RequestMapping("/pot")
public class PotController
{
    @Autowired
    private PotService potService;
    @Autowired
    private UserPotService userPotService;
    @Autowired
    private UserService userService;
    @Autowired
    private LoanService loanService;


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void registerNewPot(@RequestBody Map<String, String> json) throws ServletException {

        UserPotId userPotId = new UserPotId();
        Pot potFromJson = new Pot();
        User user = userService.findByTelephone(Integer.parseInt(json.get("telephone")));

        userPotId.setUser(user);
        userPotId.setPot(potFromJson);

        potFromJson.setName(json.get("name"));
        potFromJson.setAvailableMoney(0f);
        potFromJson.setTotalMoney(0f);
        potFromJson.setCreated(new Date());

        // fetch invites list
        // persist relationship

        potService.save(potFromJson);
        System.out.println(potFromJson.getPotId());
        userPotId.setPot(potFromJson);
        UserPot userPot = new UserPot(userPotId);
        userPot.setInvested(0);
        userPot.setInterest(0);
        userPotService.save(userPot);

    }
    @RequestMapping(value = "/getpotpart", method = RequestMethod.POST)
    public float getPotPercentage(@RequestBody Map<String, String> json) throws ServletException {

        UserPot userPot = userPotService.findByUserIdPotId(Integer.parseInt(json.get("telephone")), Integer.parseInt(json.get("potId")));

        return (float) (userPot.getInvested() / userPot.getPot().getTotalMoney()) * 100;

    }

    @RequestMapping(value = "/leave", method = RequestMethod.POST)
    public void leavePot(@RequestBody Map<String, String> json) throws ServletException {

        int userId = Integer.parseInt(json.get("telephone"));
        int potId = Integer.parseInt(json.get("potId"));

        List<Loan> loans = loanService.getLoansByUserId(userId);

        for (Loan loan: loans)
        {
            if(loan.getPot().getPotId().equals(potId))throw new ServletException("Please payback your loans before leaving");
        }


        UserPot userPot = userPotService.findByUserIdPotId(userId, potId);

        if (userPot.getInvested() != (float) 0) {
            User user = userService.findByTelephone(userId);
            Pot pot = potService.findByPotId(potId);
            user.setMoney(user.getMoney() + userPot.getInvested());
            pot.setTotalMoney(pot.getTotalMoney() - userPot.getInvested());
            if(pot.getAvailableMoney() < userPot.getInvested())
            {
                float remaining = userPot.getInvested() - pot.getAvailableMoney();
                pot.setAvailableMoney((float)0);
                // Make remaining a loan that has to be paid off before any more money goes into it
            }
            else
            {
                pot.setAvailableMoney(pot.getAvailableMoney() - userPot.getInvested());
            }
            userService.save(user);
            potService.save(pot);

        }

        userPotService.leavePot(userId, potId);

        List<UserPot> members = userPotService.getUserPotListByPotId(potId);

        if(members.size() == 0) potService.deleteGroup(potId);
    }

//    @RequestMapping(value = "/id", method = RequestMethod.POST)
//    public Pot findByT(@RequestBody int potId) {
//        return potService.findByT(potId);
//    }

//    @RequestMapping(value = "/invite", method = RequestMethod.POST)
////    public boolean sendInvitationToUser(@RequestBody int inviteToTelephone, int potId)
//    public void sendInvitationToUser(@RequestBody Map<String, String> json)
//    {
//        if (!json.isEmpty() && json.containsKey("invite") && json.containsKey("potId"))
//        {
//            Integer inviteToTelephone = Integer.parseInt(json.get("invite"));
//            Integer potId = Integer.parseInt(json.get("potId"));
//
//            if (userService.findByTelephone(inviteToTelephone)!=null) {
//                if(potService.findByPotId(potId)!=null){
//                    invitationService.invitationSent(inviteToTelephone, potId);
//                }
//            }
//        }
//    }
//
//    @RequestMapping(value = "/invites", method = RequestMethod.POST)
//    public List<Pot> getInviteList(@RequestBody int telephone){
//        if (telephone>0) {
//            if (userService.findByTelephone(telephone) != null)
//                return invitationService.getInvitationPotList(telephone);
//        }
//        return null;
//    }
}

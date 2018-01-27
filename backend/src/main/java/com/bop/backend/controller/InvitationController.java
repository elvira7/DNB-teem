package com.bop.backend.controller;

import com.bop.backend.embedded.UserPotId;
import com.bop.backend.model.Invitation;
import com.bop.backend.model.Pot;
import com.bop.backend.model.User;
import com.bop.backend.model.UserPot;
import com.bop.backend.service.InvitationService;
import com.bop.backend.service.PotService;
import com.bop.backend.service.UserPotService;
import com.bop.backend.service.UserService;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.validation.constraints.NotNull;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * bop
 * NIK on 07/03/2017
 */
@RestController
@RequestMapping("/user/invitation")
public class InvitationController {
    @Autowired
    private PotService potService;
    @Autowired
    private UserService userService;
    @Autowired
    private InvitationService invitationService;
    @Autowired
    private UserPotService userPotService;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
//    public boolean sendInvitationToUser(@RequestBody int inviteToTelephone, int potId)
    public void sendInvitationToUser(@RequestBody Map<String, String> json) throws ServletException {
        if (!json.isEmpty() && json.containsKey("invite") && json.containsKey("potId"))
        {
            Integer inviteToTelephone = Integer.parseInt(json.get("invite"));
            Integer potId = Integer.parseInt(json.get("potId"));

            if (userService.findByTelephone(inviteToTelephone) == null){
                throw new ServletException("User does not exist");
            }

            if(potService.findByPotId(potId) == null){
                throw new ServletException("Group does not exist");
            }
            try{
                invitationService.invitationSent(inviteToTelephone, potId);
            }
            catch (Exception r){
                //TODO: If it ain't working, fucking crutch it!
                throw new ServletException("User already invited");
            }


        }
        else {
            throw new ServletException("Missing arguments for invitation");
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public List<Pot> getInviteList(@RequestBody int telephone){
        if (telephone>0) {
            if (userService.findByTelephone(telephone) != null)
                return invitationService.getInvitationPotList(telephone);
        }

        return null;
    }

    @RequestMapping(value = "/decline", method = RequestMethod.POST)
    public void declineInvitation(@RequestBody Map<String, String> json) throws ServletException
    {
        if (!json.isEmpty() && json.containsKey("telephone") && json.containsKey("potId"))
        {
            Integer userPhone = Integer.parseInt(json.get("telephone"));
            Integer potId = Integer.parseInt(json.get("potId"));

            if( userPhone == null || potId == null)
                throw new ServletException("Cannot parse data for invite deletion");

            int success = invitationService.deleteInvitation(userPhone,potId);
            if( success != 1)
                throw new ServletException("Could not find invitation to decline");
        }
        else {
            throw new ServletException("Missing arguments to delete invitation");
        }

    }

    @RequestMapping(value = "/accept", method = RequestMethod.POST)
    public void acceptInvitation(@RequestBody Map<String, String> json) throws ServletException
    {
        if (!json.isEmpty() && json.containsKey("telephone") && json.containsKey("potId"))
        {
            Integer userPhone = Integer.parseInt(json.get("telephone"));
            Integer potId = Integer.parseInt(json.get("potId"));

            if( userPhone == null || potId == null)
                throw new ServletException("Cannot parse data for invite deletion");

            Invitation invite = invitationService.getInvitation(userPhone,potId);

            if( invite == null)
                throw new ServletException("Could not find invitation to accept");


            int insertedRow = 0;
            try{

            insertedRow = userPotService.attachUserToPot(invite.getUserId(),invite.getPotId());
            } catch (Exception e){
                throw new ServletException("Cannot accept invitation: user already in group");
            }

            if (insertedRow != 1){
                throw new ServletException("Could not accept invitation");
            }
            else{
                int deletedRow = invitationService.deleteInvitation(invite.getUserId(), invite.getPotId());
                if( deletedRow != 1)
                    throw new ServletException("Could not delete invitation after accepting");
            }
        }
        else {
            throw new ServletException("Missing arguments to delete invitation");
        }

    }
}

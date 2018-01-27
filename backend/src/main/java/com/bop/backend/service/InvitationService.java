package com.bop.backend.service;

import com.bop.backend.model.Invitation;
import com.bop.backend.model.Pot;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.util.List;

/**
 * bop
 * NIK on 07/03/2017
 */
public interface InvitationService
{
    boolean invitationSent(int sendToTelephone, int potId);
    List<Pot> getInvitationPotList(int telephone);
    int deleteInvitation(int telephone, int potId);
    Invitation getInvitation(int telephone, int potId);

    // + boolean acceptInvite(pk); pk = user_id + pot_id
}

package com.bop.backend.service.implementation;

import com.bop.backend.model.Invitation;
import com.bop.backend.model.Pot;
import com.bop.backend.service.InvitationService;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;

/**
 * bop
 * NIK on 07/03/2017
 */
@Service
@Transactional
public class InvitationServiceImpl implements InvitationService
{

    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean invitationSent(int sendToTelephone, int potId) {
        String temp = "INSERT user_pot_invites (user_tel, pot_id) VALUES (?1, ?2)";
        Query query = em.createNativeQuery(temp);
        query.setParameter(1, sendToTelephone);
        query.setParameter(2, potId);
        boolean persisted = query.executeUpdate()==1?true:false;
        return persisted;
    }

    @Override
    public List<Pot> getInvitationPotList(int telephone)
    {
        String temp = "SELECT pot.* FROM pot  JOIN user_pot_invites ON pot.pot_id = user_pot_invites.pot_id where user_pot_invites.user_tel = ?1";
        Query query = em.createNativeQuery(temp);
        query.setParameter(1, telephone);
        return query.getResultList();
    }

    @Override
    public int deleteInvitation(int telephone, int potId) {
        String temp = "DELETE FROM user_pot_invites WHERE user_tel = ?1 AND pot_id = ?2";
        Query query = em.createNativeQuery(temp);
        query.setParameter(1, telephone);
        query.setParameter(2, potId);
        return query.executeUpdate();
    }

    @Override
    public Invitation getInvitation(int telephone, int potId) {
        String temp1 = "SELECT user_tel FROM user_pot_invites WHERE user_tel = ?1 AND pot_id = ?2";
        String temp2 = "SELECT pot_id FROM user_pot_invites WHERE user_tel = ?1 AND pot_id = ?2";
        Query query1 = em.createNativeQuery(temp1);
        Query query2 = em.createNativeQuery(temp2);
        query1.setParameter(1, telephone);
        query1.setParameter(2, potId);
        query2.setParameter(1, telephone);
        query2.setParameter(2, potId);
        Invitation invitation = null;
        try{
            int userId = Integer.parseInt(query1.getSingleResult().toString());
            int groupId = Integer.parseInt(query2.getSingleResult().toString());
            invitation = new Invitation(userId, groupId);
            return invitation;
        }
        catch (NumberFormatException e){
            // Please don't hate us for doing this
        }
        catch (NoResultException e){
            // This is just a prototype, we know it's bad code
        }
        return invitation;
    }

    // + boolean acceptInvite(pk); pk = user_id + pot_id
}

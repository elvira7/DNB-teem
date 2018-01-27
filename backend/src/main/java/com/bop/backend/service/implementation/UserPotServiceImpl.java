package com.bop.backend.service.implementation;

import com.bop.backend.dao.PotDao;
import com.bop.backend.dao.UserDao;
import com.bop.backend.embedded.UserPotId;
import com.bop.backend.model.Pot;
import com.bop.backend.model.User;
import com.bop.backend.model.UserPot;
import com.bop.backend.dao.UserPotDao;
import com.bop.backend.service.UserPotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Robert on 06.03.2017.
 */
@Service
//@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class UserPotServiceImpl implements UserPotService {

    @Autowired
    private UserPotDao userPotDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private PotDao potDao;

    @PersistenceContext
    private EntityManager em;

    @Override
    public UserPot save(UserPot userPot) {return userPotDao.save(userPot);}

    public List<Pot> getPotList(int telephone)
    {
        List<Pot> potList = new ArrayList<>();
        Iterable<UserPot> userPots = findAll();

        for(UserPot userPot: userPots)
        {
            if(userPot.getUser().getTelephone() == telephone){
                Pot pot = userPot.getPot();
                potList.add(pot);
            }
        }
        return potList;
    }

    public List<User> getUserList(int potId)
    {
        List<User> userList = new ArrayList<>();
        Iterable<UserPot> userPots = findAll();

        for(UserPot userPot: userPots)
        {
            if(userPot.getPot().getPotId() == potId){
                User user = userPot.getUser();
                userList.add(user);
            }
        }
        return userList;
    }

    public List<UserPot> getUserPotListByTelephone(int telephone)
    {
        List<UserPot> userPotList = new ArrayList<>();
        Iterable<UserPot> userPots = findAll();

        for(UserPot userPot: userPots)
        {
            if(userPot.getUser().getTelephone() == telephone)
            {
                userPotList.add(userPot);
            }
        }
        return userPotList;
    }

    public List<UserPot> getUserPotListByPotId(int potId)
    {
        List<UserPot> userPotList = new ArrayList<>();
        Iterable<UserPot> userPots = findAll();

        for(UserPot userPot: userPots)
        {
            if(userPot.getPot().getPotId() == potId)
            {
                userPotList.add(userPot);
            }
        }
        return userPotList;
    }

    @Override
    public int attachUserToPot(int telephone, int potId) {
        String temp = "INSERT INTO user_pot (interest, invested, user_user_id, pot_pot_id) VALUES(0, 0, ?1, ?2)";
        Query query = em.createNativeQuery(temp);
        query.setParameter(1, telephone);
        query.setParameter(2, potId);

        return query.executeUpdate();
    }

    public Iterable<UserPot> findAll()
    {
        return userPotDao.findAll();
    }

    @Override
    public UserPot findByUserIdPotId(Integer userId, Integer potId) {
        UserPotId userPotId = new UserPotId(userDao.findByTelephone(userId), potDao.findByPotId(potId));
        UserPot userPot = userPotDao.findByPk(userPotId);
        if(userPot != null) return userPot;
        throw new NullPointerException("There are no records in your database with userId: " + userId + " and potId: " + potId + ".");
    }

    @Override
    public UserPot investMoney(Integer userId, Integer potId, Integer investment) {
        User user = userDao.findByTelephone(userId);
        UserPot userPot = null;

        if(enoughMoney(user, investment)){
            userPot = findByUserIdPotId(userId,potId);
            Pot pot = potDao.findByPotId(potId);
            user.setMoney(user.getMoney() - investment);
            user.setScore(user.getScore() + ((investment * 0.1)));
            userPot.setInvested(userPot.getInvested() + investment);
            pot.setAvailableMoney(pot.getAvailableMoney() + investment);
            pot.setTotalMoney(pot.getTotalMoney() + investment);

            userDao.save(user);
            potDao.save(pot);
            userPotDao.save(userPot);
        }

        return userPot;
    }

    @Override
    public void leavePot (int telephone,  int potId)
    {
        UserPotId userPotId= new UserPotId(userDao.findByTelephone(telephone), potDao.findByPotId(potId));
        userPotDao.deleteByPk(userPotId);
    }

    private boolean enoughMoney(User user, Integer amount)
    {
        if(user.getMoney() >= amount) return true;
        else return false;
    }

}

package com.bop.backend.service;

import com.bop.backend.embedded.UserPotId;
import com.bop.backend.model.Pot;
import com.bop.backend.model.User;
import com.bop.backend.model.UserPot;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import java.util.List;

/**
 * Created by Robert on 06.03.2017.
 */
//@Service
public interface UserPotService {
    UserPot save(UserPot userPot);
//    List<UserPot> findAll();
//    List<Pot> getListOfPotsByUserId(int telephone);
    Iterable<UserPot> findAll();
    UserPot findByUserIdPotId(Integer userId, Integer potId);
    UserPot investMoney(Integer userId, Integer potId, Integer investment) throws ServletException;
    List<Pot> getPotList(int telephone);
    List<User> getUserList(int potId);
    List<UserPot> getUserPotListByTelephone(int telephone);
    List<UserPot> getUserPotListByPotId(int potId);
    int attachUserToPot(int telephone, int potId);
    void leavePot (int telephone, int potId);
//    List<UserPot> getAllPots(Integer telephone);
}

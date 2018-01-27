package com.bop.backend.dao;

import com.bop.backend.embedded.UserPotId;
import com.bop.backend.model.Pot;
import com.bop.backend.model.User;
import com.bop.backend.model.UserPot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPotDao extends CrudRepository<UserPot, Long> {
    UserPot save(UserPot userPot);
//    List<Pot> getPotsByTelephone(int telephone);
    UserPot findByPk(UserPotId id);
//    void investMoney(Integer userId, Integer potId, Integer investment);
//    List<UserPot> getAllPots(Integer telephone);
    //UserPot findByPotId(int potId);
    Iterable<UserPot> findAll();
    void deleteByPk (UserPotId id);
}

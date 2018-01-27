package com.bop.backend.service.implementation;

import com.bop.backend.dao.PotDao;
import com.bop.backend.model.Pot;
import com.bop.backend.service.PotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
//@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class PotServiceImpl implements PotService {

    @Autowired
    private PotDao potDao;

    @Override
    public Pot save(Pot pot) {
        return potDao.save(pot);
    }

    @Override
    public Pot findByPotId(Integer potId) {
        return potDao.findByPotId(potId);
//        return userDao.findByTelephone(telephone);
    }

    @Override
    public void deleteGroup(int potId)
    {
        potDao.deleteByPotId(potId);
    }


}

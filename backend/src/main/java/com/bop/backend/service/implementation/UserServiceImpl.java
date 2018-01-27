package com.bop.backend.service.implementation;

import com.bop.backend.dao.PotDao;
import com.bop.backend.dao.UserDao;
import com.bop.backend.model.User;
import com.bop.backend.service.UserService;
import org.hibernate.loader.plan.exec.query.internal.QueryBuildingParametersImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Service
//@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PotDao potDao;

    @PersistenceContext
    private EntityManager em;

    @Override
    public User save(User user) {
        user.setDnbCustomer(false);
        return userDao.save(user);
    }

    @Override
    public User findByTelephone(int telephone) {
        return userDao.findByTelephone(telephone);
    }




}

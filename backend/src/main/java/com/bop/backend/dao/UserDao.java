package com.bop.backend.dao;


import com.bop.backend.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, Long> {
    User save(User user);
    User findByTelephone(int telephone);
}

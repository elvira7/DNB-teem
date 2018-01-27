package com.bop.backend.service;


import com.bop.backend.model.User;

public interface UserService {
    User save(User user);
    User findByTelephone(int telephone);
}

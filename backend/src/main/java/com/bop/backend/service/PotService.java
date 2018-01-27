package com.bop.backend.service;

import com.bop.backend.model.Pot;

public interface PotService {
    Pot save(Pot pot);
    Pot findByPotId(Integer potId);
    void deleteGroup(int potId);
}

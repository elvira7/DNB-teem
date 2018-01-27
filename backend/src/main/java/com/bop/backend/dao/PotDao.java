package com.bop.backend.dao;

import com.bop.backend.model.Pot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PotDao extends CrudRepository<Pot, Long> {
    Pot save(Pot pot);
    Pot findByPotId(int potId);
    void deleteByPotId(int potId);
}

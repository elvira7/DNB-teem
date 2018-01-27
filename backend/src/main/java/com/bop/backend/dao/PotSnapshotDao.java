package com.bop.backend.dao;

import com.bop.backend.model.PotSnapshot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Robert on 09.03.2017.
 */
@Repository
public interface PotSnapshotDao extends CrudRepository <PotSnapshot, Long> {
    PotSnapshot save(PotSnapshot potSnapshot);
    Iterable<PotSnapshot> findAll();
    void deleteById(Integer potSnapshotId);
}

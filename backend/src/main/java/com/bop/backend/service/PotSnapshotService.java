package com.bop.backend.service;

import com.bop.backend.model.PotSnapshot;

import java.util.ArrayList;

/**
 * Created by Robert on 09.03.2017.
 */
public interface PotSnapshotService {
    PotSnapshot save(PotSnapshot potSnapshot);
    ArrayList<PotSnapshot> getInvestorsByLoanId(Integer loanId);
    void delete(Integer potSnapshotId);
    ArrayList<PotSnapshot> getInvestorsByUserId(Integer userId);
}

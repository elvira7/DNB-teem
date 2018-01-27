package com.bop.backend.service.implementation;

import com.bop.backend.dao.PotSnapshotDao;
import com.bop.backend.model.PotSnapshot;
import com.bop.backend.service.PotSnapshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Created by Robert on 09.03.2017.
 */
@Service
//@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class PotSnapshotServiceImpl implements PotSnapshotService {
    @Autowired
    PotSnapshotDao potSnapshotDao;

    @Override
    public PotSnapshot save(PotSnapshot potSnapshot)
    {
        return potSnapshotDao.save(potSnapshot);
    }

    @Override
    public ArrayList<PotSnapshot> getInvestorsByLoanId(Integer loanId)
    {
        Iterable<PotSnapshot> potSnapshots = potSnapshotDao.findAll();
        ArrayList<PotSnapshot> listOfInvestors = new ArrayList<>();

        for(PotSnapshot potSnapshot: potSnapshots)
        {
            if(potSnapshot.getLoan().getLoanId() == loanId)
            {
                listOfInvestors.add(potSnapshot);
            }
        }

        return listOfInvestors;
    }

    @Override
    public ArrayList<PotSnapshot> getInvestorsByUserId(Integer userId)
    {
        Iterable<PotSnapshot> potSnapshots = potSnapshotDao.findAll();
        ArrayList<PotSnapshot> listOfInvestors = new ArrayList<>();

        for(PotSnapshot potSnapshot: potSnapshots)
        {
            if(potSnapshot.getUser().getTelephone() == userId)
            {
                listOfInvestors.add(potSnapshot);
            }
        }

        return listOfInvestors;
    }
    @Override
    public void delete(Integer potSnapshotId)
    {
        potSnapshotDao.deleteById(potSnapshotId);
    }
}

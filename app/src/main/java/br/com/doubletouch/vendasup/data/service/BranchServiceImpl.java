package br.com.doubletouch.vendasup.data.service;

import java.util.List;

import br.com.doubletouch.vendasup.data.database.dao.BranchDAO;
import br.com.doubletouch.vendasup.data.database.dao.UserBranchDAO;
import br.com.doubletouch.vendasup.data.entity.BranchOffice;
import br.com.doubletouch.vendasup.data.entity.UserBranchOffice;

/**
 * Created by LADAIR on 18/02/2015.
 */
public class BranchServiceImpl implements BranchService {

    BranchDAO branchDAO;

    public BranchServiceImpl() {
        this.branchDAO = new BranchDAO();
    }

    @Override
    public void saveOrUpdateSynchronous(List<BranchOffice> branches) {
        if(branches != null){
            branchDAO.insert(branches);
        }

    }

}

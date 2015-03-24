package br.com.doubletouch.vendasup.data.service;

import java.util.List;

import br.com.doubletouch.vendasup.data.database.dao.UserBranchDAO;
import br.com.doubletouch.vendasup.data.database.dao.UserDAO;
import br.com.doubletouch.vendasup.data.entity.User;
import br.com.doubletouch.vendasup.data.entity.UserBranchOffice;

/**
 * Created by LADAIR on 18/02/2015.
 */
public class UserBranchServiceImpl implements UserBranchService {

    UserBranchDAO userBranchDAO;

    public UserBranchServiceImpl() {
        this.userBranchDAO = new UserBranchDAO();
    }

    @Override
    public void saveOrUpdateSynchronous(List<UserBranchOffice> usersBranches) {
        if(usersBranches != null){
            userBranchDAO.insert(usersBranches);
        }

    }
}

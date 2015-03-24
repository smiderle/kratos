package br.com.doubletouch.vendasup.data.service;

import java.util.List;

import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.database.dao.CustomerDAO;
import br.com.doubletouch.vendasup.data.database.dao.UserDAO;
import br.com.doubletouch.vendasup.data.database.script.UserDB;
import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.User;
import br.com.doubletouch.vendasup.data.exception.CustomerNotFoundException;
import br.com.doubletouch.vendasup.data.exception.RepositoryErrorBundle;

/**
 * Created by LADAIR on 18/02/2015.
 */
public class UserServiceImpl implements UserService {

    UserDAO userDAO;

    public UserServiceImpl() {
        this.userDAO = new UserDAO();
    }

    @Override
    public void saveOrUpdateSynchronous(List<User> users) {


        userDAO.insert(users);


        UserBranchService userBranchService = new UserBranchServiceImpl();


        for (User user : users ){

            userBranchService.saveOrUpdateSynchronous( user.getUserBranches() );

        }
    }
}

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
    public void saveOrUpdate(User user, UserSavedCallback userSavedCallback) {
        try {
            userDAO.insert(user);
            userSavedCallback.onUserSaved( user );
        } catch (Exception e) {
            userSavedCallback.onError(new RepositoryErrorBundle(e));
        }

    }

    @Override
    public void saveOrUpdateSynchronous(List<User> users) {


        userDAO.insert(users);


        UserBranchService userBranchService = new UserBranchServiceImpl();


        for (User user : users) {

            userBranchService.saveOrUpdateSynchronous(user.getUserBranches());

        }
    }

    @Override
    public void saveOrUpdateSynchronous( User user ) {
        userDAO.insert(user);
    }

    @Override
    public void getUserByLoginAndPassword(String login, String password, UserLoginCallback userLoginCallback) {

        User user = userDAO.getByLoginAndPassword(login, password);

        if (user != null) {

            userLoginCallback.onUserLoaded(user);

        } else {

            userLoginCallback.onError(new RepositoryErrorBundle(new Exception("Usuário ou senha inválidos.")));

        }

    }
}

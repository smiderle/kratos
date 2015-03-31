package br.com.doubletouch.vendasup.data.service;

import java.util.List;

import br.com.doubletouch.vendasup.data.database.dao.UserBranchDAO;
import br.com.doubletouch.vendasup.data.database.dao.UserDAO;
import br.com.doubletouch.vendasup.data.entity.User;
import br.com.doubletouch.vendasup.data.entity.UserBranchOffice;
import br.com.doubletouch.vendasup.data.exception.RepositoryErrorBundle;

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

    @Override
    public void get(Integer branchID, Integer organizationID, Integer userID, UserBranchCallback userBranchCallback) {

        try{

            UserBranchOffice userBranchOffice = userBranchDAO.get(branchID, organizationID, userID);

            if(userBranchOffice != null && userBranchOffice.isEnable()){

                userBranchCallback.onUserBranchLoaded(userBranchOffice);

            } else {

                userBranchCallback.onError(new RepositoryErrorBundle( new Exception("Usuário sem permissão para acessar a empresa selecionada")));

            }

        } catch (Exception e){
            userBranchCallback.onError(new RepositoryErrorBundle(e));
        }


    }
}

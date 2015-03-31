package br.com.doubletouch.vendasup.data.service;

import java.util.List;

import br.com.doubletouch.vendasup.data.entity.BranchOffice;
import br.com.doubletouch.vendasup.data.entity.User;
import br.com.doubletouch.vendasup.data.entity.UserBranchOffice;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;

/**
 *
 * Interface que representa o Repositório para obter dados relacionados ao  {@link br.com.doubletouch.vendasup.data.entity.User}
 *
 * Created by LADAIR on 18/02/2015.
 */
public interface UserBranchService {

    interface UserBranchCallback {

        void onUserBranchLoaded( UserBranchOffice userBranchOffice );

        void onError(ErrorBundle errorBundle);

    }

    public void saveOrUpdateSynchronous( List<UserBranchOffice> usersBranches ) ;

    public void get(Integer branchID, Integer organizationID, Integer userID, UserBranchCallback userBranchCallback) ;

}

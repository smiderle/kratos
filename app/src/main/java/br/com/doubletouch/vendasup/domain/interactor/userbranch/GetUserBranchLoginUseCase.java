package br.com.doubletouch.vendasup.domain.interactor.userbranch;

import br.com.doubletouch.vendasup.data.entity.BranchOffice;
import br.com.doubletouch.vendasup.data.entity.User;
import br.com.doubletouch.vendasup.data.entity.UserBranchOffice;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.interactor.Interactor;

/**
 * Created by LADAIR on 24/03/2015.
 */
public interface GetUserBranchLoginUseCase extends Interactor{


    interface Callback {

        void onUserBranchLoaded(UserBranchOffice userBranchOffice);

        void onError(ErrorBundle errorBundle);

    }

    void execute(BranchOffice branchOffice,User user, Callback callback);

}

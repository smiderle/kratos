package br.com.doubletouch.vendasup.data.service;

import java.util.Collection;
import java.util.List;

import br.com.doubletouch.vendasup.data.entity.BranchOffice;
import br.com.doubletouch.vendasup.data.entity.PriceTable;
import br.com.doubletouch.vendasup.data.entity.UserBranchOffice;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;

/**
 *
 * Interface que representa o Repositório para obter dados relacionados ao  {@link br.com.doubletouch.vendasup.data.entity.User}
 *
 * Created by LADAIR on 18/02/2015.
 */
public interface BranchService {

    interface BranchListCallback {
        void onBranchListLoaded(List<BranchOffice> branches);

        void onError(ErrorBundle errorBundle);
    }


    void getBranchList( BranchListCallback branchListCallback);

    public void saveOrUpdateSynchronous(List<BranchOffice> branches) ;
}

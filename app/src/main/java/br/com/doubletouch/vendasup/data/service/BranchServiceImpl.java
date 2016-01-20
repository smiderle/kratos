package br.com.doubletouch.vendasup.data.service;

import java.util.List;

import br.com.doubletouch.vendasup.data.database.dao.BranchDAO;
import br.com.doubletouch.vendasup.data.database.dao.UserBranchDAO;
import br.com.doubletouch.vendasup.data.entity.BranchOffice;
import br.com.doubletouch.vendasup.data.entity.UserBranchOffice;
import br.com.doubletouch.vendasup.data.exception.RepositoryErrorBundle;

/**
 * Created by LADAIR on 18/02/2015.
 */
public class BranchServiceImpl implements BranchService {

    private BranchDAO branchDAO;

    public BranchServiceImpl() {
        this.branchDAO = new BranchDAO();
    }

    @Override
    public void getBranchList( BranchListCallback branchListCallback ) {

        try {

            List< BranchOffice > branches = branchDAO.getAll();
            branchListCallback.onBranchListLoaded( branches );

        } catch ( Exception e ) {

            branchListCallback.onError( new RepositoryErrorBundle( e ) );

        }


    }


    @Override
    public List< BranchOffice > getBranchList() {


        return branchDAO.getAll();


    }

    @Override
    public void saveOrUpdateSynchronous( List< BranchOffice > branches ) {
        if ( branches != null ) {
            branchDAO.insert( branches );
        }

    }

}

package br.com.doubletouch.vendasup.data.service;

import java.util.List;

import br.com.doubletouch.vendasup.data.database.dao.OrganizationDAO;
import br.com.doubletouch.vendasup.data.database.dao.UserBranchDAO;
import br.com.doubletouch.vendasup.data.entity.Organization;
import br.com.doubletouch.vendasup.data.entity.UserBranchOffice;

/**
 * Created by LADAIR on 18/02/2015.
 */
public class OrganizationServiceImpl implements OrganizationService {

    OrganizationDAO organizationDAO;

    public OrganizationServiceImpl() {
        this.organizationDAO = new OrganizationDAO();
    }

    @Override
    public void saveOrUpdateSynchronous(Organization organization) {
        if(organization != null){
            organizationDAO.insert(organization);
        }

    }
}

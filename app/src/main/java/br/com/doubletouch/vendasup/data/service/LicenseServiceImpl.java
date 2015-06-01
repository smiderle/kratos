package br.com.doubletouch.vendasup.data.service;

import java.util.List;

import br.com.doubletouch.vendasup.data.database.dao.LicenseDAO;
import br.com.doubletouch.vendasup.data.entity.License;

/**
 * Created by LADAIR on 26/05/2015.
 */
public class LicenseServiceImpl implements LicenseService {

    private LicenseDAO licenseDAO;

    public LicenseServiceImpl() {

        licenseDAO = new LicenseDAO();

    }

    @Override
    public void save(License license) {

        licenseDAO.insert(license);

    }

    @Override
    public License findByUserId(Integer userID) {

        return licenseDAO.getByUserId(userID);

    }

    @Override
    public List<License> getAll() {

        return  licenseDAO.getAll();
    }
}

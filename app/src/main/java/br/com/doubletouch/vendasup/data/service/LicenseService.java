package br.com.doubletouch.vendasup.data.service;

import java.util.List;

import br.com.doubletouch.vendasup.data.entity.License;

/**
 * Created by LADAIR on 26/05/2015.
 */
public interface LicenseService {

    void save(License license);

    License findByUserId(Integer userID);

    List<License> getAll();

}

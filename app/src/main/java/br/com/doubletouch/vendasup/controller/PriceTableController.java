package br.com.doubletouch.vendasup.controller;

import java.util.List;

import br.com.doubletouch.vendasup.data.database.dao.PriceTableDAO;
import br.com.doubletouch.vendasup.data.entity.PriceTable;

/**
 * Created by LADAIR on 10/03/2015.
 */
public class PriceTableController {

    public void saveOrUpdate(List<PriceTable> tabelas){
        PriceTableDAO priceTableDAO = new PriceTableDAO();
        priceTableDAO.insert(tabelas);
    }
}

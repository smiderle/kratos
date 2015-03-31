package br.com.doubletouch.vendasup.data.service;

import java.util.List;

import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.database.dao.PriceTableDAO;
import br.com.doubletouch.vendasup.data.entity.PriceTable;
import br.com.doubletouch.vendasup.data.exception.RepositoryErrorBundle;

/**
 * Created by LADAIR on 10/03/2015.
 */
public class PriceTableServiceImpl implements PriceTableService {

    PriceTableDAO priceTableDAO;

    public PriceTableServiceImpl(){
        priceTableDAO =  new PriceTableDAO();
    }

    public void getPriceTableList( final PriceTableListCallback priceTableListCallback) {

        try{

            List<PriceTable> all = priceTableDAO.getAll( VendasUp.getBranchOffice().getBranchOfficeID() );
            priceTableListCallback.onPriceTableListLoaded(all);

        } catch (Exception e){

            priceTableListCallback.onError(new RepositoryErrorBundle(e));

        }
    }

    public void getPriceTable(Integer idPriceTable, PriceTableCallback priceTableCallback) {

        if(idPriceTable != null){
            try {
                PriceTable priceTable = priceTableDAO.get(idPriceTable);
                priceTableCallback.onPriceTableLoaded( priceTable );
            } catch (Exception e) {
                priceTableCallback.onError(new RepositoryErrorBundle(e));
            }
        } else {
            priceTableCallback.onError(new RepositoryErrorBundle(new IllegalArgumentException("Id não informado")));
        }


    }

    @Override
    public void saveOrUpdateSynchronous(List<PriceTable> tabelas) {

        priceTableDAO.insert(tabelas);

    }
}

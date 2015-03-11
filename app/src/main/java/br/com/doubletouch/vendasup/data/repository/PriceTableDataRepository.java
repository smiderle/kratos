package br.com.doubletouch.vendasup.data.repository;

import java.util.Collection;

import br.com.doubletouch.vendasup.data.entity.PriceTable;
import br.com.doubletouch.vendasup.data.exception.RepositoryErrorBundle;
import br.com.doubletouch.vendasup.data.repository.datasource.PriceTableDataStore;
import br.com.doubletouch.vendasup.data.repository.datasource.PriceTableDataStoreFactory;
import br.com.doubletouch.vendasup.domain.repository.PriceTableRepository;

/**
 * Created by LADAIR on 10/03/2015.
 */
public class PriceTableDataRepository implements PriceTableRepository {

    private PriceTableDataStoreFactory priceTableDataStoreFactory;


    private static PriceTableDataRepository INSTANCE;

    public PriceTableDataRepository(PriceTableDataStoreFactory priceTableDataStoreFactory) {
        if(priceTableDataStoreFactory == null){
            throw  new IllegalArgumentException("Não é permitido parametros nulos no construtor.");
        }

        this.priceTableDataStoreFactory = priceTableDataStoreFactory;
    }

    public static synchronized PriceTableDataRepository getInstace(PriceTableDataStoreFactory priceTableDataStoreFactory) {
        if(INSTANCE == null){
            INSTANCE = new PriceTableDataRepository(priceTableDataStoreFactory);
        }

        return INSTANCE;
    }

    @Override
    public void getPriceTableList(Integer branchId, final PriceTableListCallback priceTableListCallback) {
        final PriceTableDataStore priceTableDataStore = this.priceTableDataStoreFactory.create();

        priceTableDataStore.getPriceTableList(branchId, new PriceTableDataStore.PriceTableListCallback() {

            @Override
            public void onPriceTableListLoaded(Collection<PriceTable> priceTableCollection) {
                priceTableListCallback.onPriceTableListLoaded(priceTableCollection);
            }

            @Override
            public void onError(Exception exception) {
                priceTableListCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }
}

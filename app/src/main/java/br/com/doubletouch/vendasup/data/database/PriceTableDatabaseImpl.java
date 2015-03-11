package br.com.doubletouch.vendasup.data.database;

import java.util.List;

import br.com.doubletouch.vendasup.data.entity.PriceTable;

/**
 * Created by LADAIR on 10/03/2015.
 */
public class PriceTableDatabaseImpl implements  PricetableDatabase {

    private PriceTablePersistence priceTablePersistence;

    public PriceTableDatabaseImpl(PriceTablePersistence priceTablePersistence) {
        this.priceTablePersistence = priceTablePersistence;
    }

    @Override
    public synchronized void list(Integer branchId, PriceTableListCallback callback) {

        List<PriceTable> tabelas = priceTablePersistence.getAll(branchId);
        callback.onPriceTableListLoaded(tabelas);
    }
}

package br.com.doubletouch.vendasup.domain.repository;

import java.util.Collection;

import br.com.doubletouch.vendasup.data.entity.PriceTable;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;

/**
 * Created by LADAIR on 10/03/2015.
 */
public interface PriceTableRepository {

    interface PriceTableListCallback {
        void onPriceTableListLoaded(Collection<PriceTable> priceTableCollection);

        void onError(ErrorBundle errorBundle);
    }

    void getPriceTableList(Integer branchId, PriceTableListCallback priceTableListCallback);
}

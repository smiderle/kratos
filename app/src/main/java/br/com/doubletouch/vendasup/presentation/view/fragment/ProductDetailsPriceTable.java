package br.com.doubletouch.vendasup.presentation.view.fragment;

import java.util.List;

import br.com.doubletouch.vendasup.data.entity.PriceTable;

/**
 * Created by LADAIR on 17/03/2015.
 */
public interface ProductDetailsPriceTable {


    void loadPriceTableList(List<PriceTable> tabelas);


    void showError(String message);
}

package br.com.doubletouch.vendasup.presentation.view.fragment;

import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Installment;
import br.com.doubletouch.vendasup.data.entity.PriceTable;

/**
 * Created by LADAIR on 11/03/2015.
 */
public interface CustomerDetailsFinancial {

    void loadPriceTableList(List<PriceTable> tabelas);

    void loadPriceTable(PriceTable priceTable);

    void loadInstallmentList(List<Installment> parcelas);

    void loadInstallment(Installment installment);

    void showError(String message);
}

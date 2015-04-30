package br.com.doubletouch.vendasup.presentation.view.fragment.order;

import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Installment;

/**
 * Created by LADAIR on 16/04/2015.
 */
public interface OrderPaymentView {

    void loadInstallmentList(List<Installment> parcelas);

    void renderTotal(Double total);

    void showError(String message);
}

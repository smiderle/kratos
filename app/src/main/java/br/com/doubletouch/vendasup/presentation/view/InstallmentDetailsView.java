package br.com.doubletouch.vendasup.presentation.view;

import br.com.doubletouch.vendasup.data.entity.Installment;
import br.com.doubletouch.vendasup.data.entity.License;

/**
 * Created by LADAIR on 26/05/2015.
 */
public interface InstallmentDetailsView extends LoadDataView {


    void renderInstallment(Installment installment);

    void installmentSaved();



}

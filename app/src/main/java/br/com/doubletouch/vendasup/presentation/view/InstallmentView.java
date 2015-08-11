package br.com.doubletouch.vendasup.presentation.view;

import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Installment;

/**
 * Created by LADAIR on 27/07/2015.
 */
public interface InstallmentView  extends  LoadDataView {

    void viewInstallment(Installment intInstallment );

    void renderInstamentList(List<Installment> list );
}

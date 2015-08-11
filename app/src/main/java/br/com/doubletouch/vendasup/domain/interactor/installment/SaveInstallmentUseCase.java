package br.com.doubletouch.vendasup.domain.interactor.installment;

import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.Installment;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.interactor.Interactor;

/**
 * Created by LADAIR on 08/03/2015.
 */
public interface SaveInstallmentUseCase extends Interactor {


    /**
     * Callback usado para notificar quando um produto for carregado ou ocorrer um erro.
     */
    interface Callback {
        void onInstallmentSaved(Installment  installment);
        void onError(ErrorBundle errorBundle);
    }

    /**
     *
     * @param callback
     */
    void execute(Installment installment, Callback callback);
}

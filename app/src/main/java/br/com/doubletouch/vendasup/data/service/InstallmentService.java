package br.com.doubletouch.vendasup.data.service;

import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Installment;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;

/**
 * Created by LADAIR on 16/03/2015.
 */
public interface InstallmentService {

    interface InstallmentListCallback {
        void onInstallmentListLoaded(List<Installment> installmentCollection);

        void onError(ErrorBundle errorBundle);
    }

    interface InstallmentCallback {
        void onInstallmentLoaded(Installment installment);

        void onError(ErrorBundle errorBundle);
    }

    void getInstallmentList( InstallmentListCallback installmentListCallback);

    void getInstallment( Integer idInstallment, InstallmentCallback installmentCallback);

    /**
     *
     * @param installment
     */
    void saveOrUpdateSynchronous(List<Installment> installment);

    Installment get(Integer id);


}

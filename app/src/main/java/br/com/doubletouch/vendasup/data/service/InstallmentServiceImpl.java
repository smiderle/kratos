package br.com.doubletouch.vendasup.data.service;

import java.util.List;

import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.database.dao.InstallmentDAO;
import br.com.doubletouch.vendasup.data.entity.Installment;
import br.com.doubletouch.vendasup.data.exception.RepositoryErrorBundle;

/**
 * Created by LADAIR on 16/03/2015.
 */
public class InstallmentServiceImpl implements InstallmentService {

    private InstallmentDAO installmentDAO;

    public InstallmentServiceImpl() {
        this.installmentDAO = new InstallmentDAO();
    }

    @Override
    public void getInstallmentList(InstallmentListCallback installmentListCallback) {

        try {
            List<Installment> all = installmentDAO.getAll(VendasUp.getUsuarioLogado().getBranchID());

            installmentListCallback.onInstallmentListLoaded(all);
        }catch (Exception e){
            installmentListCallback.onError(new RepositoryErrorBundle(e));
        }


    }

    @Override
    public void getInstallment(Integer idInstallment, InstallmentCallback installmentCallback) {

        try {
            Installment installment = installmentDAO.get(idInstallment);

            installmentCallback.onInstallmentLoaded(installment);
        }catch (Exception e){
            installmentCallback.onError(new RepositoryErrorBundle(e));
        }
    }

    @Override
    public void saveOrUpdateSynchronous(List<Installment> installment) {

        installmentDAO.insert(installment);

    }
}

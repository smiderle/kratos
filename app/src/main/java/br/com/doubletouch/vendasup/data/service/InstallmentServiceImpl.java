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
            List<Installment> all = installmentDAO.getAll(VendasUp.getBranchOffice().getBranchOfficeID());

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


    @Override
    public void save(Installment installment,final InstallmentSaveCallback installmentSaveCallback) {

        try{
            installmentDAO.insert(installment);
            installmentSaveCallback.onInstallmentSaved(installment);
        }catch (Exception e) {
            installmentSaveCallback.onError(new RepositoryErrorBundle(e));

        }
    }

    @Override
    public void delete(Installment installment, InstallmentRemoveCallback installmentRemoveCallback) {

        try{

            Integer count = installmentDAO.count();

            if( count > 1 ){
                installmentDAO.insert(installment);
                installmentRemoveCallback.onInstallmentRemoved(installment);
            } else {
                Exception e = new Exception("Pelo menos um parcelamento deve estar cadastrado.");
                installmentRemoveCallback.onError( new RepositoryErrorBundle(e));
            }

        }catch (Exception e) {
            installmentRemoveCallback.onError(new RepositoryErrorBundle(e));

        }

    }

    @Override
    public Integer getLessNegative() {

        Integer min = installmentDAO.min();

        if(min <= 0){

            min -= 1;

        } else {
            min = -1;
        }

        return min;
    }

    @Override
    public Installment get(Integer id) {

        return installmentDAO.get(id);

    }


    @Override
    public void updateByIdMobile(List<Installment> installments) {

        OrderService orderService = new OrderServiceImpl();
        CustomerService customerService = new CustomerServiceImpl();

        for( Installment installment : installments ) {

            orderService.updateInstallment(installment.getIdMobile(), installment.getID());
            customerService.updateInstallment(installment.getIdMobile(), installment.getID());
            installment.setSetSyncPending(false);

            installmentDAO.updateByIdMobile(installment);


        }
    }


    @Override
    public List<Installment> getAllSyncPendenteNovos(Integer branchId) {

        return installmentDAO.getAllSyncPendendeNovos(branchId);

    }

    @Override
    public List<Installment> getAllSyncPendenteAtualizados(Integer branchId) {

        return installmentDAO.getAllSyncPendendeAtualizados(branchId);

    }

}

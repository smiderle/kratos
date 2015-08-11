package br.com.doubletouch.vendasup.presentation.presenter;

import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.entity.Installment;
import br.com.doubletouch.vendasup.data.service.InstallmentService;
import br.com.doubletouch.vendasup.data.service.InstallmentServiceImpl;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.interactor.installment.GetInstallmentUseCase;
import br.com.doubletouch.vendasup.domain.interactor.installment.SaveInstallmentUseCase;
import br.com.doubletouch.vendasup.presentation.exception.ErrorMessageFactory;
import br.com.doubletouch.vendasup.presentation.view.InstallmentDetailsView;
import br.com.doubletouch.vendasup.util.StringUtil;

/**
 * Created by LADAIR on 23/02/2015.
 */
public class InstallmentDetailsPresenter implements Presenter {

    private Integer customerId;

    private final InstallmentDetailsView installmentDetailsView;
    private final GetInstallmentUseCase getInstallmentDetailsUseCase;
    private final SaveInstallmentUseCase saveInstallmentUseCase;

    private InstallmentService installmentService;

    public InstallmentDetailsPresenter(InstallmentDetailsView installmentDetailsView, GetInstallmentUseCase getInstallmentDetailsUseCase, SaveInstallmentUseCase saveInstallmentUseCase) {
        if (installmentDetailsView == null || getInstallmentDetailsUseCase == null || saveInstallmentUseCase == null) {
            throw new IllegalArgumentException("Parametros do construtor não podem ser nulos.");
        }

        this.installmentDetailsView = installmentDetailsView;
        this.getInstallmentDetailsUseCase = getInstallmentDetailsUseCase;
        this.saveInstallmentUseCase = saveInstallmentUseCase;

        installmentService = new InstallmentServiceImpl();
    }

    public void initialize(Integer customerId){
        this.customerId = customerId;
        this.loadInstallmentDetails();
    }

    public void saveInstallment(String descricao, String condicoes){

        String msg = isValido(descricao, condicoes);

        if( msg == null ){
            Integer codigo = installmentService.getLessNegative();

            Installment installment = new Installment();

            installment.setID(codigo);
            installment.setIdMobile(codigo);

            installment.setDescription(descricao);
            installment.setExcluded(false);
            installment.setTax(0.0);
            installment.setActive(true);
            installment.setBranchID(VendasUp.getBranchOffice().getBranchOfficeID());
            installment.setInstallmentsDays(condicoes);
            installment.setOrganizationID(VendasUp.getBranchOffice().getOrganization().getOrganizationID());
            installment.setSetSyncPending(true);

            this.showViewLoading();
            this.installmentSaveExecutor(installment);

        } else {
            this.installmentDetailsView.showError(msg);
        }
    }


    private String isValido(String descricao, String condicoes){

        String erro = null;

        if(descricao == null || descricao.trim().equals("")){
            erro = "Informe uma descrição.";
        } else if(!isValid(condicoes)){
            erro = "Informe condições validas. Ex: 0 30 60 90";
        }

        return erro;

    }

    private boolean isValid(String condicoes){

        boolean valido = true;
        int ultimoDia = -1;

        try{
            if(condicoes != null && !condicoes.trim().equals("") && StringUtil.isOnlyNumbers(condicoes)){

                String[] dias = condicoes.split(" ");

                for(String dia : dias){

                    int diaInt = Integer.parseInt( dia.trim() );

                    if(diaInt > ultimoDia && diaInt < 1000){
                        ultimoDia = diaInt;
                    } else {
                        valido = false;
                        break;
                    }

                }


            } else {
                valido = false;
            }

        } catch (Exception e){
            valido = false;
        }


        return valido;
    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    private void loadInstallmentDetails(){
        this.showViewLoading();
        this.getInstallmentDetails();
    }

    public void createNewInstallment(){

        Integer codigo = installmentService.getLessNegative();

        Installment installment = new Installment();
        installment.setID(codigo);
        installment.setIdMobile(codigo);
        installment.setOrganizationID(VendasUp.getBranchOffice().getOrganization().getOrganizationID());
        installment.setBranchID(VendasUp.getBranchOffice().getBranchOfficeID());
        installment.setExcluded(false);
        installment.setTax(0.0);
        installment.setActive(true);

        showInstallmentDetailsInView(installment);
    }




    private void showViewLoading() {
        this.installmentDetailsView.showLoading();
    }

    private void hideViewLoading() {
        this.installmentDetailsView.hideLoading();
    }


    private void showInstallmentDetailsInView(Installment installment) {

        this.installmentDetailsView.renderInstallment(installment);
    }


    private void installmentSaved(){
        this.installmentDetailsView.installmentSaved();
    }




    private void getInstallmentDetails(){
        this.getInstallmentDetailsUseCase.execute(this.customerId, this.getInstallmentDetailsCallback);
    }



    private void installmentSaveExecutor(Installment installment){
        this.saveInstallmentUseCase.execute(installment, this.saveCustomerCallback);
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.installmentDetailsView.getContext(),
                errorBundle.getException());
        this.installmentDetailsView.showError(errorMessage);
    }


    private final GetInstallmentUseCase.Callback getInstallmentDetailsCallback = new GetInstallmentUseCase.Callback(){


        @Override
        public void onInstallmentLoaded(Installment installment) {

            InstallmentDetailsPresenter.this.showInstallmentDetailsInView(installment);
            InstallmentDetailsPresenter.this.hideViewLoading();

        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            InstallmentDetailsPresenter.this.hideViewLoading();
            InstallmentDetailsPresenter.this.showErrorMessage(errorBundle);
        }


    };

    private final SaveInstallmentUseCase.Callback saveCustomerCallback = new SaveInstallmentUseCase.Callback() {

        @Override
        public void onInstallmentSaved(Installment installment) {
            InstallmentDetailsPresenter.this.installmentSaved();
            InstallmentDetailsPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            InstallmentDetailsPresenter.this.hideViewLoading();
            InstallmentDetailsPresenter.this.showErrorMessage(errorBundle);
        }

    };

}

package br.com.doubletouch.vendasup.presentation.presenter;

import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Installment;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.interactor.installment.GetInstallmentListUseCase;
import br.com.doubletouch.vendasup.presentation.exception.ErrorMessageFactory;
import br.com.doubletouch.vendasup.presentation.view.InstallmentView;

/**
 * Created by LADAIR on 21/04/2015.
 */
public class InstallmentListPresenter implements Presenter {



    private final InstallmentView installmentListView;

    private final GetInstallmentListUseCase getInstallmentListUseCase;

    public InstallmentListPresenter(InstallmentView installmentListView, GetInstallmentListUseCase getInstallmentListUseCase) {
        this.installmentListView = installmentListView;
        this.getInstallmentListUseCase = getInstallmentListUseCase;
    }


    public void initialize() {
        this.loadInstallmentList();
    }


    public void onInstallmentClicked(Installment installment){
        this.installmentListView.viewInstallment(installment);
    }


    private void loadInstallmentList() {
        this.showViewLoading();
        this.getInstallmentList();
    }


    private void getInstallmentList(){
        this.getInstallmentListUseCase.execute(getInstallmentListCallback);
    }


    private void showInstallmentCollectionInView(List<Installment> installments){
        this.installmentListView.renderInstamentList(installments);
    }


    private void showViewLoading() {
        this.installmentListView.showLoading();
    }

    private void hideViewLoading() {
        this.installmentListView.hideLoading();
    }


    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.installmentListView.getContext(),
                errorBundle.getException());
        this.installmentListView.showError(errorMessage);
    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }


    private GetInstallmentListUseCase.Callback getInstallmentListCallback = new GetInstallmentListUseCase.Callback() {

        @Override
        public void onInstallmentListLoaded(List<Installment> installmentCollection) {
            InstallmentListPresenter.this.showInstallmentCollectionInView(installmentCollection);
            InstallmentListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {

            InstallmentListPresenter.this.hideViewLoading();
            showErrorMessage(errorBundle);

        }
    };


}

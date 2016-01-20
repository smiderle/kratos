package br.com.doubletouch.vendasup.presentation.presenter;

import br.com.doubletouch.vendasup.presentation.view.SigninView;

/**
 * Created by LADAIR on 13/05/2015.
 */
public class SigninPresenter implements Presenter {

    private SigninView signinView;

    public SigninPresenter(SigninView signinView) {
        this.signinView = signinView;
    }

    public void signin() {

        signinView.showDialogSynchronization();

    }


    private void showViewLoading() {
        this.signinView.showLoading();
    }


    private void hideViewLoading() {
        this.signinView.hideLoading();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }
}

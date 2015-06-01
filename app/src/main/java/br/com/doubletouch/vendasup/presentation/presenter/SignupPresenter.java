package br.com.doubletouch.vendasup.presentation.presenter;

import br.com.doubletouch.vendasup.presentation.view.SigninView;
import br.com.doubletouch.vendasup.presentation.view.SignupView;

/**
 * Created by LADAIR on 13/05/2015.
 */
public class SignupPresenter implements Presenter {

    private SigninView signinView;

    public SignupPresenter(SigninView signinView) {
        this.signinView = signinView;
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

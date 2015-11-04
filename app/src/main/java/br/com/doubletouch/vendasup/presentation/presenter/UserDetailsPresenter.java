package br.com.doubletouch.vendasup.presentation.presenter;

import br.com.doubletouch.vendasup.data.entity.User;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.interactor.user.SaveUserUseCase;
import br.com.doubletouch.vendasup.presentation.exception.ErrorMessageFactory;
import br.com.doubletouch.vendasup.presentation.view.UserDetailsView;

/**
 * Created by LADAIR on 15/02/2015.
 */
public class UserDetailsPresenter implements Presenter {

    private final UserDetailsView userDetailsView;

    private SaveUserUseCase saveUserUseCase;

    public UserDetailsPresenter(UserDetailsView userDetailsView, SaveUserUseCase saveUserUseCase) {
        if (userDetailsView == null) {
            throw new IllegalArgumentException("Parametros do construtor não podem ser nulos.");
        }

        this.userDetailsView = userDetailsView;
        this.saveUserUseCase = saveUserUseCase;
    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    public void saveUser(User user) {

        if(user.getPassword() == null || user.getPassword().trim().equals("")){

            this.userDetailsView.showError("Informe uma senha");

        } else {

            this.showViewLoading();

            saveUserUseCase.execute(user, saveUserCallback);

        }



    }

    private void showViewLoading() {
        this.userDetailsView.showLoading();
    }

    private void hideViewLoading() {
        this.userDetailsView.hideLoading();
    }


    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.userDetailsView.getContext(),
                errorBundle.getException());
        this.userDetailsView.showError(errorMessage);

    }

    SaveUserUseCase.Callback saveUserCallback = new SaveUserUseCase.Callback() {
        @Override
        public void onUserSaved(User user) {
            userDetailsView.saved(user);

            UserDetailsPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {

            showErrorMessage(errorBundle);

        }
    };


}

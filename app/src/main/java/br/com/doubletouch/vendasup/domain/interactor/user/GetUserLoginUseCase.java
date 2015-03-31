package br.com.doubletouch.vendasup.domain.interactor.user;

import br.com.doubletouch.vendasup.data.entity.User;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.interactor.Interactor;

/**
 * Created by LADAIR on 24/03/2015.
 */
public interface GetUserLoginUseCase extends Interactor{


    /**
     * Callback usado para notificar quando um usuario for carregado se o usuario com determinado login e senha não existirem.
     */
    interface Callback {
        void onUserLoaded(User user);
        void onError(ErrorBundle errorBundle);
    }

    /**
     *
     * @param callback
     */
    void execute(String login, String password, Callback callback);
}

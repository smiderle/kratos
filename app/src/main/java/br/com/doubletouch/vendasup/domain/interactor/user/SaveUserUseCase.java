package br.com.doubletouch.vendasup.domain.interactor.user;

import br.com.doubletouch.vendasup.data.entity.User;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.interactor.Interactor;

/**
 * Created by LADAIR on 24/03/2015.
 */
public interface SaveUserUseCase extends Interactor{

    interface Callback {
        void onUserSaved(User user);
        void onError(ErrorBundle errorBundle);
    }

    /**
     *
     * @param callback
     */
    void execute(User user, Callback callback);
}

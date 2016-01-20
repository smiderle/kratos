package br.com.doubletouch.vendasup.data.service;

import java.util.Collection;
import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.User;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;

/**
 *
 * Interface que representa o Repositório para obter dados relacionados ao  {@link br.com.doubletouch.vendasup.data.entity.User}
 *
 * Created by LADAIR on 18/02/2015.
 */
public interface UserService {

    interface UserLoginCallback {

        void onUserLoaded(User user);

        void onError(ErrorBundle errorBundle);
    }

    interface UserSavedCallback {

        void onUserSaved(User user);

        void onError(ErrorBundle errorBundle);
    }



    void saveOrUpdate(User user, UserSavedCallback userSavedCallback) ;

    void saveOrUpdateSynchronous(List<User> users) ;

    void saveOrUpdateSynchronous(User user) ;

    void getUserByLoginAndPassword(String login, String password,UserLoginCallback userLoginCallback);
}

package br.com.doubletouch.vendasup.domain.interactor.user;

import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.data.entity.User;
import br.com.doubletouch.vendasup.data.service.UserService;
import br.com.doubletouch.vendasup.data.service.UserServiceImpl;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;

/**
 * Created by LADAIR on 24/03/2015.
 */
public class GetUserLoginUseCaseImpl implements  GetUserLoginUseCase {

    private final UserService userService;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Callback callback;


    private String login;

    private String password;

    public GetUserLoginUseCaseImpl(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        this.userService = new UserServiceImpl();
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(String login, String password, Callback callback) {
        if ( callback == null) {
            throw new IllegalArgumentException("Parametros inválidos.");
        }

        this.login = login;
        this.password = password;
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override
    public void run() {
        this.userService.getUserByLoginAndPassword(this.login, password,  this.userLoginCallback);
    }



    private UserService.UserLoginCallback userLoginCallback = new UserService.UserLoginCallback() {
        @Override
        public void onUserLoaded(User user) {
            notifyUserLoadedSuccessfully(user);
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyError(errorBundle);
        }
    };



    private void notifyUserLoadedSuccessfully(final User user) {
        this.postExecutionThread.post(new Runnable() {
            @Override public void run() {
                callback.onUserLoaded(user);
            }
        });
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(new Runnable() {
            @Override public void run() {
                callback.onError(errorBundle);
            }
        });
    }


}

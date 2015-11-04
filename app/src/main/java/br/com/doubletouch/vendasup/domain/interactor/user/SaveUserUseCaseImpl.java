package br.com.doubletouch.vendasup.domain.interactor.user;

import br.com.doubletouch.vendasup.data.entity.User;
import br.com.doubletouch.vendasup.data.service.UserService;
import br.com.doubletouch.vendasup.data.service.UserServiceImpl;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;

/**
 * Created by LADAIR on 24/03/2015.
 */
public class SaveUserUseCaseImpl implements  SaveUserUseCase {

    private final UserService userService;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Callback callback;


    private User user;

    public SaveUserUseCaseImpl(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        this.userService = new UserServiceImpl();
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(User user, Callback callback) {
        if ( callback == null) {
            throw new IllegalArgumentException("Parametros inválidos.");
        }

        this.user = user;
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override
    public void run() {
        this.userService.saveOrUpdate(user, userSavedCallback);
    }


    private UserService.UserSavedCallback userSavedCallback = new UserService.UserSavedCallback() {
        @Override
        public void onUserSaved(User user) {
            notifyUserSavedSuccessfully(user);

        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyError(errorBundle);
        }
    };


    private void notifyUserSavedSuccessfully(final User user) {
        this.postExecutionThread.post(new Runnable() {
            @Override public void run() {
                callback.onUserSaved(user);
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

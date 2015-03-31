package br.com.doubletouch.vendasup.domain.interactor.userbranch;

import br.com.doubletouch.vendasup.data.entity.BranchOffice;
import br.com.doubletouch.vendasup.data.entity.User;
import br.com.doubletouch.vendasup.data.entity.UserBranchOffice;
import br.com.doubletouch.vendasup.data.service.UserBranchService;
import br.com.doubletouch.vendasup.data.service.UserBranchServiceImpl;
import br.com.doubletouch.vendasup.data.service.UserService;
import br.com.doubletouch.vendasup.data.service.UserServiceImpl;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;

/**
 * Created by LADAIR on 24/03/2015.
 */
public class GetUserBranchLoginUseCaseImpl implements  GetUserBranchLoginUseCase {

    private final UserBranchService userBranchService;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Callback callback;

    private BranchOffice branchOffice;

    private User user;

    public GetUserBranchLoginUseCaseImpl(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        this.userBranchService = new UserBranchServiceImpl();
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override
    public void run() {
        this.userBranchService.get( branchOffice.getBranchOfficeID(), branchOffice.getOrganization().getOrganizationID(),user.getUserID(), userLoginCallback );
    }

    @Override
    public void execute(BranchOffice branchOffice,User user, Callback callback) {
        if ( callback == null) {
            throw new IllegalArgumentException("Parametros inválidos.");
        }

        this.user = user;
        this.branchOffice = branchOffice;
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    private UserBranchService.UserBranchCallback userLoginCallback = new UserBranchService.UserBranchCallback() {

        @Override
        public void onUserBranchLoaded(UserBranchOffice userBranchOffice) {
            notifyUserBranchLoadedSuccessfully(userBranchOffice);
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyError(errorBundle);
        }
    };

    private void notifyUserBranchLoadedSuccessfully(final UserBranchOffice userBranchOffice) {
        this.postExecutionThread.post(new Runnable() {
            @Override public void run() {
                callback.onUserBranchLoaded(userBranchOffice);
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

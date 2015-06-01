package br.com.doubletouch.vendasup.presentation.presenter;

import java.util.Date;
import java.util.List;

import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.SharedPreferencesUtil;
import br.com.doubletouch.vendasup.data.entity.BranchOffice;
import br.com.doubletouch.vendasup.data.entity.License;
import br.com.doubletouch.vendasup.data.entity.User;
import br.com.doubletouch.vendasup.data.entity.UserBranchOffice;
import br.com.doubletouch.vendasup.data.executor.JobExecutor;
import br.com.doubletouch.vendasup.data.service.LicenseService;
import br.com.doubletouch.vendasup.data.service.LicenseServiceImpl;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.domain.interactor.branch.GetBranchListUseCase;
import br.com.doubletouch.vendasup.domain.interactor.branch.GetBranchListUseCaseImpl;
import br.com.doubletouch.vendasup.domain.interactor.user.GetUserLoginUseCase;
import br.com.doubletouch.vendasup.domain.interactor.user.GetUserLoginUseCaseImpl;
import br.com.doubletouch.vendasup.domain.interactor.userbranch.GetUserBranchLoginUseCase;
import br.com.doubletouch.vendasup.domain.interactor.userbranch.GetUserBranchLoginUseCaseImpl;
import br.com.doubletouch.vendasup.presentation.UIThread;
import br.com.doubletouch.vendasup.presentation.exception.ErrorMessageFactory;
import br.com.doubletouch.vendasup.presentation.view.LoginView;

/**
 * Created by LADAIR on 24/03/2015.
 */
public class LoginPresenter implements Presenter {

    private LoginView loginView;

    GetBranchListUseCase getBranchListUseCase ;
    GetUserLoginUseCase getUserLoginUseCase;
    GetUserBranchLoginUseCase getUserBranchLoginUseCase;

    private BranchOffice branchOffice;
    private User user;

    public LoginPresenter(LoginView loginView) {

        this.loginView = loginView;
        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();

        getBranchListUseCase = new GetBranchListUseCaseImpl(threadExecutor, postExecutionThread);
        getUserLoginUseCase = new GetUserLoginUseCaseImpl(threadExecutor, postExecutionThread);
        getUserBranchLoginUseCase = new GetUserBranchLoginUseCaseImpl(threadExecutor, postExecutionThread);

    }

    public void initialize(){
        loadLastLogin();
        loadBranches();
    }

    public void login(String login, String senha, BranchOffice branchOffice){

        if(login != null && login.trim() != "" && senha.trim() != "" && senha != null && branchOffice != null)

        this.branchOffice = branchOffice;
        LoginPresenter.this.getUserLoginUseCase.execute(login, senha, getUserLoginUseCaseCallback);
    }

    private void showBranches(List<BranchOffice> branches){
        loginView.renderBranches(branches);
    }

    private void userAndPasswordSuccess(User user) {

        LicenseService licenseService = new LicenseServiceImpl();

        License license = licenseService.findByUserId(user.getUserID());

        if( isLicensaValida( user ) ){

            getUserBranchLoginUseCase.execute( branchOffice, user, getUserBranchLoginUseCaseCallback );

        } else {

            VendasUp.setUser( user );
            VendasUp.setBranchOffice( branchOffice );

            this.loginView.expiredVersion();

        }



    }

    private boolean isLicensaValida(User user){

        boolean isValid = true;

        LicenseService licenseService = new LicenseServiceImpl();

        License license = licenseService.findByUserId(user.getUserID());

        Date expirateDate = new Date( license.getExpirationDate() );

        if( license.isExpired() ||  expirateDate.before( new Date() ) ){

            isValid = false;

        }

        return isValid;

    }

    private void loginsuccessful() {
        saveLastLogin();

        VendasUp.setUser( user );
        VendasUp.setBranchOffice( branchOffice );

        loginView.loginSuccessful();
    }


    public void loadBranches(){

        getBranchListUseCase.execute(getBranchesTableCallback);
    }

    private final  GetUserBranchLoginUseCase.Callback getUserBranchLoginUseCaseCallback = new GetUserBranchLoginUseCase.Callback() {


        @Override
        public void onUserBranchLoaded(UserBranchOffice userBranchOffice) {
            LoginPresenter.this.hideViewLoading();
            LoginPresenter.this.loginsuccessful();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            LoginPresenter.this.hideViewLoading();
            LoginPresenter.this.showErrorMessage(errorBundle);
        }
    };


    private final  GetBranchListUseCase.Callback getBranchesTableCallback = new GetBranchListUseCase.Callback() {

        @Override
        public void onBranchListLoaded(List<BranchOffice> branches) {
            LoginPresenter.this.hideViewLoading();
            LoginPresenter.this.showBranches(branches);
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            LoginPresenter.this.hideViewLoading();
            LoginPresenter.this.showErrorMessage(errorBundle);

        }
    };


    private final  GetUserLoginUseCase.Callback getUserLoginUseCaseCallback = new GetUserLoginUseCase.Callback() {

        @Override
        public void onUserLoaded(User user) {
            LoginPresenter.this.user = user;
            LoginPresenter.this.userAndPasswordSuccess(user);
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            LoginPresenter.this.hideViewLoading();
            LoginPresenter.this.showErrorMessage(errorBundle);
        }
    };


    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.loginView.getContext(),
                errorBundle.getException());
        this.loginView.showError(errorMessage);
    }

    private void hideViewLoading() {
        this.loginView.hideLoading();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    private void saveLastLogin(){

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(loginView.getContext());
        sharedPreferencesUtil.addString(SharedPreferencesUtil.PREFERENCES_LOGIN, user.getEmail() );
        sharedPreferencesUtil.addString(SharedPreferencesUtil.PREFERENCES_PASSWORD , user.getPassword() );

    }

    private void loadLastLogin(){

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(loginView.getContext());
        String login = sharedPreferencesUtil.getString(SharedPreferencesUtil.PREFERENCES_LOGIN);
        String password = sharedPreferencesUtil.getString(SharedPreferencesUtil.PREFERENCES_PASSWORD);

        loginView.loadLastLogin(login, password);

    }
}

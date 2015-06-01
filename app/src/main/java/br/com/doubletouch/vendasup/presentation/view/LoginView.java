package br.com.doubletouch.vendasup.presentation.view;

import java.util.List;

import br.com.doubletouch.vendasup.data.entity.BranchOffice;

/**
 * Created by LADAIR on 24/03/2015.
 */
public interface LoginView extends LoadDataView {

    void renderBranches(List<BranchOffice> branches);

    void loginSuccessful();

    void loadLastLogin(String login, String password);

    void expiredVersion();
}

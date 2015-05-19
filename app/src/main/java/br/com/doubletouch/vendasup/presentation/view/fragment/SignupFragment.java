package br.com.doubletouch.vendasup.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.SharedPreferencesUtil;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.presenter.SignupPresenter;
import br.com.doubletouch.vendasup.presentation.view.SigninView;
import br.com.doubletouch.vendasup.presentation.view.SignupView;
import br.com.doubletouch.vendasup.presentation.view.activity.LoginActivity;
import br.com.doubletouch.vendasup.presentation.view.dialog.SigninSignupDialog;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by LADAIR on 14/05/2015.
 */
public class SignupFragment   extends BaseFragment implements SigninView {



    private Activity activity;

    private Navigator navigator;

    private SignupPresenter signupPresenter;


    @InjectView(R.id.et_organization)
    EditText et_organization;

    @InjectView(R.id.et_email)
    EditText et_email;

    @InjectView(R.id.et_password)
    EditText et_password;

    @InjectView(R.id.et_password_re)
    EditText et_password_re;

    @InjectView(R.id.btn_signup)
    Button btn_signup;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.inject(this, fragmentView);

        navigator = new Navigator();


        return fragmentView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.activity = activity;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void initializePresenter() {

        signupPresenter = new SignupPresenter(this);

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context getContext() {

        return getActivity().getApplicationContext();
    }


    @OnClick(R.id.btn_signup)
    public void onClickSignUp(){

        signupPresenter.signup();

    }

    @Override
    public void showDialogSynchronization() {
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String organizationName = et_organization.getText().toString();

        SigninSignupDialog dialog = new SigninSignupDialog();
        dialog.setSynchronizationView(this);
        dialog.setSigninAttributes(organizationName, "Usuário Demonstração", email, password );

        dialog.show(getFragmentManager(), "");
    }

    @Override
    public void onSuccessSynchronization() {


        Toast.makeText(getActivity(), "SUCESSO", Toast.LENGTH_LONG).show();
        navigator.navigateTo(activity, LoginActivity.class);

        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil( activity );
        sharedPreferencesUtil.addString(SharedPreferencesUtil.PREFERENCES_LOGIN, email );
        sharedPreferencesUtil.addString(SharedPreferencesUtil.PREFERENCES_PASSWORD , password );


    }
}

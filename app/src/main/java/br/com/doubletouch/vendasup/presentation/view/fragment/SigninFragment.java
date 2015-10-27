package br.com.doubletouch.vendasup.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.SharedPreferencesUtil;
import br.com.doubletouch.vendasup.data.entity.BranchOffice;
import br.com.doubletouch.vendasup.data.entity.Order;
import br.com.doubletouch.vendasup.data.entity.User;
import br.com.doubletouch.vendasup.data.entity.enumeration.SignType;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.presenter.SigninPresenter;
import br.com.doubletouch.vendasup.presentation.view.SigninView;
import br.com.doubletouch.vendasup.presentation.view.SynchronizationView;
import br.com.doubletouch.vendasup.presentation.view.activity.LoginActivity;
import br.com.doubletouch.vendasup.presentation.view.activity.SignupActivity;
import br.com.doubletouch.vendasup.presentation.view.dialog.SigninSignupDialog;
import br.com.doubletouch.vendasup.presentation.view.dialog.SynchronizationProgressDialog;
import br.com.doubletouch.vendasup.util.anim.AnimationSetUtil;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by LADAIR on 13/05/2015.
 */
public class SigninFragment  extends BaseFragment implements SigninView {


    private Activity activity;

    private Navigator navigator;

    private SigninPresenter signinPresenter;


    @InjectView(R.id.et_password)
    EditText et_password;

    @InjectView(R.id.et_email)
    EditText et_email;

    @InjectView(R.id.btn_signin)
    Button btn_signin;


    @InjectView(R.id.btn_notification)
    Button btn_notification;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_signin, container, false);
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

        signinPresenter = new SigninPresenter(this);

    }


    @OnClick(R.id.btn_signin)
    public void onClickSignIn(){

        signinPresenter.signin();

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String message) {

        btn_notification.setText(message);
        btn_notification.setVisibility(View.VISIBLE);

        AnimationSet animationSet = AnimationSetUtil.get(activity);
        btn_notification.startAnimation(animationSet);

    }

    @Override
    public Context getContext() {
        return getActivity().getApplicationContext();
    }

    @Override
    public void showDialogSynchronization() {

        String email = et_email.getText().toString().replaceAll("\\s", "");
        String password = et_password.getText().toString().replaceAll("\\s", "");

        if(isValidate(email, password)){

            User user = new User();
            user.setEmail(email);
            user.setPassword(password);

            VendasUp.setUser(user);


            SigninSignupDialog dialog = new SigninSignupDialog();
            dialog.setSynchronizationView(this);
            dialog.setSignType(SignType.SIGNIN);
            dialog.setSigninAttributes(null,null, email, password, null );

            dialog.show(getFragmentManager(), "");

        } else {
            showError("Informe um e-mail e senha válidos.");
        }






    }

    private boolean isValidate(String email, String password){

        if(email.trim().equals("") || password.trim().equals("") || !email.contains("@") || !email.contains(".com") ){

            return false;

        }

        return true;

    }


    @Override
    public void onSuccessSynchronization() {

        //Toast.makeText(getActivity(), "SUCESSO", Toast.LENGTH_LONG).show();
        navigator.navigateTo( activity, LoginActivity.class, Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );

        String email = et_email.getText().toString().replaceAll("\\s", "");
        String password = et_password.getText().toString().replaceAll("\\s", "");

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil( activity );
        sharedPreferencesUtil.addString(SharedPreferencesUtil.PREFERENCES_LOGIN, email );
        sharedPreferencesUtil.addString(SharedPreferencesUtil.PREFERENCES_PASSWORD , password );

    }

}

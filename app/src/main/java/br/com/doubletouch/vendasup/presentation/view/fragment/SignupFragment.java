package br.com.doubletouch.vendasup.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.SharedPreferencesUtil;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.presenter.SignupPresenter;
import br.com.doubletouch.vendasup.presentation.view.SigninView;
import br.com.doubletouch.vendasup.presentation.view.SignupView;
import br.com.doubletouch.vendasup.presentation.view.activity.LoginActivity;
import br.com.doubletouch.vendasup.presentation.view.activity.SigninActivity;
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

    @InjectView(R.id.et_email)
    EditText et_email;

    @InjectView(R.id.et_password)
    EditText et_password;

    @InjectView(R.id.et_password_re)
    EditText et_password_re;

    @InjectView(R.id.btn_signup)
    Button btn_signup;

    @InjectView(R.id.btn_signin)
    Button btn_signin;

    @InjectView(R.id.btn_notification)
    Button btn_notification;


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

        mostrarNotificacao(message);

    }

    @Override
    public Context getContext() {

        return getActivity().getApplicationContext();
    }


    @OnClick(R.id.btn_signup)
    public void onClickSignUp(){

        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String password_re = et_password_re.getText().toString().trim();

        String messageError = validate(email, password, password_re);

        if( messageError == null ) {

            SigninSignupDialog dialog = new SigninSignupDialog();
            dialog.setSynchronizationView(this);
            dialog.setSigninAttributes("Empresa Demonstração", "Usuário Demonstração", email, password );

            dialog.show(getFragmentManager(), "");

        } else {

            mostrarNotificacao( messageError );

        }

    }

    private String validate(String email, String password, String password_re){

        String messageError = null;

        if( email.equals("") || !email.contains("@") ){

            messageError = "Email inválido.";

        } else if( password.equals("") ) {

            messageError = "Informa uma senha.";

        } else if( !password.equals(password_re)){

            messageError = "As senhas não são iguais.";

        }


        return messageError;

    }

    @OnClick(R.id.btn_signin)
    public void onClickSignIn(){

        navigator.navigateTo(activity, SigninActivity.class);


    }

    @Override
    public void showDialogSynchronization() {

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

    private void mostrarNotificacao( String message ) {

        btn_notification.setText(message);
        btn_notification.setVisibility(View.VISIBLE);
        Animation animBounce = AnimationUtils.loadAnimation(activity, R.anim.move_in_move_out);
        btn_notification.startAnimation(animBounce);

    }

}

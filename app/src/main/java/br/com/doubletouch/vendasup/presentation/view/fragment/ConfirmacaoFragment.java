package br.com.doubletouch.vendasup.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.SharedPreferencesUtil;
import br.com.doubletouch.vendasup.data.entity.User;
import br.com.doubletouch.vendasup.data.entity.enumeration.SignType;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.presenter.SigninPresenter;
import br.com.doubletouch.vendasup.presentation.view.ConfirmationView;
import br.com.doubletouch.vendasup.presentation.view.SigninView;
import br.com.doubletouch.vendasup.presentation.view.SignupView;
import br.com.doubletouch.vendasup.presentation.view.activity.LoginActivity;
import br.com.doubletouch.vendasup.presentation.view.dialog.ConfirmacaoCadastroDialog;
import br.com.doubletouch.vendasup.presentation.view.dialog.SigninSignupDialog;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by LADAIR on 13/05/2015.
 */
public class ConfirmacaoFragment extends BaseFragment implements SigninView {


    private Activity activity;

    private Navigator navigator;

    private SigninPresenter signinPresenter;

    @InjectView(R.id.et_codigo)
    EditText et_codigo;

    @InjectView(R.id.tv_wellcome)
    TextView tv_wellcome;

    @InjectView(R.id.btn_validar)
    Button btn_validar;


    @InjectView(R.id.btn_notification)
    Button btn_notification;

    private String email;

    private String senha;

    private static final String ARGUMENT_EMAIL= "kratos.ARGUMENT_EMAIL";
    private static final String ARGUMENT_PASSWORD= "kratos.ARGUMENT_PASSWORD";

    public static ConfirmacaoFragment newInstance(String email, String password) {
        ConfirmacaoFragment confirmacaoFragment = new ConfirmacaoFragment();
        Bundle argumentsBundle = new Bundle();
        argumentsBundle.putSerializable(ARGUMENT_EMAIL, email);
        argumentsBundle.putSerializable(ARGUMENT_PASSWORD, password);

        confirmacaoFragment.setArguments(argumentsBundle);
        return confirmacaoFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.email = getArguments().getString(ARGUMENT_EMAIL);
        this.senha = getArguments().getString(ARGUMENT_PASSWORD);



        navigator = new Navigator();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_confirmation, container, false);
        ButterKnife.inject(this, fragmentView);

        String mensagem = getString(R.string.tv_confirmacao_codigo);

        tv_wellcome.setText(  String.format( mensagem, email ) );


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

        //signinPresenter = new SigninPresenter(this);

    }


    @OnClick(R.id.btn_validar)
    public void onClickValidar(){

        String codigo = et_codigo.getText().toString().replaceAll("\\s", "");

        if(isValidate(codigo)){

            SigninSignupDialog dialog = new SigninSignupDialog();
            dialog.setSynchronizationView(this);
            dialog.setSigninAttributes("Empresa Demonstração", "Usuário Demonstração", email, senha , codigo);

            dialog.show(getFragmentManager(), "");

        } else {
            showError("Informe um código válido.");
        }

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
        Animation animBounce = AnimationUtils.loadAnimation(activity, R.anim.move_in_move_out);
        btn_notification.startAnimation(animBounce);

    }

    @Override
    public Context getContext() {
        return getActivity().getApplicationContext();
    }

    @Override
    public void showDialogSynchronization() {

       throw new IllegalStateException("Não implementado");


    }

    private boolean isValidate(String codigo){

        if(codigo.trim().equals("")  ){

            return false;

        }

        return true;

    }


    @Override
    public void onSuccessSynchronization() {

        navigator.navigateTo( activity, LoginActivity.class, Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil( activity );
        sharedPreferencesUtil.addString(SharedPreferencesUtil.PREFERENCES_LOGIN, email );
        sharedPreferencesUtil.addString(SharedPreferencesUtil.PREFERENCES_PASSWORD , senha );

    }

}

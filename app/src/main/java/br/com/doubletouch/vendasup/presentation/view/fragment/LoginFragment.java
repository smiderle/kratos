package br.com.doubletouch.vendasup.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.BranchOffice;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.presenter.LoginPresenter;
import br.com.doubletouch.vendasup.presentation.view.LoginView;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by LADAIR on 24/03/2015.
 */
public class LoginFragment extends BaseFragment implements LoginView {


    @InjectView(R.id.bt_login_login)
    Button bt_login_login;

    @InjectView(R.id.et_login_password)
    EditText et_login_password;

    @InjectView(R.id.et_login_user)
    EditText et_login_user;

    @InjectView(R.id.sp_login_branches)
    Spinner sp_login_branches;

    @InjectView(R.id.btn_notification)
    Button btn_notification;

    private LoginPresenter loginPresenter;

    private Activity activity;

    private Navigator navigator;

    @Override
    public void initializePresenter() {

        loginPresenter = new LoginPresenter(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.inject(this, fragmentView);

        navigator = new Navigator();

        bt_login_login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                loginPresenter.login(et_login_user.getText().toString(),et_login_password.getText().toString(), (BranchOffice)sp_login_branches.getSelectedItem());
            }
        });

        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loginPresenter.initialize();
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
        //Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    private void mostrarNotificacao( final String msg ){

        btn_notification.setText(msg);
        btn_notification.setVisibility(View.VISIBLE);
        Animation animBounce = AnimationUtils.loadAnimation(activity, R.anim.move_in_move_out);
        btn_notification.startAnimation( animBounce );

    }

    @Override
    public Context getContext() {

        return getActivity().getApplicationContext();

    }

    @Override
    public void renderBranches( List<BranchOffice> branches ) {

        ArrayAdapter<BranchOffice> adapter = new ArrayAdapter<BranchOffice>(activity, R.layout.spinner_text, branches);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );
        sp_login_branches.setAdapter(adapter);

    }

    @Override
    public void loginSuccessful() {

        navigator.navigateToMenu(activity);

    }

    @Override
    public void loadLastLogin(String login, String password) {

        if(login != null) {
            et_login_password.setText(password);
        }

        if(password != null) {
            et_login_user.setText(login);
        }

    }
}

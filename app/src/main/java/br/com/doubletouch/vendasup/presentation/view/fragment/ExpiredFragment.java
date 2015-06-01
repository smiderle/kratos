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
import android.widget.TextView;
import android.widget.Toast;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.License;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.presenter.ExpiredPresenter;
import br.com.doubletouch.vendasup.presentation.view.ExpiredView;
import br.com.doubletouch.vendasup.presentation.view.dialog.ExpiredProgressDialog;
import br.com.doubletouch.vendasup.presentation.view.dialog.SynchronizationProgressDialog;
import br.com.doubletouch.vendasup.util.DateUtil;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by LADAIR on 26/05/2015.
 */
public class ExpiredFragment extends BaseFragment implements ExpiredView {


    private Navigator navigator;

    private Activity activity;

    @InjectView(R.id.btn_notification)
    Button btn_notification;

    @InjectView(R.id.tv_expired_info)
    TextView tv_expired_info;

    private ExpiredPresenter expiredPresenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_expired, container, false);
        ButterKnife.inject(this, fragmentView);

        navigator = new Navigator();

        expiredPresenter.validaDataExpiracao();

        return fragmentView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.activity = activity;
    }

    @Override
    public void initializePresenter() {

        expiredPresenter = new ExpiredPresenter(this);


    }



    @OnClick( R.id.btn_expired_sync )
    public void sync(){
        ExpiredProgressDialog dialog = new ExpiredProgressDialog();
        dialog.setExpiredView(this);
        dialog.show(getFragmentManager(), "");
    }

    @Override
    public void onSuccessSynchronization() {

        //Toast.makeText(activity, "Sincronizado", Toast.LENGTH_LONG).show();
        expiredPresenter.validaDataExpiracao();

    }

    @Override
    public void onErrorSynchronization(String message) {
        btn_notification.setBackgroundResource(R.drawable.notification_warning);
        mostrarNotificacao(message);
        //synchronizationPresenter.initialize();


    }

    @Override
    public void onRenewedLicense() {

        navigator.previousActivity(activity);

    }

    @Override
    public void onExpiredLicense(License license) {

        tv_expired_info.setText("Sua licensa expirou em " + DateUtil.formatDate(license.getExpirationDate()));

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


    private void mostrarNotificacao(final String msg){

        btn_notification.setText(msg);
        //É alterado para invisivel e depois para visivel, pois por algum motivo em algumas telas, a animação não esta iniciando. Então quando é alterado de invisivel para visivel a animação inicia.
        btn_notification.setVisibility(View.INVISIBLE);
        btn_notification.setVisibility(View.VISIBLE);
        Animation animBounce = AnimationUtils.loadAnimation(activity, R.anim.move_in_move_out);
        btn_notification.startAnimation( animBounce );


    }

}

package br.com.doubletouch.vendasup.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.List;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.entity.Order;
import br.com.doubletouch.vendasup.data.executor.JobExecutor;
import br.com.doubletouch.vendasup.data.net.Integracao;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.domain.interactor.order.GetOrderSyncPendingUseCase;
import br.com.doubletouch.vendasup.domain.interactor.order.GetOrderSyncPendingUseCaseImpl;
import br.com.doubletouch.vendasup.presentation.UIThread;
import br.com.doubletouch.vendasup.presentation.exception.ErrorMessageFactory;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.presenter.SynchronizationPresenter;
import br.com.doubletouch.vendasup.presentation.view.SynchronizationView;
import br.com.doubletouch.vendasup.presentation.view.adapter.SynchronizationAdapter;
import br.com.doubletouch.vendasup.presentation.view.dialog.SynchronizationProgressDialog;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by LADAIR on 29/04/2015.
 */
public class SynchronizationFragment extends BaseFragment implements SynchronizationView {

    private Navigator navigator;

    private Activity activity;


    @InjectView(R.id.rl_progress)
    RelativeLayout rl_progress;

    @InjectView(R.id.lv_orders)
    ListView lv_orders;

    @InjectView(R.id.btn_notification)
    Button btn_notification;

    private SynchronizationAdapter syncAdapter;

    private SynchronizationPresenter synchronizationPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_sync, container, false);
        ButterKnife.inject(this, fragmentView);

        navigator = new Navigator();



        return fragmentView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        synchronizationPresenter.initialize();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.activity = activity;
    }



    @Override
    public void initializePresenter() {

        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();
        GetOrderSyncPendingUseCase getOrderSyncPendingUseCase = new GetOrderSyncPendingUseCaseImpl(threadExecutor, postExecutionThread);
        synchronizationPresenter = new SynchronizationPresenter(this, getOrderSyncPendingUseCase);

    }

    @Override
    public void showLoading() {
        this.rl_progress.setVisibility(View.VISIBLE);
        this.getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideLoading() {
        this.rl_progress.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context getContext() {

        return getActivity().getApplicationContext();

    }

    @Override
    public void renderOrderList(List<Order> orders) {


        if(orders != null){
            if(syncAdapter == null){
                syncAdapter = new SynchronizationAdapter(activity, orders);
            } else {
                syncAdapter.setOrders(orders);
            }

            this.lv_orders.setAdapter(syncAdapter);
        }

    }

    @Override
    public void onSuccessSynchronization() {

        btn_notification.setBackgroundResource(R.drawable.notification_success);
        mostrarNotificacao("Dados sincronizados com sucesso!");
        synchronizationPresenter.initialize();

    }

    @Override
    public void onErrorSynchronization(String message) {

        btn_notification.setBackgroundResource(R.drawable.notification_warning);
        mostrarNotificacao(message);
        synchronizationPresenter.initialize();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                navigator.previousActivity(activity);
                break;
            default:
                super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }



    @OnClick( R.id.btn_sync )
    void onRefreshClick(){

        SynchronizationProgressDialog dialog = new SynchronizationProgressDialog();
        dialog.setSynchronizationView(this);
        dialog.show(getFragmentManager(), "");

        /*Integracao integracao = new Integracao();


        try {

            integracao.enviarDados( VendasUp.getBranchOffice().getBranchOfficeID() );
            integracao.receberDados( VendasUp.getBranchOffice().getOrganization().getOrganizationID() );

            mostrarNotificacaoSucess("Dados sincronizados com sucesso!");
            synchronizationPresenter.initialize();

        } catch (Exception e) {

            String errorMessage = ErrorMessageFactory.create(activity, e );
            Log.e(VendasUp.APP_TAG, e.getMessage(),e);
            mostrarNotificacaoWarning( errorMessage );

        }*/

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

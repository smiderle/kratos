package br.com.doubletouch.vendasup.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.Installment;
import br.com.doubletouch.vendasup.data.entity.enumeration.ViewMode;
import br.com.doubletouch.vendasup.data.executor.JobExecutor;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.domain.interactor.installment.GetInstallmentListUseCase;
import br.com.doubletouch.vendasup.domain.interactor.installment.GetInstallmentListUseCaseImpl;
import br.com.doubletouch.vendasup.presentation.UIThread;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.presenter.InstallmentListPresenter;
import br.com.doubletouch.vendasup.presentation.view.InstallmentView;
import br.com.doubletouch.vendasup.presentation.view.activity.CustomerDetailsActivity;
import br.com.doubletouch.vendasup.presentation.view.activity.InstallmentActivity;
import br.com.doubletouch.vendasup.presentation.view.activity.InstallmentDetailsActivity;
import br.com.doubletouch.vendasup.presentation.view.adapter.InstallmentAdapter;
import br.com.doubletouch.vendasup.presentation.view.adapter.KratosLayoutManager;
import br.com.doubletouch.vendasup.presentation.view.fragment.customer.CustomerListFragment;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by LADAIR on 27/07/2015.
 */
public class InstallmentListFragment extends BaseFragment implements InstallmentView {

    private Navigator navigator;

    private Activity activity;

    private KratosLayoutManager kratosLayoutManager;

    private InstallmentListPresenter installmentListPresenter;

    private InstallmentAdapter installmentAdapter;

    @InjectView( R.id.rv_customers )
    RecyclerView rv_customers;

    @InjectView( R.id.rl_progress )
    RelativeLayout rl_progress;


    public static InstallmentListFragment newInstance() {
        InstallmentListFragment installmentListFragment = new InstallmentListFragment();
        Bundle argumentsBundle = new Bundle();
        installmentListFragment.setArguments( argumentsBundle );
        return installmentListFragment;
    }


    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        View fragmentView = inflater.inflate( R.layout.fragment_installment_list, container, true );
        ButterKnife.inject( this, fragmentView );

        setupUI();

        navigator = new Navigator();

        return fragmentView;
    }


    @Override
    public void onAttach( Activity activity ) {
        super.onAttach( activity );

        this.activity = activity;
    }


    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );

        setHasOptionsMenu( true );
        this.installmentListPresenter.initialize();

    }


    @Override
    public void onCreateOptionsMenu( Menu menu, MenuInflater inflater ) {
        super.onCreateOptionsMenu( menu, inflater );

        //Carrega o arquivo de menu.
        inflater.inflate( R.menu.menu_search_view, menu );

        menu.findItem( R.id.search ).setVisible( false );


    }


    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        switch ( item.getItemId() ) {

            case android.R.id.home:
                navigator.previousActivity( activity );
                break;
            default:
                super.onOptionsItemSelected( item );
        }
        return super.onOptionsItemSelected( item );
    }

    @Override
    public void initializePresenter() {

        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();

        GetInstallmentListUseCase getInstallmentListUseCase = new GetInstallmentListUseCaseImpl( threadExecutor, postExecutionThread );
        this.installmentListPresenter = new InstallmentListPresenter( this, getInstallmentListUseCase );

    }

    private void setupUI() {
        this.kratosLayoutManager = new KratosLayoutManager( getActivity() );
        this.rv_customers.setLayoutManager( kratosLayoutManager );
    }


    @Override
    public void showLoading() {
        this.rl_progress.setVisibility( View.VISIBLE );
        this.getActivity().setProgressBarIndeterminateVisibility( true );
    }

    @Override
    public void hideLoading() {
        this.rl_progress.setVisibility( View.GONE );
        this.getActivity().setProgressBarIndeterminateVisibility( false );
    }

    @Override
    public void showError( String message ) {
        this.showToastMessage( message );
    }

    @Override
    public Context getContext() {
        return getActivity().getApplicationContext();
    }


    @Override
    public void viewInstallment( Installment intInstallment ) {

        Intent intentToLaunch = InstallmentDetailsActivity.getCallingIntent( activity, intInstallment.getID(), ViewMode.VISUALIZACAO );
        startActivityForResult( intentToLaunch, 1 );
        navigator.transitionGo( activity );

    }

    @Override
    public void renderInstamentList( List<Installment> list ) {

        if ( list != null && !list.isEmpty() ) {

            if ( installmentAdapter == null ) {

                this.installmentAdapter = new InstallmentAdapter( getActivity(), list );

            } else {

                this.installmentAdapter.setInstallmentCollection( list );

            }
            this.installmentAdapter.setOnItemClickListener( onItemClickListener );
            this.rv_customers.setAdapter( installmentAdapter );
        }

    }


    private InstallmentAdapter.OnInstallmentClickListener onItemClickListener = new InstallmentAdapter.OnInstallmentClickListener() {

        @Override
        public void onInstallmentItemClicked( Installment installment ) {
            if ( InstallmentListFragment.this.installmentListPresenter != null && installment != null ) {

                InstallmentListFragment.this.installmentListPresenter.onInstallmentClicked( installment );

            }
        }

    };


    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent data ) {
        super.onActivityResult( requestCode, resultCode, data );

        this.installmentListPresenter.initialize();

    }

    @OnClick( R.id.btnAdd )
    public void addProduct() {

        Intent intentToLaunch = InstallmentDetailsActivity.getCallingIntent( activity, 0, ViewMode.INCLUSAO );
        startActivityForResult( intentToLaunch, 1 );
        navigator.transitionGo( activity );

    }

}

package br.com.doubletouch.vendasup.presentation.view.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.Installment;
import br.com.doubletouch.vendasup.data.entity.enumeration.ViewMode;
import br.com.doubletouch.vendasup.data.executor.JobExecutor;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.domain.interactor.installment.DeleteInstallmentUseCase;
import br.com.doubletouch.vendasup.domain.interactor.installment.DeleteInstallmentUseCaseImpl;
import br.com.doubletouch.vendasup.domain.interactor.installment.GetInstallmentUseCase;
import br.com.doubletouch.vendasup.domain.interactor.installment.GetInstallmentUseCaseImpl;
import br.com.doubletouch.vendasup.domain.interactor.installment.SaveInstallmentUseCase;
import br.com.doubletouch.vendasup.domain.interactor.installment.SaveInstallmentUseCaseImpl;
import br.com.doubletouch.vendasup.presentation.UIThread;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.presenter.InstallmentDetailsPresenter;
import br.com.doubletouch.vendasup.presentation.view.InstallmentDetailsView;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by LADAIR on 27/07/2015.
 */
public class InstallmentDetailsFragment  extends BaseFragment implements InstallmentDetailsView {


    private static final String ARGUMENT_KEY_INSTALLMENT_ID = "kratos.INTENT_EXTRA_PARAM_INSTALLMENT_ID";
    private static final String ARGUMENT_KEY_VIEW_MODE = "kratos.INTENT_EXTRA_PARAM_EDITION";

    private Navigator navigator;

    private Integer installmentId;

    private Activity activity;

    private ViewMode viewMode;

    private InstallmentDetailsPresenter installmentDetailsPresenter;

    @InjectView(R.id.et_installment_description)
    public EditText et_installment_description;

    @InjectView(R.id.et_installment_condition)
    public EditText et_installment_condition;

    @InjectView(R.id.tv_installment_description)
    public TextView tv_installment_description;

    @InjectView(R.id.tv_installment_condition)
    public TextView tv_installment_condition;

    Installment installment;

    private MenuItem menuDone;

    private MenuItem menuEdit;

    private MenuItem menuDelte;

    public static InstallmentDetailsFragment newInstance(Integer installmentId, ViewMode viewMode){
        InstallmentDetailsFragment customerDetailsFragment = new InstallmentDetailsFragment();
        Bundle argumentsBundle = new Bundle();
        argumentsBundle.putInt(ARGUMENT_KEY_INSTALLMENT_ID, installmentId);
        argumentsBundle.putSerializable(ARGUMENT_KEY_VIEW_MODE, viewMode);
        customerDetailsFragment.setArguments(argumentsBundle);
        return customerDetailsFragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.installmentId = getArguments().getInt(ARGUMENT_KEY_INSTALLMENT_ID);
        this.viewMode = (ViewMode) getArguments().getSerializable(ARGUMENT_KEY_VIEW_MODE);
        navigator = new Navigator();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.activity = activity;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView;

        /*if( ViewMode.EDICAO.equals( viewMode ) || ViewMode.INCLUSAO.equals( viewMode ) ) {
            fragmentView = inflater.inflate(R.layout.fragment_customer_details_form, container, false);
        } */

        fragmentView = inflater.inflate(R.layout.fragment_installment_details_form, container, false);

        ButterKnife.inject(this, fragmentView);



        return fragmentView;
    }




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        switch (viewMode){
            case EDICAO:
                this.installmentDetailsPresenter.initialize(this.installmentId);
                break;
            case VISUALIZACAO:
                this.installmentDetailsPresenter.initialize(this.installmentId);
                showTextView();
                break;
            case INCLUSAO:
                this.installmentDetailsPresenter.createNewInstallment();
                break;
            default:
                break;
        }

        setHasOptionsMenu(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                navigator.previousActivity(activity);
                break;
            case R.id.it_done:
                saveInstallment();
                break;
            case R.id.it_edit:
                menuDone.setVisible(true);
                menuEdit.setVisible(false);
                menuDelte.setVisible(false);
                showTextView();
                et_installment_description.setText(installment.getDescription());
                et_installment_condition.setText(installment.getInstallmentsDays());
                break;
            case R.id.it_delete:
                deleteInstallment();
                break;
            default:
                super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        //Carrega o arquivo de menu.
        inflater.inflate(R.menu.menu_details_view, menu);

        menuDone = menu.findItem(R.id.it_done);
        menuEdit = menu.findItem(R.id.it_edit);
        menuDelte = menu.findItem(R.id.it_delete);

        if( ViewMode.EDICAO.equals( viewMode ) || ViewMode.INCLUSAO.equals( viewMode ) ) {
            menuEdit.setVisible(false);
            menuDone.setVisible(true);
            menuDelte.setVisible(false);
        } else {
            menuEdit.setVisible(true);
            menuDone.setVisible(false);
            menuDelte.setVisible(true);
        }
    }


    private void deleteInstallment() {

        new AlertDialog.Builder(activity)
                .setMessage("Deseja excluir o parcelamento \"" + installment.getDescription() + "\" ?")
                .setCancelable(true)
                .setPositiveButton(R.string.btn_excluir, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        InstallmentDetailsFragment.this.installmentDetailsPresenter.deleteInstallment(installment);
                    }
                })
                .setNegativeButton(R.string.btn_cancelar, null)
                .show();
    }


    private void saveInstallment(){

        if(installment == null){
            InstallmentDetailsFragment.this.installmentDetailsPresenter.saveInstallment(et_installment_description.getText().toString(), et_installment_condition.getText().toString());
        } else {
            installment.setDescription(et_installment_description.getText().toString());
            installment.setInstallmentsDays( et_installment_condition.getText().toString() );
            InstallmentDetailsFragment.this.installmentDetailsPresenter.updateInstallment(installment);
        }



    }



    @Override
    public void initializePresenter() {

        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();

        GetInstallmentUseCase getInstallmentDetailsUseCase = new GetInstallmentUseCaseImpl(threadExecutor, postExecutionThread);
        SaveInstallmentUseCase saveInstallmentUseCase = new SaveInstallmentUseCaseImpl(threadExecutor, postExecutionThread);
        DeleteInstallmentUseCase deleteInstallmentUseCase = new DeleteInstallmentUseCaseImpl(threadExecutor, postExecutionThread);

        installmentDetailsPresenter = new InstallmentDetailsPresenter(this, getInstallmentDetailsUseCase, saveInstallmentUseCase, deleteInstallmentUseCase);

    }

    @Override
    public void showLoading() {


        this.activity.setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideLoading() {

        this.activity.setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override
    public Context getContext() {

        return activity.getApplicationContext();

    }

    @Override
    public void renderInstallment(Installment installment) {

        this.installment = installment;

        if (viewMode.equals(ViewMode.VISUALIZACAO)){
            tv_installment_description.setText(installment.getDescription());
            tv_installment_condition.setText(installment.getInstallmentsDays());

        } else {
            et_installment_description.setText(installment.getDescription());
            et_installment_condition.setText(installment.getInstallmentsDays());

        }


    }

    @Override
    public void installmentSaved() {
        Toast.makeText(activity, "Parcelamento salvo com sucesso", Toast.LENGTH_LONG).show();
        navigator.previousActivity(activity);
    }

    @Override
    public void installmentRemoved() {
        Toast.makeText(activity, "Parcelamento excluido com sucesso", Toast.LENGTH_LONG).show();
        navigator.previousActivity(activity);
    }


    private void showTextView(){
        ((ViewSwitcher) activity.findViewById(R.id.vs_installment_condition)).showNext();
        ((ViewSwitcher) activity.findViewById(R.id.vs_installment_description)).showNext();

    }
}

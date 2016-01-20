package br.com.doubletouch.vendasup.presentation.view.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import java.io.InputStream;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.net.GerarDados;
import br.com.doubletouch.vendasup.data.net.Integracao;
import br.com.doubletouch.vendasup.presentation.exception.ErrorMessageFactory;
import br.com.doubletouch.vendasup.presentation.view.SynchronizationView;
import br.com.doubletouch.vendasup.util.NetworkUtils;
import br.com.doubletouch.vendasup.util.StringUtil;

/**
 * Created by LADAIR on 06/05/2015.
 */
public class SynchronizationProgressDialog extends DialogFragment {

    public static final int STATUS_ERROR = 0;
    private static final int STATUS_SUCESS = 1;

    private ProgressDialog progressDialog;
    private static final String KEY_INITIAL_POSITION = "initial_position";
    private int mLastPosition = 0;
    private CustomTask customTask;

    private static final String PROGRESS_MESSAGE = "Sincronizando...";

    private SynchronizationView synchronizationView;

    private String errorMessage = "";


    public SynchronizationProgressDialog() {
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mLastPosition = savedInstanceState.getInt(KEY_INITIAL_POSITION, 0);
        }

        progressDialog = new ProgressDialog(getActivity(), getTheme());
        progressDialog.setProgressStyle( ProgressDialog.STYLE_HORIZONTAL );
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage(PROGRESS_MESSAGE);
        progressDialog.setProgressNumberFormat("");
        setCancelable(false);
        customTask = new CustomTask();
        customTask.mInitalPosition = mLastPosition;
        customTask.execute();

        return progressDialog;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (customTask != null) {
            customTask.cancel(false);
        }
        super.onCancel(dialog);
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        if (customTask != null) {
            customTask.cancel(false);
        }
        /*if(msgErro != null &&  !msgErro.trim().equals(""))
            mostrarNotificacao(msgErro);
        else
            atualizaStatusSincronizado();*/

        super.onDismiss(dialog);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INITIAL_POSITION, mLastPosition);
    }




    private final class CustomTask extends AsyncTask<Void, Integer, Integer> {
        private int mInitalPosition = 0;

        @Override
        protected void onPostExecute(Integer status) {

            switch (status){
                case STATUS_ERROR:
                    synchronizationView.onErrorSynchronization(errorMessage);
                    break;
                case STATUS_SUCESS:
                    synchronizationView.onSuccessSynchronization();
                    break;

            }

            super.onPostExecute(status);
        }

        @Override
        protected Integer doInBackground(Void... params) {


            Integracao integracao = new Integracao();

            GerarDados gerarDados = new GerarDados();

            try {







                if( !NetworkUtils.isThereInternetConnection()){
                    throw new Exception(getResources().getString(R.string.notificacao_sem_conexao));
                }

                Integer organizationId = VendasUp.getBranchOffice().getOrganization().getOrganizationID();


                integracao.receberLicencaPorUsuario( VendasUp.getUser().getUserID() );
                publishProgress( 10 );
                publishProgress( 20 );
                InputStream isProduto = getResources().openRawResource( R.raw.produto );
                gerarDados.receberProdutos( StringUtil.inputStreamToString( isProduto ) );
                publishProgress(30);
                InputStream isCliente = getResources().openRawResource( R.raw.produto );
                gerarDados.receberClientes( StringUtil.inputStreamToString( isCliente ) );
                publishProgress(40);
                integracao.receberPromocoes(organizationId);
                publishProgress(50);
                integracao.receberFilial(organizationId);
                publishProgress(60);
                integracao.receberEmpresa(organizationId);
                publishProgress(70);
                integracao.receberUsuarios(organizationId);
                publishProgress(80);
                integracao.receberEmpresa(organizationId);
                publishProgress(85);
                integracao.receberMetas(organizationId);
                publishProgress(90);
                integracao.receberTabelasPrecos(organizationId);
                publishProgress(95);
                integracao.receberParcelamentos(organizationId);
                publishProgress(100);

                mLastPosition = 0;


                if (getDialog() != null && getDialog().isShowing()) {
                    dismiss();
                }


                return STATUS_SUCESS;

            } catch (Exception e) {

                errorMessage = ErrorMessageFactory.create(synchronizationView.getContext(), e);
                Log.e(VendasUp.APP_TAG, e.getMessage(), e);

                dismiss();

                return STATUS_ERROR;
            }


        }



        @Override
        protected void onProgressUpdate (Integer...values){
            if (progressDialog != null) {
                progressDialog.setProgress(mLastPosition = values[0]);
            }
        }

    }


    public void setSynchronizationView( SynchronizationView synchronizationView ) {
        this.synchronizationView = synchronizationView;
    }


}

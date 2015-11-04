package br.com.doubletouch.vendasup.presentation.view.dialog;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.entity.enumeration.SignType;
import br.com.doubletouch.vendasup.data.net.Integracao;
import br.com.doubletouch.vendasup.presentation.exception.ErrorMessageFactory;
import br.com.doubletouch.vendasup.presentation.view.ConfirmationView;
import br.com.doubletouch.vendasup.presentation.view.SigninView;
import br.com.doubletouch.vendasup.util.NetworkUtils;

/**
 * Created by LADAIR on 06/05/2015.
 */
public class ConfirmacaoCadastroDialog extends DialogFragment {

    public static final int STATUS_ERROR = 0;
    private static final int STATUS_SUCESS = 1;

    private ProgressDialog progressDialog;
    private CustomTask customTask;

    private static final String PROGRESS_MESSAGE = "Aguarde...";

    private SigninView confirmationView;

    private String errorMessage = "";

    private String email;

    private String code ;



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
        }

        progressDialog = new ProgressDialog(getActivity(), getTheme());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage(PROGRESS_MESSAGE);
        progressDialog.setProgressNumberFormat("");
        setCancelable(false);
        customTask = new CustomTask();
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

        super.onDismiss(dialog);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putInt(KEY_INITIAL_POSITION, mLastPosition);
    }




    private final class CustomTask extends AsyncTask<Void, Integer, Integer> {
        private int mInitalPosition = 0;

        @Override
        protected void onPostExecute(Integer status) {

            switch (status){
                case STATUS_ERROR:
                    confirmationView.showError(errorMessage);
                    break;
                case STATUS_SUCESS:
                    confirmationView.onSuccessSynchronization();
                    break;

            }

            super.onPostExecute(status);
        }

        @Override
        protected Integer doInBackground(Void... params) {

            Integer status = STATUS_SUCESS;

            Integracao integracao = new Integracao();

            try {


                if( !NetworkUtils.isThereInternetConnection()){
                    throw new Exception(getResources().getString(R.string.notificacao_sem_conexao));
                }



                integracao.confirmation( email );

                status =  STATUS_SUCESS;


                if (getDialog() != null && getDialog().isShowing()) {
                    dismiss();
                }

                return  status;

            } catch (Exception e) {

                Log.e(VendasUp.APP_TAG, e.getMessage(), e);
                errorMessage = ErrorMessageFactory.create( confirmationView.getContext(), e );


                dismiss();

                return STATUS_ERROR;
            }


        }



        @Override
        protected void onProgressUpdate (Integer...values){

        }

    }


    public void setSynchronizationView( SigninView confirmationView ) {
        this.confirmationView = confirmationView;
    }



    public void setSigninAttributes(String email){
        this.email = email;

    }
}

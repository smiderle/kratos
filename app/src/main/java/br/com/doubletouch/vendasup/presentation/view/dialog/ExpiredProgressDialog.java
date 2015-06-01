package br.com.doubletouch.vendasup.presentation.view.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.net.Integracao;
import br.com.doubletouch.vendasup.presentation.exception.ErrorMessageFactory;
import br.com.doubletouch.vendasup.presentation.view.ExpiredView;
import br.com.doubletouch.vendasup.presentation.view.SynchronizationView;

/**
 * Created by LADAIR on 06/05/2015.
 */
public class ExpiredProgressDialog extends DialogFragment {

    public static final int STATUS_ERROR = 0;
    private static final int STATUS_SUCESS = 1;

    private ProgressDialog progressDialog;
    private static final String KEY_INITIAL_POSITION = "initial_position";
    private int mLastPosition = 0;
    private CustomTask customTask;

    private static final String PROGRESS_MESSAGE = "Sincronizando...";

    private ExpiredView expiredView;

    private String errorMessage = "";


    public ExpiredProgressDialog() {
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
                    expiredView.onErrorSynchronization(errorMessage);
                    break;
                case STATUS_SUCESS:
                    expiredView.onSuccessSynchronization();
                    break;

            }

            super.onPostExecute(status);
        }

        @Override
        protected Integer doInBackground(Void... params) {


            Integracao integracao = new Integracao();

            try {

                Integer organizationId = VendasUp.getBranchOffice().getOrganization().getOrganizationID();

                publishProgress(5);
                integracao.receberLicencaPorUsuario(VendasUp.getUser().getUserID());
                publishProgress(100);

                mLastPosition = 0;


                if (getDialog() != null && getDialog().isShowing()) {
                    dismiss();
                }


                return STATUS_SUCESS;

            } catch (Exception e) {

                errorMessage = ErrorMessageFactory.create(expiredView.getContext(), e);
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


    public void setExpiredView( ExpiredView expiredView ) {
        this.expiredView = expiredView;
    }


}

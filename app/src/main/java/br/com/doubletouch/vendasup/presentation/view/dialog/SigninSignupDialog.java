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
import br.com.doubletouch.vendasup.data.entity.User;
import br.com.doubletouch.vendasup.data.entity.enumeration.SignType;
import br.com.doubletouch.vendasup.data.net.Integracao;
import br.com.doubletouch.vendasup.presentation.exception.ErrorMessageFactory;
import br.com.doubletouch.vendasup.presentation.view.SigninView;
import br.com.doubletouch.vendasup.presentation.view.SynchronizationView;

/**
 * Created by LADAIR on 06/05/2015.
 */
public class SigninSignupDialog extends DialogFragment {


    public static final int STATUS_ERROR = 0;
    private static final int STATUS_SUCESS = 1;

    private ProgressDialog progressDialog;
    private static final String KEY_INITIAL_POSITION = "initial_position";
    private int mLastPosition = 0;
    private CustomTask customTask;

    private static final String PROGRESS_MESSAGE = "Sincronizando...";

    private SigninView signinView;

    private String errorMessage = "";

    private String email;

    private String password;

    private String organizationName;

    private String userName;

    private SignType signType;

    private String codigo;



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
                    signinView.showError(errorMessage);
                    break;
                case STATUS_SUCESS:
                    signinView.onSuccessSynchronization();
                    break;

            }

            super.onPostExecute(status);
        }

        @Override
        protected Integer doInBackground(Void... params) {

            Integer status = STATUS_SUCESS;

            Integracao integracao = new Integracao();

            try {

                User user = null;

                if( SignType.SIGNIN.equals( signType ) ){

                    user = integracao.getUserByEmail(email, password);

                } else {

                    //Ira validar o código antes de gerar a carga. Se o código for invalido, ira lançar uma exception.
                    integracao.validaCodigo( email, codigo );

                   user = integracao.generateNewUser(organizationName, userName, email, password);
                   VendasUp.setUser(user);

                }



                if( user != null ) {

                    integracao.receberLicencaPorUsuario( user.getUserID() );
                    publishProgress(5);
                    Integer organizationId = user.getOrganizationID();
                    publishProgress(10);
                    integracao.receberProdutos(organizationId);
                    publishProgress(30);
                    integracao.receberClientes(organizationId);
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


                    status =  STATUS_SUCESS;

                } else {

                    status =  STATUS_ERROR;

                }

                mLastPosition = 0;


                if (getDialog() != null && getDialog().isShowing()) {
                    dismiss();
                }




                return  status;

            } catch (Exception e) {

                Log.e(VendasUp.APP_TAG, e.getMessage(), e);
                errorMessage = ErrorMessageFactory.create( signinView.getContext(), e );


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

    public void setSynchronizationView( SigninView signinView ) {
        this.signinView = signinView;
    }

    public void setSigninAttributes(String organizationName, String userName, String email, String password, String codigo){
        this.userName = userName;
        this.organizationName = organizationName;
        this.email = email;
        this.password = password;
        this.codigo = codigo;
    }

    public void setSignType(SignType signType){
        this.signType = signType;
    }
}

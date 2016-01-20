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
import br.com.doubletouch.vendasup.data.net.GerarDados;
import br.com.doubletouch.vendasup.presentation.exception.ErrorMessageFactory;
import br.com.doubletouch.vendasup.presentation.view.SigninView;
import br.com.doubletouch.vendasup.util.StringUtil;

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
    public Dialog onCreateDialog( Bundle savedInstanceState ) {
        if ( savedInstanceState != null ) {
            mLastPosition = savedInstanceState.getInt( KEY_INITIAL_POSITION, 0 );
        }

        progressDialog = new ProgressDialog( getActivity(), getTheme() );
        progressDialog.setProgressStyle( ProgressDialog.STYLE_HORIZONTAL );
        progressDialog.setIndeterminate( false );
        progressDialog.setMessage( PROGRESS_MESSAGE );
        progressDialog.setProgressNumberFormat( "" );
        setCancelable( false );
        customTask = new CustomTask();
        customTask.mInitalPosition = mLastPosition;
        customTask.execute();

        return progressDialog;
    }

    @Override
    public void onCancel( DialogInterface dialog ) {
        if ( customTask != null ) {
            customTask.cancel( false );
        }
        super.onCancel( dialog );
    }


    @Override
    public void onDismiss( DialogInterface dialog ) {
        if ( customTask != null ) {
            customTask.cancel( false );
        }
        /*if(msgErro != null &&  !msgErro.trim().equals(""))
            mostrarNotificacao(msgErro);
        else
            atualizaStatusSincronizado();*/

        super.onDismiss( dialog );
    }

    @Override
    public void onSaveInstanceState( Bundle outState ) {
        super.onSaveInstanceState( outState );
        outState.putInt( KEY_INITIAL_POSITION, mLastPosition );
    }


    private final class CustomTask extends AsyncTask< Void, Integer, Integer > {
        private int mInitalPosition = 0;

        @Override
        protected void onPostExecute( Integer status ) {

            switch ( status ) {
                case STATUS_ERROR:
                    signinView.showError( errorMessage );
                    break;
                case STATUS_SUCESS:
                    signinView.onSuccessSynchronization();
                    break;

            }

            super.onPostExecute( status );
        }

        @Override
        protected Integer doInBackground( Void... params ) {

            Integer status = STATUS_SUCESS;

            GerarDados gerarDados = new GerarDados();

            try {

                publishProgress( 10 );
                gerarDados.receberEmpresa( StringUtil.inputStreamToString( getResources().openRawResource( R.raw.empresa ) ) );
                publishProgress( 20 );
                gerarDados.receberProdutos( StringUtil.inputStreamToString( getResources().openRawResource( R.raw.produto ) ) );
                publishProgress( 30 );
                gerarDados.receberClientes( StringUtil.inputStreamToString( getResources().openRawResource( R.raw.cliente ) ) );
                publishProgress( 50 );
                gerarDados.receberFilial( StringUtil.inputStreamToString( getResources().openRawResource( R.raw.filial ) ) );
                publishProgress( 70 );
                gerarDados.receberUsuarioEmpresa( StringUtil.inputStreamToString( getResources().openRawResource( R.raw.usuario_empresa ) ) );
                publishProgress( 80 );
                gerarDados.receberTabelaPreco( StringUtil.inputStreamToString( getResources().openRawResource( R.raw.tabela_preco ) ) );
                publishProgress( 95 );
                gerarDados.receberParcelamento( StringUtil.inputStreamToString( getResources().openRawResource( R.raw.parcelamento ) ) );
                publishProgress( 100 );


                status = STATUS_SUCESS;

                mLastPosition = 0;


                if ( getDialog() != null && getDialog().isShowing() ) {
                    dismiss();
                }


                return status;

            } catch ( Exception e ) {

                Log.e( VendasUp.APP_TAG, e.getMessage(), e );
                errorMessage = ErrorMessageFactory.create( signinView.getContext(), e );


                dismiss();

                return STATUS_ERROR;
            }


        }


        @Override
        protected void onProgressUpdate( Integer... values ) {
            if ( progressDialog != null ) {
                progressDialog.setProgress( mLastPosition = values[ 0 ] );
            }
        }

    }

    public void setSynchronizationView( SigninView signinView ) {
        this.signinView = signinView;
    }

    public void setSigninAttributes( String organizationName, String userName, String email, String password, String codigo ) {
        this.userName = userName;
        this.organizationName = organizationName;
        this.email = email;
        this.password = password;
        this.codigo = codigo;
    }

    public void setSignType( SignType signType ) {
        this.signType = signType;
    }
}

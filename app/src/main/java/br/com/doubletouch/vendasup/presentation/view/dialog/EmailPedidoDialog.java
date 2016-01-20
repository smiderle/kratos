package br.com.doubletouch.vendasup.presentation.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.entity.Order;
import br.com.doubletouch.vendasup.presentation.exception.ErrorMessageFactory;
import br.com.doubletouch.vendasup.presentation.view.EmailPedidoView;

/**
 * Created by ladairsmiderle on 09/11/2015.
 */
public class EmailPedidoDialog extends DialogFragment {


    private Activity context;


    Order order;

    private String errorMessage = "";

    private EmailPedidoView emailPedidoView;

    private SyncTask syncTask;

    public void setContext( Activity activity ) {
        this.context = activity;
    }


    public void setOrder( Order order ) {
        this.order = order;

    }

    public void setEmailPedidoView( EmailPedidoView emailPedidoView ) {
        this.emailPedidoView = emailPedidoView;
    }


    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState ) {

        LayoutInflater inflate = context.getLayoutInflater();
        final View view = inflate.inflate( R.layout.dialog_email, null );

        final EditText viewById = ( EditText ) view.findViewById( R.id.ed_email );
        viewById.setText( VendasUp.getUser().getEmail() );


        AlertDialog.Builder builder = new AlertDialog.Builder( context )
                .setView( view );
        builder.setTitle( "Email" );
        builder.setMessage( "Inclua os emails que irão receber o pedido." );
        builder.setPositiveButton( "Enviar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick( DialogInterface dialog, int which ) {

                try {

                    syncTask = new SyncTask();
                    syncTask.execute( viewById.getText().toString() );

                } catch ( Exception e ) {
                    errorMessage = ErrorMessageFactory.create( emailPedidoView.getContext(), e );
                    e.printStackTrace();
                }

            }
        } );
        builder.setNegativeButton( "Cancel", null );


        return builder.create();

    }


    private final class SyncTask extends AsyncTask< String, Integer, Boolean > {


        @Override
        protected void onProgressUpdate( Integer... values ) {

        }

        @Override
        protected void onPostExecute( Boolean status ) {

            if ( status ) {
                emailPedidoView.emailEnviado();
                ;
            } else {
                emailPedidoView.showError( errorMessage );
            }

            super.onPostExecute( status );
        }


        @Override
        protected Boolean doInBackground( String... params ) {

            Boolean sucesso = true;

            try {
                String email = VendasUp.getUser().getEmail();
                if ( params[ 0 ] != null && !params[ 0 ].trim().equals( "" ) ) {
                    email = params[ 0 ];
                }


                //Enviar email do pedido aqui
            } catch ( Exception e ) {
                sucesso = false;
                errorMessage = e.getMessage();
                e.printStackTrace();
            }

            return sucesso;
        }
    }


}
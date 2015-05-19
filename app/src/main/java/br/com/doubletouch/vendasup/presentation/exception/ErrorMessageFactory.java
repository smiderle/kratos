package br.com.doubletouch.vendasup.presentation.exception;

import android.content.Context;
import android.util.Log;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.exception.SyncronizationException;

/**
 * Factory usado para criar mensagem de erro baseada na exception.
 * Created by LADAIR on 11/02/2015.
 */
public class ErrorMessageFactory {

    public ErrorMessageFactory() {
        //empty
    }

    /**
     * Cria uma string representando a mensagem de erro.
     * @param context Contexto
     * @param exception Exception utilizada pela condição para retornar a mensagem de erro.
     * @return
     */
    public static String create(Context context, Exception exception){

        String message = getHumanMessage( exception.getMessage() );



        if( message == null ){

            //context.getString(R.string.exception_message_syncronization);

        }

        Log.e(VendasUp.APP_TAG, exception.getMessage(), exception);

        return message;
    }



    private static String getHumanMessage(String message){
        String retorno = message;

        if( message.toLowerCase().contains("timed out") || message.toLowerCase().contains("refused")){

            retorno = "Opss. O servidor não esta respondendo. Tente novamente em alguns instantes.";

        }

        return retorno;
    }
}

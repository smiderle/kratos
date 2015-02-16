package br.com.doubletouch.vendasup.presentation.exception;

import android.content.Context;

import br.com.doubletouch.vendasup.R;
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
        String message = context.getString(R.string.exception_message_generic);

        if(exception instanceof SyncronizationException){
            message = context.getString(R.string.exception_message_syncronization);
        }

        return message;
    }
}

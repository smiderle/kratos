package br.com.doubletouch.vendasup.presentation.presenter;

/**
 * Interface representando o Presenter no padrão MVP
 * Created by LADAIR on 11/02/2015.
 */
public interface Presenter {

    /**
     * Metodo que controla o cilco de vida da view. Esse deve ser chamado na view (Activity or Fragment) no metodo onResume().
     */
    void resume();

    /**
     * Metodo que controla o cilco de vida da view. Esse deve ser chamado na view (Activity or Fragment) no metodo onPause().
     */
    void pause();
}

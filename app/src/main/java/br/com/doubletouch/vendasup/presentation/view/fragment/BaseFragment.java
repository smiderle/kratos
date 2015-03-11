package br.com.doubletouch.vendasup.presentation.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Classe Base {@link android.app.Fragment} para todos os fragment nessa aplicação.
 * Created by LADAIR on 12/02/2015.
 */
public abstract class BaseFragment extends Fragment {


    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        initializePresenter();
    }

    /**
     * Initializes the {@link br.com.doubletouch.vendasup.presentation.presenter.Presenter}
     * for this fragment in a MVP pattern used to architect the application presentation layer.
     */
    public abstract void initializePresenter();

    /**
     * Shows a {@link android.widget.Toast} message.
     *
     * @param message An string representing a message to be shown.
     */
    protected void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}

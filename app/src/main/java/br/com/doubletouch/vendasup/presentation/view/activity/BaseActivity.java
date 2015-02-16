package br.com.doubletouch.vendasup.presentation.view.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

/**
 * Base {@link android.app.Activity} class for every Activity in this application.
 * Created by LADAIR on 11/02/2015.
 */
public abstract class BaseActivity extends ActionBarActivity {

    /**
     * Adds a {@link Fragment} to this activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment The fragment to be added.
     */
    protected void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }
}

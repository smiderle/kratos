package br.com.doubletouch.vendasup.presentation.view;

import android.view.View;

/**
 * Created by LADAIR on 23/02/2015.
 */
public class ViewHelper {

    public static void setTranslationY(View view, float translationY) {
        view.setTranslationY(translationY);
    }

    public static float getTranslationY(View view) {
        return view.getTranslationY();
    }
}

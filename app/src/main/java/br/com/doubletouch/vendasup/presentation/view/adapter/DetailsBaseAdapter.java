package br.com.doubletouch.vendasup.presentation.view.adapter;

import android.widget.BaseAdapter;

/**
 * Adapter base para ser utilizado pelos adapters que estão dentro dos fragments do detalhes do cliente.
 * Created by LADAIR on 21/02/2015.
 */
public abstract class DetailsBaseAdapter extends BaseAdapter {

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
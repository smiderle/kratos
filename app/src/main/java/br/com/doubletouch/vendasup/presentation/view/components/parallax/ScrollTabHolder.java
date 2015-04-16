package br.com.doubletouch.vendasup.presentation.view.components.parallax;
import android.widget.AbsListView;

import br.com.doubletouch.vendasup.data.entity.Customer;

public interface ScrollTabHolder {

    void adjustScroll(int scrollHeight);

    void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition);

    void setAtributes(Object object);

    void tabSelected();
}
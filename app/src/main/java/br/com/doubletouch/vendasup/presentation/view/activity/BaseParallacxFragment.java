package br.com.doubletouch.vendasup.presentation.view.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AbsListView;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.presentation.view.fragment.BaseFragment;

/**
 * Created by LADAIR on 23/02/2015.
 */
public abstract class BaseParallacxFragment extends BaseFragment {

    private int mActionBarHeight;
    private int mMinHeaderHeight;
    private int mHeaderHeight;
    protected int mMinHeaderTranslation;

    private TypedValue mTypedValue = new TypedValue();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMinHeaderHeight = getResources().getDimensionPixelSize(R.dimen.parallax_min_header_height);
        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.parallax_height);
        mMinHeaderTranslation = -mMinHeaderHeight + getActionBarHeight();

    }



    public int getScrollY(AbsListView view) {
        View c = view.getChildAt(0);
        if (c == null) {
            return 0;
        }

        int firstVisiblePosition = view.getFirstVisiblePosition();
        int top = c.getTop();

        int headerHeight = 0;
        if (firstVisiblePosition >= 1) {
            headerHeight = mHeaderHeight;
        }

        return -top + firstVisiblePosition * c.getHeight() + headerHeight;
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public int getActionBarHeight() {
        if (mActionBarHeight != 0) {
            return mActionBarHeight;
        }

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB){
            getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, mTypedValue, true);
        }else{
            getActivity().getTheme().resolveAttribute(R.attr.actionBarSize, mTypedValue, true);
        }
        /*
         * Foi comentado essa linha porque as tabs estava ficando  ~= 50 pixes a baixo o actionbar
         * então estou retornando 0, ai fica certo
         */

        //mActionBarHeight = TypedValue.complexToDimensionPixelSize(mTypedValue.data, getResources().getDisplayMetrics());

        //return mActionBarHeight;
        return 0;
    }
}

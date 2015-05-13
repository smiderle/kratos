package br.com.doubletouch.vendasup.presentation.view.fragment.order;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.Order;
import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.data.entity.enumeration.ViewMode;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.view.ViewHelper;
import br.com.doubletouch.vendasup.presentation.view.activity.BaseParallacxFragment;
import br.com.doubletouch.vendasup.presentation.view.components.PagerSlidingTabStrip;
import br.com.doubletouch.vendasup.presentation.view.components.parallax.ScrollTabHolder;
import br.com.doubletouch.vendasup.presentation.view.components.parallax.ScrollTabHolderFragment;
import br.com.doubletouch.vendasup.presentation.view.fragment.BaseFragment;
import br.com.doubletouch.vendasup.presentation.view.fragment.product.ProductDetailsMainFragment;
import br.com.doubletouch.vendasup.presentation.view.fragment.product.ProductDetailsPriceTableFragment;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

/**
 * Created by LADAIR on 06/04/2015.
 */
public class OrderProductFragment extends BaseParallacxFragment  {

    private final int PAGES_NUMER = 2;

    private static final String ARGUMENT_KEY_VIEW_MODE = "kratos.INTENT_EXTRA_PARAM_EDITION";


    private PageAdaper pageAdaper;


    @InjectView(R.id.vp_header)
    public ViewPager vp_header;

    @Optional
    @InjectView(R.id.fl_header)
    public View fl_header;

    private Navigator navigator;
    private Activity activity;

    @InjectView(R.id.tabs)
    public PagerSlidingTabStrip mPagerSlidingTabStrip;

    private ViewMode viewMode;

    public static OrderProductFragment newInstance(ViewMode viewMode){
        OrderProductFragment orderProductFragment = new OrderProductFragment();
        Bundle bundle = new Bundle();
        orderProductFragment.setArguments(bundle);
        bundle.putSerializable(ARGUMENT_KEY_VIEW_MODE, viewMode);
        return orderProductFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigator = new Navigator();
        this.viewMode = (ViewMode) getArguments().getSerializable(ARGUMENT_KEY_VIEW_MODE);
        //order = (Order) getArguments().getSerializable(ARGS_ORDER);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView;

        fragmentView = inflater.inflate(R.layout.fragment_customer_details_form, container, false);

        ButterKnife.inject(this, fragmentView);

        vp_header.setOffscreenPageLimit(PAGES_NUMER);
        pageAdaper = new PageAdaper(getFragmentManager());
        pageAdaper.setTabHolderScrollingContent(new OnScrollTabHolderListner());

        vp_header.setAdapter(pageAdaper);

        mPagerSlidingTabStrip.setViewPager(vp_header);
        mPagerSlidingTabStrip.setOnPageChangeListener(new OnPageChangeListener());
        pageAdaper.notifyDataSetChanged();



        return fragmentView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case android.R.id.home:
                navigator.previousActivity(activity);
                break;
            default:
                super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }





    @Override
    public void initializePresenter() {


    }

    public class OnScrollTabHolderListner implements ScrollTabHolder {
        @Override
        public void adjustScroll(int scrollHeight) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition) {

        }

        @Override
        public void setAtributes(Object object) {

        }

        @Override
        public void tabSelected() {

        }
    }


    /**
     * Page adapter que ira manipular as fragments das tabs
     */
    private class PageAdaper extends FragmentStatePagerAdapter {

        private Product product;
        private SparseArrayCompat<ScrollTabHolder> scrollTabHolder;
        private ScrollTabHolder listener;




        private final String[] TITLES;

        public void setTabHolderScrollingContent(ScrollTabHolder listener){
            this.listener = listener;
        }

        private PageAdaper(FragmentManager fm) {
            super(fm);
            this.scrollTabHolder = new SparseArrayCompat<>();

            if( ViewMode.VISUALIZACAO.equals( viewMode ) ){
                TITLES = new String[]{"Carrinho"};
            } else {
                TITLES = new String[]{"Produtos", "Carrinho"};
            }

        }

        @Override
        public int getItemPosition( Object object ) {

            return POSITION_NONE;

        }

        @Override
        public Fragment getItem(int position) {

            ScrollTabHolderFragment fragment = null;

            fragment = (ScrollTabHolderFragment) OrderProductListFragment.newInstance(position, OrderFragment.order, viewMode);
            /*if( ViewMode.VISUALIZACAO.equals( viewMode ) ){

                if(position == 0){

                    fragment = (ScrollTabHolderFragment) OrderProductListFragment.newInstance(position, OrderFragment.order, viewMode);

                } else if(position == 1){

                    fragment = (ScrollTabHolderFragment) OrderProductListFragment.newInstance(position, OrderFragment.order, viewMode);

                }

            } else {

                fragment = (ScrollTabHolderFragment) OrderProductListFragment.newInstance(position, OrderFragment.order, viewMode);

            }*/

            scrollTabHolder.put(position, fragment);

            if(listener != null){

                fragment.setScrollTabHolder(listener);

            }

            return fragment;
        }


        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        public SparseArrayCompat<ScrollTabHolder> getScrollTabHolders() {
            return scrollTabHolder;
        }
    }


    /**
     * Listner para as tabs
     */
    public class OnPageChangeListener implements  ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            ScrollTabHolder fragment = null;

            SparseArrayCompat<ScrollTabHolder> scrollTabHolders = pageAdaper.getScrollTabHolders();

            ScrollTabHolder scrollTabHolder = scrollTabHolders.get(position);
            scrollTabHolder.tabSelected();


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

    }


}

package br.com.doubletouch.vendasup.presentation.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;

import br.com.doubletouch.vendasup.presentation.view.ViewHelper;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.presentation.view.components.PagerSlidingTabStrip;
import br.com.doubletouch.vendasup.presentation.view.components.parallax.ScrollTabHolder;
import br.com.doubletouch.vendasup.presentation.view.components.parallax.ScrollTabHolderFragment;
import br.com.doubletouch.vendasup.presentation.view.fragment.CustomerDetailsPersonalFragment;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by LADAIR on 21/02/2015.
 */
public class CustomerDetailsActivity extends BaseParallaxActivity {

    private final int PAGES_NUMER = 4;

    @InjectView(R.id.fl_header)
    public View fl_header;

    @InjectView(R.id.vp_header)
    public ViewPager vp_header;

    @InjectView(R.id.iv_header)
    public ImageView iv_header;

    @InjectView(R.id.tabs)
    public PagerSlidingTabStrip mPagerSlidingTabStrip;

    private PageAdaper pageAdaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.component_parallax_header);
        ButterKnife.inject(this, CustomerDetailsActivity.this);

        vp_header.setOffscreenPageLimit(PAGES_NUMER);
        pageAdaper = new PageAdaper(getSupportFragmentManager());
        pageAdaper.setTabHolderScrollingContent(new OnScrollTabHolderListner());

        vp_header.setAdapter(pageAdaper);
        mPagerSlidingTabStrip.setViewPager(vp_header);
        mPagerSlidingTabStrip.setOnPageChangeListener(new OnPageChangeListener());

    }


    /**
     * Page adapter que ira manipular as fragments das tabs
     */
    private class PageAdaper extends FragmentPagerAdapter {

        private SparseArrayCompat<ScrollTabHolder> scrollTabHolder;
        private ScrollTabHolder listener;

        private final String[] TITLES = {"Principal", "Contato", "Endereço", "Titulos", "Peddidos"};

        public void setTabHolderScrollingContent(ScrollTabHolder listener){
            this.listener = listener;
        }

        private PageAdaper(FragmentManager fm) {
            super(fm);
            this.scrollTabHolder = new SparseArrayCompat<>();
        }

        @Override
        public Fragment getItem(int position) {
            ScrollTabHolderFragment fragment = (ScrollTabHolderFragment) CustomerDetailsPersonalFragment.newInstance(position);
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


    public class OnScrollTabHolderListner implements ScrollTabHolder {
        @Override
        public void adjustScroll(int scrollHeight) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition) {
            if (vp_header.getCurrentItem() == pagePosition) {
                int scrollY = getScrollY(view);
                ViewHelper.setTranslationY(fl_header, Math.max(-scrollY, mMinHeaderTranslation));
            }
        }
    }

    /**
     * Listner para as tabs
     */
    public class OnPageChangeListener implements  ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            SparseArrayCompat<ScrollTabHolder> scrollTabHolders = pageAdaper.getScrollTabHolders();
            ScrollTabHolder currentHolder = scrollTabHolders.valueAt(position);

            currentHolder.adjustScroll((int) (fl_header.getHeight() + ViewHelper.getTranslationY(fl_header)));

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

    }


}

package br.com.doubletouch.vendasup.presentation.view.fragment.product;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.data.entity.enumeration.ViewMode;
import br.com.doubletouch.vendasup.data.executor.JobExecutor;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.domain.interactor.product.GetProductDetailsUseCase;
import br.com.doubletouch.vendasup.domain.interactor.product.GetProductDetailsUseCaseImpl;
import br.com.doubletouch.vendasup.domain.interactor.product.SaveProductUseCase;
import br.com.doubletouch.vendasup.domain.interactor.product.SaveProductUseCaseImpl;
import br.com.doubletouch.vendasup.presentation.UIThread;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.presenter.ProductDetailsPresenter;
import br.com.doubletouch.vendasup.presentation.view.ProductDetailsView;
import br.com.doubletouch.vendasup.presentation.view.ViewHelper;
import br.com.doubletouch.vendasup.presentation.view.activity.BaseParallacxFragment;
import br.com.doubletouch.vendasup.presentation.view.activity.ProductDetailsActivity;
import br.com.doubletouch.vendasup.presentation.view.components.PagerSlidingTabStrip;
import br.com.doubletouch.vendasup.presentation.view.components.parallax.ScrollTabHolder;
import br.com.doubletouch.vendasup.presentation.view.components.parallax.ScrollTabHolderFragment;
import br.com.doubletouch.vendasup.util.image.ImageLoader;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

/**
 * Created by LADAIR on 15/02/2015.
 */
public class ProductDetailsFragment  extends BaseParallacxFragment implements ProductDetailsView {

    private final int PAGES_NUMER = 2;

    private static final String ARGUMENT_KEY_PRODUCT_ID = "kratos.ARGUMENT_PRODUCT_ID";
    private static final String ARGUMENT_VIEW_MODE = "kratos.INTENT_EXTRA_PARAM_EDITION";
    private static final String ARGUMENT_PRODUCT = "kratos.ARGS_PRODUCT";


    private Integer productId;
    private ProductDetailsPresenter productDetailsPresenter;

    @Optional
    @InjectView(R.id.iv_header)
    public ImageView iv_header;

    @Optional
    @InjectView(R.id.fl_header)
    public View fl_header;

    @InjectView(R.id.vp_header)
    public ViewPager vp_header;

    private PageAdaper pageAdaper;

    @InjectView(R.id.rl_progress)
    RelativeLayout rl_progress;


    private ImageLoader imageLoader;

    private Product product;

    @InjectView(R.id.tabs)
    public PagerSlidingTabStrip mPagerSlidingTabStrip;


    private Activity activity;

    MenuItem menuEdit;

    private Navigator navigator;

    public ProductDetailsFragment(){
        super();
    }

    private ViewMode viewMode;

    public static ProductDetailsFragment newInstance(int productId, ViewMode viewMode){
        ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
        Bundle argumentsBundle = new Bundle();
        argumentsBundle.putInt(ARGUMENT_KEY_PRODUCT_ID, productId);
        argumentsBundle.putSerializable(ARGUMENT_VIEW_MODE, viewMode);
        productDetailsFragment.setArguments(argumentsBundle);
        return productDetailsFragment;
    }


    @Override
      public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.productId = getArguments().getInt(ARGUMENT_KEY_PRODUCT_ID);
        this.viewMode = (ViewMode) getArguments().getSerializable(ARGUMENT_VIEW_MODE);
        imageLoader = new ImageLoader(getContext());
        navigator = new Navigator();

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.activity = activity;


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView;

        if(ViewMode.EDICAO.equals(viewMode) || ViewMode.INCLUSAO.equals(viewMode)){
            fragmentView = inflater.inflate(R.layout.fragment_customer_details_form, container, false);
        } else {
            fragmentView = inflater.inflate(R.layout.fragment_customer_details, container, false);
        }

        ButterKnife.inject(this, fragmentView);


        return fragmentView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        switch (viewMode){
            case EDICAO:
                this.productDetailsPresenter.initialize(this.productId);
                break;
            case VISUALIZACAO:
                this.productDetailsPresenter.initialize(this.productId);
                break;
            case INCLUSAO:
                this.productDetailsPresenter.createNewProduct();

                break;
            default:
                break;
        }

        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        //Carrega o arquivo de menu.
        inflater.inflate(R.menu.menu_details_view, menu);

        if(ViewMode.EDICAO.equals(viewMode) || ViewMode.INCLUSAO.equals(viewMode)){
            menu.findItem(R.id.it_edit).setVisible(false);
            menuEdit = menu.findItem(R.id.it_edit).setVisible(false);
            menu.findItem(R.id.it_done).setVisible(true);
        } else {
            menu.findItem(R.id.it_edit).setVisible(true);
            menuEdit = menu.findItem(R.id.it_edit).setVisible(true);
            menu.findItem(R.id.it_done).setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                navigator.previousActivity(activity);
                break;
            case R.id.it_edit:
                Intent it = ProductDetailsActivity.getCallingIntent(activity, productId, ViewMode.EDICAO);
                startActivityForResult (it, 1 );
                navigator.transitionGo(activity);
                break;
            case R.id.it_done:
                saveProduct();
                break;
            case R.id.it_delete:
                deleteProduct();
                break;
            default:
                super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteProduct() {
        new AlertDialog.Builder(activity)
                .setMessage("Deseja excluir o produto \"" + product.getDescription() + "\" ?")
                .setCancelable(true)
                .setPositiveButton(R.string.btn_excluir, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ProductDetailsFragment.this.productDetailsPresenter.deleteProduct(product);
                    }
                })
                .setNegativeButton(R.string.btn_cancelar, null)
                .show();

    }

    /**
     * Salva o produto.
     */
    private void saveProduct() {

        //Retorna o usuário salvo para atualizar a activity com os dados alterados.
        Intent returnIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ProductDetailsFragment.ARGUMENT_PRODUCT, product);
        returnIntent.putExtras(bundle);
        activity.setResult(activity.RESULT_OK, returnIntent);


        //Notifica todas os fragments filhos para valorizar os atributos com os dados alterados.
        SparseArrayCompat<ScrollTabHolder> scrollTabHolders = pageAdaper.getScrollTabHolders();

        for(int i = 0; i < scrollTabHolders.size(); i ++) {
            ScrollTabHolder scrollTabHolder = scrollTabHolders.get(i);
            scrollTabHolder.setAtributes(product);
        }

        product.setSyncPending(true);
        ProductDetailsFragment.this.productDetailsPresenter.saveProduct(product);

        navigator.previousActivity(activity);


        //Retorna para a view anterior.
        activity.finish();
    }

    @Override
    public void initializePresenter() {

        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();

        GetProductDetailsUseCase getProductDetailsUseCase = new GetProductDetailsUseCaseImpl(threadExecutor, postExecutionThread);
        SaveProductUseCase saveProductUseCase = new SaveProductUseCaseImpl(threadExecutor, postExecutionThread);
        this.productDetailsPresenter = new ProductDetailsPresenter(this,getProductDetailsUseCase, saveProductUseCase);
    }

    @Override
    public void renderProduct(Product product) {

        if(ViewMode.VISUALIZACAO.equals(viewMode) ){
            imageLoader.displayImage(product.getPictureUrl(), this.iv_header, R.drawable.caixa);
        }

        this.product = product;

        vp_header.setOffscreenPageLimit(PAGES_NUMER);
        pageAdaper = new PageAdaper(getFragmentManager(), product);
        pageAdaper.setTabHolderScrollingContent(new OnScrollTabHolderListner());

        vp_header.setAdapter(pageAdaper);

        mPagerSlidingTabStrip.setViewPager(vp_header);
        mPagerSlidingTabStrip.setOnPageChangeListener(new OnPageChangeListener());
        pageAdaper.notifyDataSetChanged();



    }

    @Override
    public void productSaved() {
        Toast.makeText(activity, "Produto salvo com sucesso", Toast.LENGTH_LONG).show();
    }

    @Override
    public void productRemoved() {
        Toast.makeText(activity, "Produto excluido com sucesso", Toast.LENGTH_LONG).show();
        navigator.previousActivity(activity);
    }

    @Override
    public void showLoading() {
        this.rl_progress.setVisibility(View.VISIBLE);
        this.getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideLoading() {
        this.rl_progress.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);
    }




    @Override public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override public Context getContext() {
        return getActivity().getApplicationContext();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.productDetailsPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.productDetailsPresenter.pause();
    }


    /**
     * Page adapter que ira manipular as fragments das tabs
     */
    private class PageAdaper extends FragmentStatePagerAdapter {

        private Product product;
        private SparseArrayCompat<ScrollTabHolder> scrollTabHolder;
        private ScrollTabHolder listener;

        private final String[] TITLES = {"Principal", "Tabelas de Preço"};

        public void setTabHolderScrollingContent(ScrollTabHolder listener){
            this.listener = listener;
        }

        private PageAdaper(FragmentManager fm, Product product) {
            super(fm);
            this.scrollTabHolder = new SparseArrayCompat<>();
            this.product = product;
        }

        @Override
        public int getItemPosition(Object object){
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            ScrollTabHolderFragment fragment = null;

            switch (position){
                case 0:
                    fragment = (ScrollTabHolderFragment) ProductDetailsMainFragment.newInstance(position, viewMode, product);
                    break;
                case 1:
                    fragment = (ScrollTabHolderFragment) ProductDetailsPriceTableFragment.newInstance(position,  product, viewMode);
                    break;
                default:
            }

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
            if (ViewMode.VISUALIZACAO.equals(viewMode) && vp_header.getCurrentItem() == pagePosition) {
                int scrollY = getScrollY(view);
                ViewHelper.setTranslationY(fl_header, Math.max(-scrollY, mMinHeaderTranslation));
            }
        }

        @Override
        public void setAtributes(Object object) {

        }

        @Override
        public void tabSelected() {

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

            SparseArrayCompat<ScrollTabHolder> scrollTabHolders = pageAdaper.getScrollTabHolders();
            ScrollTabHolder currentHolder = scrollTabHolders.valueAt(position);

            if(ViewMode.VISUALIZACAO.equals(viewMode) ){
                currentHolder.adjustScroll((int) (fl_header.getHeight() + ViewHelper.getTranslationY(fl_header)));
            }



        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (viewMode){
            case EDICAO:
                this.productDetailsPresenter.initialize(this.productId);
                break;
            case VISUALIZACAO:
                this.productDetailsPresenter.initialize(this.productId);
                break;
            case INCLUSAO:
                this.productDetailsPresenter.createNewProduct();

                break;
            default:
                break;
        }

    }

}

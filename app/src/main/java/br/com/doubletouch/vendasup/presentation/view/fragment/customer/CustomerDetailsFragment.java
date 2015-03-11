package br.com.doubletouch.vendasup.presentation.view.fragment.customer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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
import br.com.doubletouch.vendasup.data.database.CustomerDatabase;
import br.com.doubletouch.vendasup.data.database.CustomerDatabaseImpl;
import br.com.doubletouch.vendasup.data.database.CustomerPersistence;
import br.com.doubletouch.vendasup.data.database.PriceTableDatabaseImpl;
import br.com.doubletouch.vendasup.data.database.PriceTablePersistence;
import br.com.doubletouch.vendasup.data.database.PricetableDatabase;
import br.com.doubletouch.vendasup.data.database.sqlite.product.CustomerSQLite;
import br.com.doubletouch.vendasup.data.database.sqlite.product.PriceTableSQLite;
import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.PriceTable;
import br.com.doubletouch.vendasup.data.executor.JobExecutor;
import br.com.doubletouch.vendasup.data.repository.CustomerDataRepository;
import br.com.doubletouch.vendasup.data.repository.PriceTableDataRepository;
import br.com.doubletouch.vendasup.data.repository.datasource.CustomerDataStoreFactory;
import br.com.doubletouch.vendasup.data.repository.datasource.PriceTableDataStoreFactory;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.domain.interactor.GetCustomerDetailsUseCase;
import br.com.doubletouch.vendasup.domain.interactor.GetCustomerDetailsUseCaseImpl;
import br.com.doubletouch.vendasup.domain.interactor.SaveCustomerUseCase;
import br.com.doubletouch.vendasup.domain.interactor.SaveCustomerUseCaseImpl;
import br.com.doubletouch.vendasup.domain.interactor.pricetable.GetPriceTableListUseCase;
import br.com.doubletouch.vendasup.domain.interactor.pricetable.GetPriceTableListUseCaseImpl;
import br.com.doubletouch.vendasup.domain.repository.CustomerRepository;
import br.com.doubletouch.vendasup.domain.repository.PriceTableRepository;
import br.com.doubletouch.vendasup.presentation.UIThread;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.presenter.CustomerDetailsPresenter;
import br.com.doubletouch.vendasup.presentation.view.CustomerDetailsView;
import br.com.doubletouch.vendasup.presentation.view.ViewHelper;
import br.com.doubletouch.vendasup.presentation.view.activity.BaseParallacxFragment;
import br.com.doubletouch.vendasup.presentation.view.activity.CustomerDetailsActivity;
import br.com.doubletouch.vendasup.presentation.view.components.PagerSlidingTabStrip;
import br.com.doubletouch.vendasup.presentation.view.components.parallax.ScrollTabHolder;
import br.com.doubletouch.vendasup.presentation.view.components.parallax.ScrollTabHolderFragment;
import br.com.doubletouch.vendasup.util.image.ImageLoader;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

/**
 * Created by LADAIR on 23/02/2015.
 */
public class CustomerDetailsFragment  extends BaseParallacxFragment implements CustomerDetailsView {


    private static final String ARGUMENT_KEY_CUSTOMER_ID = "kratos.INTENT_EXTRA_PARAM_CUSTOMER_ID";
    private static final String ARGUMENT_KEY_EDITION = "kratos.INTENT_EXTRA_PARAM_EDITION";

    private final int PAGES_NUMER = 4;

    @Optional
    @InjectView(R.id.fl_header)
    public View fl_header;

    @InjectView(R.id.vp_header)
    public ViewPager vp_header;

    @Optional
    @InjectView(R.id.iv_header)
    public ImageView iv_header;

    @InjectView(R.id.tabs)
    public PagerSlidingTabStrip mPagerSlidingTabStrip;


    @InjectView(R.id.rl_progress)
    RelativeLayout rl_progress;

    private ImageLoader imageLoader;

    private PageAdaper pageAdaper;

    private CustomerDetailsPresenter customerDetailsPresenter;

    private Integer customerId;

    private Navigator navigator;

    MenuItem menuEdit;

    private boolean isEdition;

    private Customer customer;

    public static CustomerDetailsFragment newInstance(Integer customerId, boolean isEditionMode){
        CustomerDetailsFragment customerDetailsFragment = new CustomerDetailsFragment();
        Bundle argumentsBundle = new Bundle();
        argumentsBundle.putInt(ARGUMENT_KEY_CUSTOMER_ID, customerId);
        argumentsBundle.putBoolean(ARGUMENT_KEY_EDITION, isEditionMode);
        customerDetailsFragment.setArguments(argumentsBundle);
        return customerDetailsFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.customerId = getArguments().getInt(ARGUMENT_KEY_CUSTOMER_ID);
        this.isEdition = getArguments().getBoolean(ARGUMENT_KEY_EDITION);
        imageLoader = new ImageLoader(getContext());
        navigator = new Navigator();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView;

        if(isEdition){
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
        this.customerDetailsPresenter.initialize(this.customerId);

        setHasOptionsMenu(true);

    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        //Carrega o arquivo de menu.
        inflater.inflate(R.menu.menu_details_view, menu);

        if(isEdition){
            menuEdit = menu.findItem(R.id.it_edit).setVisible(false);
            menu.findItem(R.id.it_done).setVisible(true);
        } else {
            menuEdit = menu.findItem(R.id.it_edit).setVisible(true);
            menu.findItem(R.id.it_done).setVisible(false);
        }


    }

    /**
     * Salva o cliente.
     */
    private void saveCustomer() {

        //Retorna o usuário salvo para atualizar a activity com os dados alterados.
        Intent returnIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CustomerDetailsPersonalFragment.ARGS_CUSTOMER, customer);
        returnIntent.putExtras(bundle);
        getActivity().setResult(getActivity().RESULT_OK, returnIntent);


        //Notifica todas os fragments filhos para valorizar os atributos com os dados alterados.
        SparseArrayCompat<ScrollTabHolder> scrollTabHolders = pageAdaper.getScrollTabHolders();

        for(int i = 0; i < scrollTabHolders.size(); i ++) {
            ScrollTabHolder scrollTabHolder = scrollTabHolders.get(i);
            scrollTabHolder.setAtributes(customer);
        }

        customer.setSyncPending(true);
        CustomerDetailsFragment.this.customerDetailsPresenter.saveCustomer(customer);

        //Retorna para a view anterior.
        getActivity().finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                navigator.previousActivity(getActivity());
                break;
            case R.id.it_edit:
                Intent it = CustomerDetailsActivity.getCallingIntent(getActivity(), customerId, true);
                startActivityForResult (it, 1 );
                break;
            case R.id.it_done:
                saveCustomer();
                break;
            default:
                super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initializePresenter(){

        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();

        CustomerPersistence customerPersistence = new CustomerSQLite();
        PriceTablePersistence priceTablePersistence = new PriceTableSQLite();

        CustomerDatabase customerDatabase = new CustomerDatabaseImpl(customerPersistence);

        PricetableDatabase pricetableDatabase = new PriceTableDatabaseImpl(priceTablePersistence);

        CustomerDataStoreFactory customerDataStoreFactory  = new CustomerDataStoreFactory(this.getContext(), customerDatabase);
        PriceTableDataStoreFactory priceTableDataStoreFactory  = new PriceTableDataStoreFactory(this.getContext(), pricetableDatabase);
        CustomerRepository customerRepository = CustomerDataRepository.getInstace(customerDataStoreFactory);
        PriceTableRepository priceTableRepository = PriceTableDataRepository.getInstace(priceTableDataStoreFactory);
        GetCustomerDetailsUseCase getCustomerDetailsUseCase = new GetCustomerDetailsUseCaseImpl(customerRepository, threadExecutor, postExecutionThread);
        SaveCustomerUseCase saveCustomerUseCase = new SaveCustomerUseCaseImpl(customerRepository, threadExecutor, postExecutionThread);
        GetPriceTableListUseCase getPriceTableListUseCase = new GetPriceTableListUseCaseImpl(priceTableRepository, threadExecutor, postExecutionThread);

        customerDetailsPresenter = new CustomerDetailsPresenter(this, getCustomerDetailsUseCase, saveCustomerUseCase, getPriceTableListUseCase);
    }

    @Override
    public void renderCustomer(Customer customer) {
        if(!isEdition){
            imageLoader.displayImage(customer.getPictureUrl(), this.iv_header);
        }

        this.customer = customer;

        vp_header.setOffscreenPageLimit(PAGES_NUMER);
        pageAdaper = new PageAdaper(getFragmentManager(), customer);
        pageAdaper.setTabHolderScrollingContent(new OnScrollTabHolderListner());

        vp_header.setAdapter(pageAdaper);

        mPagerSlidingTabStrip.setViewPager(vp_header);
        mPagerSlidingTabStrip.setOnPageChangeListener(new OnPageChangeListener());
        pageAdaper.notifyDataSetChanged();
    }



    @Override
    public void customerSaved() {
        Toast.makeText(getActivity(), "Cliente salvo com sucesso" ,Toast.LENGTH_LONG).show();
        navigator.previousActivity(getActivity());
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

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.customerDetailsPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.customerDetailsPresenter.pause();
    }

    @Override
    public Context getContext() {
        return getActivity().getApplicationContext();
    }

    /**
     * Page adapter que ira manipular as fragments das tabs
     */
    private class PageAdaper extends FragmentStatePagerAdapter {

        private Customer customer;
        private SparseArrayCompat<ScrollTabHolder> scrollTabHolder;
        private ScrollTabHolder listener;

        private final String[] TITLES = {"Principal", "Contato", "Endereço", "Financeiro"};

        public void setTabHolderScrollingContent(ScrollTabHolder listener){
            this.listener = listener;
        }

        private PageAdaper(FragmentManager fm, Customer customer) {
            super(fm);
            this.scrollTabHolder = new SparseArrayCompat<>();
            this.customer = customer;
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
                    fragment = (ScrollTabHolderFragment) CustomerDetailsPersonalFragment.newInstance(position,isEdition, customer);
                    break;
                case 1:
                    fragment = (ScrollTabHolderFragment) CustomerDetailsContactFragment.newInstance(position,isEdition, customer);
                    break;
                case 2:
                    fragment = (ScrollTabHolderFragment) CustomerDetailsAddressFragment.newInstance(position,isEdition, customer);
                    break;
                case 3:
                    fragment = (ScrollTabHolderFragment) CustomerDetailsFinancialFragment.newInstance(position, customer);
                    break;
                default:
                    //fragment = (ScrollTabHolderFragment) CustomerDetailsAddressFragment.newInstance(position,isEdition, customer);
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
            if (!isEdition && vp_header.getCurrentItem() == pagePosition) {
                int scrollY = getScrollY(view);
                ViewHelper.setTranslationY(fl_header, Math.max(-scrollY, mMinHeaderTranslation));
            }
        }

        @Override
        public void setAtributes(Customer customer) {

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

            if(!isEdition){
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

        this.customerDetailsPresenter.initialize(this.customerId);
    }
}
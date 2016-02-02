package br.com.doubletouch.vendasup.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.List;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.data.entity.enumeration.ViewMode;
import br.com.doubletouch.vendasup.data.executor.JobExecutor;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.domain.interactor.product.GetProductListFilterUseCase;
import br.com.doubletouch.vendasup.domain.interactor.product.GetProductListFilterUseCaseImpl;
import br.com.doubletouch.vendasup.domain.interactor.product.GetProductListUseCase;
import br.com.doubletouch.vendasup.domain.interactor.product.GetProductListUseCaseImpl;
import br.com.doubletouch.vendasup.presentation.UIThread;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.presenter.ProductListPresenter;
import br.com.doubletouch.vendasup.presentation.view.ProductListView;
import br.com.doubletouch.vendasup.presentation.view.activity.ProductDetailsActivity;
import br.com.doubletouch.vendasup.presentation.view.adapter.KratosLayoutManager;
import br.com.doubletouch.vendasup.presentation.view.adapter.ProductsAdapter;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by LADAIR on 12/02/2015.
 */
public class ProductListFragment extends BaseFragment implements ProductListView {



    private ProductListPresenter productListPresenter;
    private KratosLayoutManager kratosLayoutManager;
    private ProductListListener productListListener;
    private ProductsAdapter productAdapter;

    @InjectView(R.id.rv_users)
    RecyclerView rv_products;

    @InjectView(R.id.rl_progress)
    RelativeLayout rl_progress;


    private Navigator navigator;

    private Activity activity;

    public ProductListFragment() {
        super();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof ProductListListener){
            this.productListListener = (ProductListListener) activity;
        }

        this.activity = activity;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_product_list, container, true);
        ButterKnife.inject(this, fragmentView);
        setupUI();

        navigator = new Navigator();

        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        this.loadProductList();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        //Carrega o arquivo de menu.
        inflater.inflate(R.menu.menu_search_view, menu);

        //Pega o Componente.
        SearchView mSearchView = (SearchView) menu.findItem(R.id.search).getActionView();
        //Define um texto de ajuda:
        mSearchView.setQueryHint("Pesquisar");

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                productListPresenter.onProductFilterChange(s);
                return false;
            }
        });
    }

    @Override
    public void initializePresenter() {
        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();

        GetProductListUseCase getProductListUseCase = new GetProductListUseCaseImpl( threadExecutor, postExecutionThread);
        GetProductListFilterUseCase getProductListFilterUseCase = new GetProductListFilterUseCaseImpl( threadExecutor, postExecutionThread);
        this.productListPresenter = new ProductListPresenter(this,getProductListUseCase, getProductListFilterUseCase);
    }

    @Override
    public void renderProductList(List<Product> productsCollection) {
        if(productsCollection != null){
            if(productAdapter == null){
                this.productAdapter = new ProductsAdapter(getActivity(), productsCollection);
            } else {
                this.productAdapter.setProductsCollection(productsCollection);
            }

            this.productAdapter.setOnItemClickListener(onItemClickListener);
            this.rv_products.setAdapter(productAdapter);
        }
    }

    @Override
    public void viewProduct(Product product) {
        if(this.productListListener != null){
            Intent intentToLaunch = ProductDetailsActivity.getCallingIntent(activity, product.getID(), ViewMode.VISUALIZACAO);
            startActivityForResult(intentToLaunch,1);
            navigator.transitionGo(activity);
        }
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
    public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override
    public Context getContext() {
        return this.getActivity().getApplicationContext();
    }

    private void setupUI(){
        this.kratosLayoutManager = new KratosLayoutManager(getActivity());
        this.rv_products.setLayoutManager(kratosLayoutManager);
    }

    private ProductsAdapter.OnItemClickListener onItemClickListener = new ProductsAdapter.OnItemClickListener() {
        @Override
        public void onProductItemClicked(Product product) {
            if(ProductListFragment.this.productListPresenter != null && product != null){
                   ProductListFragment.this.productListPresenter.onProductClicked(product);
            }
        }
    };

    /**
     * Carrega os produtos
     */
    private void loadProductList(){
        this.productListPresenter.initialize();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        this.productListPresenter.initialize();

    }

    @OnClick( R.id.btnAdd )
    public void addProduct() {
        Intent intentToLaunch = ProductDetailsActivity.getCallingIntent(activity, 0, ViewMode.INCLUSAO);
        startActivityForResult(intentToLaunch,1);
    }
}

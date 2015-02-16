package br.com.doubletouch.vendasup.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.database.ProductDatabase;
import br.com.doubletouch.vendasup.data.database.ProductDatabaseImpl;
import br.com.doubletouch.vendasup.data.database.ProductPersistence;
import br.com.doubletouch.vendasup.data.database.sqlite.product.ProductSQLite;
import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.data.executor.JobExecutor;
import br.com.doubletouch.vendasup.data.repository.ProductDataRepository;
import br.com.doubletouch.vendasup.data.repository.datasource.ProductDataStoreFactory;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.domain.interactor.GetProductDetailsUseCase;
import br.com.doubletouch.vendasup.domain.interactor.GetProductDetailsUseCaseImpl;
import br.com.doubletouch.vendasup.domain.interactor.GetProductListUseCase;
import br.com.doubletouch.vendasup.domain.interactor.GetProductListUserCaseImpl;
import br.com.doubletouch.vendasup.domain.repository.ProductRepository;
import br.com.doubletouch.vendasup.presentation.UIThread;
import br.com.doubletouch.vendasup.presentation.presenter.ProductDetailsPresenter;
import br.com.doubletouch.vendasup.presentation.presenter.ProductListPresenter;
import br.com.doubletouch.vendasup.presentation.view.ProductDetailsView;
import br.com.doubletouch.vendasup.presentation.view.components.AutoLoadImageView;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by LADAIR on 15/02/2015.
 */
public class ProductDetailsFragment extends BaseFragment implements ProductDetailsView {

    private static final String ARGUMENT_KEY_PRODUCT_ID = "kratos.ARGUMENT_PRODUCT_ID";

    private Integer productId;
    private ProductDetailsPresenter productDetailsPresenter;


    @InjectView(R.id.iv_cover)
    AutoLoadImageView iv_cover;

    @InjectView(R.id.tv_fullname)
    TextView tv_fullname;

    @InjectView(R.id.tv_product_code)
    TextView tv_product_code;

    @InjectView(R.id.tv_product_description)
    TextView tv_product_description;

    @InjectView(R.id.rl_progress)
    RelativeLayout rl_progress;

    @InjectView(R.id.rl_retry)
    RelativeLayout rl_retry;


    public ProductDetailsFragment(){
        super();
    }

    public static ProductDetailsFragment newInstance(int productId){
        ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
        Bundle argumentsBundle = new Bundle();
        argumentsBundle.putInt(ARGUMENT_KEY_PRODUCT_ID, productId);
        productDetailsFragment.setArguments(argumentsBundle);
        return productDetailsFragment;
    }

    /**
     * Inicialização do fragment
     */
    private void initialize(){
        this.productId = getArguments().getInt(ARGUMENT_KEY_PRODUCT_ID);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initialize();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_product_details, container, false);
        ButterKnife.inject(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.productDetailsPresenter.initialize(this.productId);
    }

    @Override
    void initializePresenter() {

        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();

        ProductPersistence productPersistence = new ProductSQLite();

        ProductDatabase productDatabase = new ProductDatabaseImpl(productPersistence);

        ProductDataStoreFactory productDataStoreFactory  = new ProductDataStoreFactory(this.getContext(), productDatabase);
        ProductRepository productRepository = ProductDataRepository.getInstace(productDataStoreFactory);
        GetProductDetailsUseCase getProductDetailsUseCase = new GetProductDetailsUseCaseImpl(productRepository, threadExecutor, postExecutionThread);
        this.productDetailsPresenter = new ProductDetailsPresenter(this,getProductDetailsUseCase);
    }

    @Override
    public void renderProduct(Product product) {
        if(product != null){
            this.iv_cover.setImageUrl(product.getPictureUrl());
            this.tv_fullname.setText(product.getDescription());
            this.tv_product_code.setText(product.getProductID());
            this.tv_product_description.setText(product.getDescription());
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



    @Override public void showRetry() {
        this.rl_retry.setVisibility(View.VISIBLE);
    }

    @Override public void hideRetry() {
        this.rl_retry.setVisibility(View.GONE);
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
}

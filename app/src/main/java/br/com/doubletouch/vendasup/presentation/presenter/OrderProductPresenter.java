package br.com.doubletouch.vendasup.presentation.presenter;

import java.util.Collection;
import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.data.executor.JobExecutor;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.domain.interactor.product.GetProductListFilterUseCase;
import br.com.doubletouch.vendasup.domain.interactor.product.GetProductListUseCase;
import br.com.doubletouch.vendasup.domain.interactor.product.GetProductListUseCaseImpl;
import br.com.doubletouch.vendasup.presentation.UIThread;
import br.com.doubletouch.vendasup.presentation.exception.ErrorMessageFactory;
import br.com.doubletouch.vendasup.presentation.view.ProductListView;

/**
 * Created by LADAIR on 06/04/2015.
 */
public class OrderProductPresenter implements  Presenter {

    private ProductListView productListView;
    private final GetProductListUseCase getProductListUseCase;
    private final GetProductListFilterUseCase getProductListFilterUseCase;

    public OrderProductPresenter(ProductListView productListView, GetProductListUseCase getProductListUseCase, GetProductListFilterUseCase getProductListFilterUseCase) {
        this.productListView = productListView;
        this.getProductListUseCase = getProductListUseCase;
        this.getProductListFilterUseCase = getProductListFilterUseCase;
    }

    public void initialize(){
        this.showViewLoading();
        this.getProductList();
    }

    public void onProductFilterChange(String filter){
        this.getProductListFilter(filter);
    }

    private void getProductListFilter(String filter){
        this.getProductListFilterUseCase.execute(filter, filter, productListFilterCallback);
    }

    private void showViewLoading() {
        this.productListView.showLoading();
    }

    private void getProductList(){
        this.getProductListUseCase.execute(productListCallback);
    }

    private final GetProductListUseCase.Callback productListCallback = new GetProductListUseCase.Callback() {
        @Override
        public void onProductListLoaded(Collection<Product> productsCollection) {
            OrderProductPresenter.this.showProductsCollectionInView(productsCollection);
            OrderProductPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            OrderProductPresenter.this.hideViewLoading();
            OrderProductPresenter.this.showErrorMessage(errorBundle);


        }
    };

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    private void showProductsCollectionInView(Collection<Product> productsCollection){
        this.productListView.renderProductList((List<Product>) productsCollection);
    }

    private void hideViewLoading() {
        this.productListView.hideLoading();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.productListView.getContext(),
                errorBundle.getException());
        this.productListView.showError(errorMessage);
    }
    private final GetProductListFilterUseCase.Callback productListFilterCallback = new GetProductListFilterUseCase.Callback() {
        @Override
        public void onProductListFilterLoaded(Collection<Product> productsCollection) {
            OrderProductPresenter.this.showProductsCollectionInView(productsCollection);
            OrderProductPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            OrderProductPresenter.this.hideViewLoading();
            OrderProductPresenter.this.showErrorMessage(errorBundle);

        }
    };

}

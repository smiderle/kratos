package br.com.doubletouch.vendasup.presentation.presenter;

import java.util.Collection;
import java.util.List;

import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.interactor.product.GetProductListFilterUseCase;
import br.com.doubletouch.vendasup.domain.interactor.product.GetProductListUseCase;
import br.com.doubletouch.vendasup.presentation.exception.ErrorMessageFactory;
import br.com.doubletouch.vendasup.presentation.view.ProductListView;

/**
 * Created by LADAIR on 10/02/2015.
 */
public class ProductListPresenter implements Presenter {

    private final ProductListView productListView;
    private final GetProductListUseCase getProductListUseCase;
    private final GetProductListFilterUseCase getProductListFilterUseCase;


    public ProductListPresenter(ProductListView productListView, GetProductListUseCase getProductListUseCase, GetProductListFilterUseCase getProductListFilterUseCase ){

        if (productListView == null || getProductListUseCase == null) {
            throw new IllegalArgumentException("Os parametros do Construtor não podem ser nulos!!!");
        }

        this.productListView = productListView;
        this.getProductListUseCase = getProductListUseCase;
        this.getProductListFilterUseCase = getProductListFilterUseCase;
    }

    public void initialize() {
        this.loadUserList();
    }

    private void loadUserList() {
        this.showViewLoading();
        this.getProductList();
    }

    private void showViewLoading() {
        this.productListView.showLoading();
    }

    private void hideViewLoading() {
        this.productListView.hideLoading();
    }


    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.productListView.getContext(),
                errorBundle.getException());
        this.productListView.showError(errorMessage);
    }

    private void showProductsCollectionInView(Collection<Product> productsCollection){
        this.productListView.renderProductList((List<Product>) productsCollection);
    }

    private void getProductList(){
        this.getProductListUseCase.execute(productListCallback);
    }

    private void getProductListFilter(String filter){
        this.getProductListFilterUseCase.execute(filter, filter, productListFilterCallback);
    }

    @Override
    public void resume() {
        //empty
    }

    @Override
    public void pause() {
        //empty
    }

    public void onProductClicked(Product product){
        this.productListView.viewProduct(product);
    }

    public void onProductFilterChange(String filter){
        this.getProductListFilter(filter);
    }


    private final GetProductListUseCase.Callback productListCallback = new GetProductListUseCase.Callback() {
        @Override
        public void onProductListLoaded(Collection<Product> productsCollection) {
            ProductListPresenter.this.showProductsCollectionInView(productsCollection);
            ProductListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            ProductListPresenter.this.hideViewLoading();
            ProductListPresenter.this.showErrorMessage(errorBundle);


        }
    };

    private final GetProductListFilterUseCase.Callback productListFilterCallback = new GetProductListFilterUseCase.Callback() {
        @Override
        public void onProductListFilterLoaded(Collection<Product> productsCollection) {
            ProductListPresenter.this.showProductsCollectionInView(productsCollection);
            ProductListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            ProductListPresenter.this.hideViewLoading();
            ProductListPresenter.this.showErrorMessage(errorBundle);

        }
    };
}

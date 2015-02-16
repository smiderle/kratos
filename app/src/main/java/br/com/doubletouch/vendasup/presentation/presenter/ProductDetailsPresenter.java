package br.com.doubletouch.vendasup.presentation.presenter;

import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.interactor.GetProductDetailsUseCase;
import br.com.doubletouch.vendasup.presentation.exception.ErrorMessageFactory;
import br.com.doubletouch.vendasup.presentation.view.ProductDetailsView;

/**
 * Created by LADAIR on 15/02/2015.
 */
public class ProductDetailsPresenter implements Presenter {

    /**
     * Para carregar os detalhes do produto
     */
    private Integer productId;

    private final ProductDetailsView productDetailsView;
    private final GetProductDetailsUseCase getProductDetailsUseCase;

    public ProductDetailsPresenter(ProductDetailsView productDetailsView, GetProductDetailsUseCase getProductDetailsUseCase) {
        if (productDetailsView == null || getProductDetailsUseCase == null) {
            throw new IllegalArgumentException("Parametros do construtor não podem ser nulos.");
        }

        this.productDetailsView = productDetailsView;
        this.getProductDetailsUseCase = getProductDetailsUseCase;
    }

    public void initialize(Integer productId){
        this.productId = productId;
        this.loadProductDetails();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    private void loadProductDetails(){
        this.hideViewRetry();
        this.showViewLoading();
        this.getProductDetails();
    }

    private void getProductDetails(){
        this.getProductDetailsUseCase.execute(this.productId, this.productDetailsCallback);
    }

    private void showViewLoading() {
        this.productDetailsView.showLoading();
    }

    private void hideViewLoading() {
        this.productDetailsView.hideLoading();
    }

    private void showViewRetry() {
        this.productDetailsView.showRetry();
    }

    private void hideViewRetry() {
        this.productDetailsView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.productDetailsView.getContext(),
                errorBundle.getException());
        this.productDetailsView.showError(errorMessage);
    }

    private void showProductDetailsInView(Product product) {
        this.productDetailsView.renderProduct(product);
    }

    private final GetProductDetailsUseCase.Callback productDetailsCallback = new GetProductDetailsUseCase.Callback(){

        @Override
        public void onProductDataLoaded(Product product) {
            ProductDetailsPresenter.this.showProductDetailsInView(product);
            ProductDetailsPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            ProductDetailsPresenter.this.hideViewLoading();
            ProductDetailsPresenter.this.showErrorMessage(errorBundle);
            ProductDetailsPresenter.this.showViewRetry();
        }
    };
}

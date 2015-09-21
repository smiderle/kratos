package br.com.doubletouch.vendasup.presentation.presenter;

import java.util.UUID;

import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.data.service.ProductService;
import br.com.doubletouch.vendasup.data.service.ProductServiceImpl;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.interactor.product.GetProductDetailsUseCase;
import br.com.doubletouch.vendasup.domain.interactor.product.SaveProductUseCase;
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
    private final SaveProductUseCase saveProductUseCase;

    public ProductDetailsPresenter(ProductDetailsView productDetailsView, GetProductDetailsUseCase getProductDetailsUseCase, SaveProductUseCase saveProductUseCase) {
        if (productDetailsView == null || getProductDetailsUseCase == null || saveProductUseCase== null) {
            throw new IllegalArgumentException("Parametros do construtor não podem ser nulos.");
        }

        this.saveProductUseCase = saveProductUseCase;
        this.productDetailsView = productDetailsView;
        this.getProductDetailsUseCase = getProductDetailsUseCase;
    }

    public void initialize(Integer productId){
        this.productId = productId;
        this.loadProductDetails();
    }

    public void createNewProduct(){
        Product product = new Product();

        ProductService productService = new ProductServiceImpl();
        Integer id = productService.getLessNegative();

        product.setIdMobile(id);
        product.setID(id);
        product.setBranchID(VendasUp.getBranchOffice().getBranchOfficeID());
        product.setOrganizationID(VendasUp.getBranchOffice().getOrganization().getOrganizationID());
        product.setExcluded(false);

        showProductDetailsInView(product);
    }

    public void saveProduct(Product product){
        this.showViewLoading();
        product.setExcluded(false);
        this.saveProductExecutor(product);
    }

    public void deleteProduct(Product product){
        this.showViewLoading();
        product.setExcluded(true);
        product.setSyncPending(true);
        this.removeProductExecutor(product);
    }




    private void saveProductExecutor(Product product){
        this.saveProductUseCase.execute(product, this.saveProductCallback);
    }

    private void removeProductExecutor(Product product){
        this.saveProductUseCase.execute(product, this.removedProductCallback);
    }



    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    private void loadProductDetails(){
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
        }
    };

    private void productSaved(){
        this.productDetailsView.productSaved();
    }

    private void productRemoved(){
        this.productDetailsView.productRemoved();
    }


    private final SaveProductUseCase.Callback saveProductCallback = new SaveProductUseCase.Callback() {
        @Override
        public void onProductSaved(Product product) {
            ProductDetailsPresenter.this.productSaved();
            ProductDetailsPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            ProductDetailsPresenter.this.hideViewLoading();
            ProductDetailsPresenter.this.showErrorMessage(errorBundle);
        }
    };

    private final SaveProductUseCase.Callback removedProductCallback = new SaveProductUseCase.Callback() {
        @Override
        public void onProductSaved(Product product) {
            ProductDetailsPresenter.this.productRemoved();
            ProductDetailsPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            ProductDetailsPresenter.this.hideViewLoading();
            ProductDetailsPresenter.this.showErrorMessage(errorBundle);
        }
    };
}

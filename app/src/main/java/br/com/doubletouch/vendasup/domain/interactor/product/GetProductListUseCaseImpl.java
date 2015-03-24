package br.com.doubletouch.vendasup.domain.interactor.product;

import java.util.Collection;

import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.data.service.ProductServiceImpl;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.data.service.ProductService;

/**
 * Essa classe é a implementação de {@link GetProductListUseCase}
 * Created by LADAIR on 12/02/2015.
 */
public class GetProductListUseCaseImpl implements GetProductListUseCase {

    private final ProductService productService;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Callback callback;

    public GetProductListUseCaseImpl( ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {

        if(threadExecutor == null || postExecutionThread == null){
            throw  new IllegalArgumentException("Os parametros do construtor não podem ser nulos.");
        }

        this.productService = new ProductServiceImpl();
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Interactor callback não pode ser null!!!");
        }

        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override
    public void run() {
        this.productService.getProductList( this.productListCallback);
    }

    private final ProductService.ProductListCallback productListCallback = new ProductService.ProductListCallback() {
        @Override
        public void onProductListLoaded(Collection<Product> productsCollection) {
            notifyGetProductListSuccessfully(productsCollection);
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyError(errorBundle);
        }
    };

    private void notifyGetProductListSuccessfully(final Collection<Product> productsCollection){
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onProductListLoaded(productsCollection);
            }
        });
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(new Runnable() {
            @Override public void run() {
                callback.onError(errorBundle);
            }
        });
    }
}

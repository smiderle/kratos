package br.com.doubletouch.vendasup.domain.interactor.product;

import java.util.Collection;

import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.data.service.ProductServiceImpl;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.data.service.ProductService;

/**
 * Created by LADAIR on 17/02/2015.
 */
public class GetProductListFilterUseCaseImpl implements GetProductListFilterUseCase {

    private String description;
    private String productId;
    private Integer branchId;

    private final ProductService productService;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private GetProductListFilterUseCase.Callback callback;

    public GetProductListFilterUseCaseImpl( ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {

        if(threadExecutor == null || postExecutionThread == null){
            throw  new IllegalArgumentException("Os parametros do construtor não podem ser nulos.");
        }

        this.productService = new ProductServiceImpl();
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }



    @Override
    public void execute(String description, String productId, Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Interactor callback não pode ser null!!!");
        }
        this.description = description;
        this.productId = productId;
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override
    public void run() {
        this.productService.getProductListByFilter(description, productId, this.productListFilterCallback);
    }



    private final ProductService.ProductListFilterCallback productListFilterCallback = new ProductService.ProductListFilterCallback() {
        @Override
        public void onProductListFilterLoaded(Collection<Product> productsCollection) {
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
                callback.onProductListFilterLoaded(productsCollection);
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

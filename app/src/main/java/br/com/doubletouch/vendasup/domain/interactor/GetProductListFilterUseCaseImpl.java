package br.com.doubletouch.vendasup.domain.interactor;

import java.util.Collection;

import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.domain.repository.ProductRepository;

/**
 * Created by LADAIR on 17/02/2015.
 */
public class GetProductListFilterUseCaseImpl implements GetProductListFilterUseCase {

    private String description;
    private String productId;
    private Integer branchId;

    private final ProductRepository productRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private GetProductListFilterUseCase.Callback callback;

    public GetProductListFilterUseCaseImpl(ProductRepository productRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {

        if(productRepository == null || threadExecutor == null || postExecutionThread == null){
            throw  new IllegalArgumentException("Os parametros do construtor não podem ser nulos.");
        }

        this.productRepository = productRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }



    @Override
    public void execute(String description, String productId, Integer branchId, Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Interactor callback não pode ser null!!!");
        }
        this.description = description;
        this.productId = productId;
        this.branchId = branchId;
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override
    public void run() {
        this.productRepository.getProductListByFilter(description, productId, branchId, this.productListFilterCallback);
    }



    private final ProductRepository.ProductListFilterCallback productListFilterCallback = new ProductRepository.ProductListFilterCallback() {
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

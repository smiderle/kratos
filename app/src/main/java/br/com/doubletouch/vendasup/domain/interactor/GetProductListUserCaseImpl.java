package br.com.doubletouch.vendasup.domain.interactor;

import java.util.Collection;

import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.domain.repository.ProductRepository;

/**
 * Essa classe é a implementação de {@link br.com.doubletouch.vendasup.domain.interactor.GetProductListUseCase}
 * Created by LADAIR on 12/02/2015.
 */
public class GetProductListUserCaseImpl implements GetProductListUseCase {

    private final ProductRepository productRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Callback callback;

    public GetProductListUserCaseImpl(ProductRepository productRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {

        if(productRepository == null || threadExecutor == null || postExecutionThread == null){
            throw  new IllegalArgumentException("Os parametros do construtor não podem ser nulos.");
        }

        this.productRepository = productRepository;
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
        this.productRepository.getProductList(VendasUp.getUsuarioLogado().getOrganizationID(), this.productListCallback);
    }

    private final ProductRepository.ProductListCallback productListCallback = new ProductRepository.ProductListCallback() {
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

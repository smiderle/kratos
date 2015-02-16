package br.com.doubletouch.vendasup.domain.interactor;

import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.domain.repository.ProductRepository;

/**
 * Created by LADAIR on 15/02/2015.
 */
public class GetProductDetailsUseCaseImpl implements GetProductDetailsUseCase {

    private Integer productId;
    private final ProductRepository productRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Callback callback;


    public GetProductDetailsUseCaseImpl(ProductRepository productRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {

        if(productRepository == null || threadExecutor == null || postExecutionThread == null){
            throw  new IllegalArgumentException("Os parametros do construtor não podem ser nulos.");
        }

        this.productRepository = productRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }


    @Override
    public void execute(Integer productId, Callback callback) {
        if (productId == null || callback == null) {
            throw new IllegalArgumentException("Parametros inválidos.");
        }

        this.productId = productId;
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override
    public void run() {
        this.productRepository.getProductById(this.productId, this.repositoryCallback );
    }

    private final ProductRepository.ProductDetailsCallback repositoryCallback =
            new ProductRepository.ProductDetailsCallback() {

                @Override
                public void onProductLoaded(Product product) {
                    notifyGetProductDetailsSuccessfully(product);
                }

                @Override public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };


    private void notifyGetProductDetailsSuccessfully(final Product product) {
        this.postExecutionThread.post(new Runnable() {
            @Override public void run() {
                callback.onProductDataLoaded(product);
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

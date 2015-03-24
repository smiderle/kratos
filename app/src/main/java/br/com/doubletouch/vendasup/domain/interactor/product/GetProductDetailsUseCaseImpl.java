package br.com.doubletouch.vendasup.domain.interactor.product;

import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.data.service.ProductServiceImpl;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.data.service.ProductService;

/**
 * Created by LADAIR on 15/02/2015.
 */
public class GetProductDetailsUseCaseImpl implements GetProductDetailsUseCase {

    private Integer productId;
    private final ProductService productService;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Callback callback;


    public GetProductDetailsUseCaseImpl( ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {

        if(threadExecutor == null || postExecutionThread == null){
            throw  new IllegalArgumentException("Os parametros do construtor não podem ser nulos.");
        }

        this.productService = new ProductServiceImpl();
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
        this.productService.getProductById(this.productId, this.repositoryCallback );
    }

    private final ProductService.ProductDetailsCallback repositoryCallback =
            new ProductService.ProductDetailsCallback() {

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

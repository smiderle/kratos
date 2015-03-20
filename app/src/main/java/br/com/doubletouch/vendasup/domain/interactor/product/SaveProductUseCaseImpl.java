package br.com.doubletouch.vendasup.domain.interactor.product;

import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.data.service.CustomerServiceImpl;
import br.com.doubletouch.vendasup.data.service.ProductServiceImpl;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.domain.repository.CustomerService;
import br.com.doubletouch.vendasup.domain.repository.ProductService;

/**
 * Created by LADAIR on 08/03/2015.
 */
public class SaveProductUseCaseImpl implements SaveProductUseCase {

    private final ProductService productService;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private SaveProductUseCase.Callback callback;

    private Product product;

    public SaveProductUseCaseImpl(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {

        if(threadExecutor == null || postExecutionThread == null){
            throw  new IllegalArgumentException("Os parametros do construtor não podem ser nulos.");
        }

        this.productService = new ProductServiceImpl();
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(Product product, Callback callback) {
        if (product == null || callback == null) {
            throw new IllegalArgumentException("Parametros inválidos.");
        }

        this.product = product;
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override
    public void run() {
        this.productService.save(this.product, this.repositoryCallback);
    }

    private final ProductService.ProductSaveCallback repositoryCallback = new ProductService.ProductSaveCallback() {


        @Override
        public void onError(ErrorBundle errorBundle) {

            notifyError(errorBundle);
        }

        @Override
        public void onProductSaved(Product product) {
            notifySaveProductSuccessfully(product);
        }
    };


    private void notifySaveProductSuccessfully(final Product product) {
        this.postExecutionThread.post(new Runnable() {
            @Override public void run() {
                callback.onProductSaved(product);
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

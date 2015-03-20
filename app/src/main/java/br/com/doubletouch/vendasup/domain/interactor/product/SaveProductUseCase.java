package br.com.doubletouch.vendasup.domain.interactor.product;

import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.interactor.Interactor;

/**
 * Created by LADAIR on 08/03/2015.
 */
public interface SaveProductUseCase extends Interactor {


    /**
     * Callback usado para notificar quando um produto for carregado ou ocorrer um erro.
     */
    interface Callback {
        void onProductSaved(Product product);
        void onError(ErrorBundle errorBundle);
    }

    /**
     *
     * @param callback
     */
    void execute(Product product, Callback callback);
}

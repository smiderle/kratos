package br.com.doubletouch.vendasup.domain.interactor.product;

import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.interactor.Interactor;

/**
 * Essa interface representa um execução para o caso de uso para obter os detalhes do {@link br.com.doubletouch.vendasup.data.entity.Product}
 * Por convenção a implementação desse caso de uso (Interactor) vai retornar um resultado usando Callback.
 * O callback deve ser executar no UI thread.
 *
 * Created by LADAIR on 15/02/2015.
 */
public interface GetProductDetailsUseCase extends Interactor {


        /**
         * Callback usado para notificar quando um produto for carregado ou ocorrer um erro.
         */
        interface Callback {
            void onProductDataLoaded(Product product);
            void onError(ErrorBundle errorBundle);
        }

        /**
         *
         * @param callback
         */
        void execute(Integer productId, Callback callback);
}

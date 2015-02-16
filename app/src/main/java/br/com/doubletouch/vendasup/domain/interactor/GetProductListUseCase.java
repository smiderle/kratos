package br.com.doubletouch.vendasup.domain.interactor;

import java.util.Collection;

import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;

/**
 * Essa interface representa um execução para o caso de uso para obter uma lista de {@link br.com.doubletouch.vendasup.data.entity.Product}
 * Por convenção a implementação desse caso de uso (Interactor) vai retornar um resultado usando Callback.
 * O callback deve ser executar no UI thread.
 *
 * Created by LADAIR on 12/02/2015.
 */
public interface GetProductListUseCase extends Interactor {

    /**
     * Callback usado para notificar quando a collection for carregada ou ocorrer um erro.
     */
    interface Callback {
        void onProductListLoaded(Collection<Product> productsCollection);
        void onError(ErrorBundle errorBundle);
    }

    /**
     *
     * @param callback
     */
    void execute(Callback callback);
}

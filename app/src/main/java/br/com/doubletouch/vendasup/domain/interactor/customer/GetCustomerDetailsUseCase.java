package br.com.doubletouch.vendasup.domain.interactor.customer;

import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.interactor.Interactor;

/**
 * Essa interface representa um execução para o caso de uso para obter os detalhes do {@link br.com.doubletouch.vendasup.data.entity.Customer}
 * Por convenção a implementação desse caso de uso (Interactor) vai retornar um resultado usando Callback.
 * O callback deve ser executar no UI thread.
 *
 * Created by LADAIR on 18/02/2015.
 */
public interface GetCustomerDetailsUseCase extends Interactor {


        /**
         * Callback usado para notificar quando um produto for carregado ou ocorrer um erro.
         */
        interface Callback {
            void onCustomerDataLoaded(Customer customer);
            void onError(ErrorBundle errorBundle);
        }

        /**
         *
         * @param callback
         */
        void execute(Integer customerId, Callback callback);
}

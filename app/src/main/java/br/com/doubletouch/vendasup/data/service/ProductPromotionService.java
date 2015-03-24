package br.com.doubletouch.vendasup.data.service;

import java.util.Collection;
import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.data.entity.ProductPromotion;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;

/**
 * Interface que representa o Repositório para obter dados relacionados ao  {@link br.com.doubletouch.vendasup.data.entity.ProductPromotion}
 * Created by LADAIR on 12/02/2015.
 *
 */
public interface ProductPromotionService {

    public void saveOrUpdateSynchronous(List<ProductPromotion> produtosPromocoes) ;

}

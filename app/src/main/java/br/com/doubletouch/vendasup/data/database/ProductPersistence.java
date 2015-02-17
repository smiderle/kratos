package br.com.doubletouch.vendasup.data.database;

import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Product;

/**
 *
 * Created by LADAIR on 13/02/2015.
 */
public interface ProductPersistence extends Persistence<Product> {

    public static final String LIMIT = "50";

    /**
     * Retorna todos os produtos com o código do produto ou que iniciem com a descrição
     * @param description descricao
     * @param productId idproduto
     * @param branchId idfilial
     * @return lista de produtos
     */
    List<Product> getByDescriptionOrProductId(String description, String productId, Integer branchId);
}
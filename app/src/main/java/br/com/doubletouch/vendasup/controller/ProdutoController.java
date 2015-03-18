package br.com.doubletouch.vendasup.controller;

import java.util.List;

import br.com.doubletouch.vendasup.data.database.dao.ProductDAO;
import br.com.doubletouch.vendasup.data.entity.Product;

/**
 * Created by LADAIR on 29/01/2015.
 */
public class ProdutoController {

    /**
     * Salva se for um novo registro. Ou atualiza se ja existir.
     * @param products
     */
    public void saveOrUpdate(List<Product> products) {
        ProductDAO productDAO = new ProductDAO();
        productDAO.insert(products);
    }
}

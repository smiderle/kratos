package br.com.doubletouch.vendasup.controller;

import android.database.sqlite.SQLiteConstraintException;

import java.util.List;

import br.com.doubletouch.vendasup.dao.ProdutoDAO;
import br.com.doubletouch.vendasup.data.database.sqlite.product.ProductSQLite;
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
        ProductSQLite productSQLite = new ProductSQLite();
        productSQLite.insert(products);
    }
}

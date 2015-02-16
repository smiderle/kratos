package br.com.doubletouch.vendasup.controller;

import android.database.sqlite.SQLiteConstraintException;

import java.util.List;

import br.com.doubletouch.vendasup.dao.ProdutoDAO;
import br.com.doubletouch.vendasup.data.entity.Product;

/**
 * Created by LADAIR on 29/01/2015.
 */
public class ProdutoController {

    /**
     * Salva se for um novo registro. Ou atualiza se ja existir.
     * @param products
     */
    public void saveOrUpdate(List<Product> products){
        ProdutoDAO produtoDAO = new ProdutoDAO();
        for ( Product product : products) {
            try{
                produtoDAO.save(product);
            }catch (SQLiteConstraintException e){
                produtoDAO.update(product);
            }
        }
    }


    public void salvar( List<Product> products) {
        ProdutoDAO produtoDAO = new ProdutoDAO();
        for ( Product product : products) {
            produtoDAO.saveOrUpdate(product);
        }
    }

    public List<Product> listaTodos(){
        ProdutoDAO produtoDAO = new ProdutoDAO();
        return produtoDAO.getAll();
    }
}

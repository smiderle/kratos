package br.com.doubletouch.vendasup.dao.script;

import br.com.doubletouch.vendasup.data.entity.Product;

/**
 * Created by LADAIR on 26/01/2015.
 */
public class ProdutoSQL {

    /**
     * Script para criação da tabale PRODUTO
     * @return
     */
    public static String create(){
        String SQL = "CREATE TABLE IF NOT EXISTS "+ Product.ProductDB.TABELA+" (" +
                Product.ProductDB.ID + " INTEGER NOT NULL , " +
                Product.ProductDB.IDEMPRESA+ " INTEGER NOT NULL , "+
                Product.ProductDB.IDFILIAL+ " INTEGER NOT NULL , " +
                Product.ProductDB.IDPRODUTO + " TEXT, " +
                Product.ProductDB.DESCRICAO + " TEXT NOT NULL,"+
                Product.ProductDB.REFERENCIA +  " TEXT,"+
                Product.ProductDB.EMBALAGEM + " TEXT,"+
                Product.ProductDB.CODBAR + " TEXT,"+
                Product.ProductDB.ESTOQUE + " DOUBLE,"+
                Product.ProductDB.PRECO_VENDA + " DOUBLE,"+
                Product.ProductDB.ATIVO + " BOOLEAN," +
                Product.ProductDB.EXCLUIDO + " BOOLEAN,"+
                Product.ProductDB.URL_IMAGEM + " TEXT ,"+
                "PRIMARY KEY( "+ Product.ProductDB.ID +" )  );";
        return SQL;
    }
}

package br.com.doubletouch.vendasup.data.database.script;

import br.com.doubletouch.vendasup.data.entity.Product;

/**
 * Created by LADAIR on 26/01/2015.
 */
public final class ProductDB {

    private ProductDB(){}

    /**
     * Script para criação da tabale PRODUTO
     * @return
     */
    public static String create(){
        String SQL = "CREATE TABLE IF NOT EXISTS "+ TABELA+" (" +
                ID + " INTEGER NOT NULL , " +
                IDEMPRESA+ " INTEGER NOT NULL , "+
                IDFILIAL+ " INTEGER NOT NULL , " +
                IDPRODUTO + " TEXT, " +
                DESCRICAO + " TEXT NOT NULL,"+
                REFERENCIA +  " TEXT,"+
                EMBALAGEM + " TEXT,"+
                CODBAR + " TEXT,"+
                ESTOQUE + " DOUBLE,"+
                PRECO_VENDA + " DOUBLE,"+
                ATIVO + " BOOLEAN," +
                EXCLUIDO + " BOOLEAN,"+
                URL_IMAGEM + " TEXT ,"+
                "PRIMARY KEY( "+ ID +" )  );";
        return SQL;
    }

    public static final String TABELA = "PRODUTO";
    public static final String ID = "_id";
    public static final String IDPRODUTO = "IDPRODUTO";
    public static final String IDEMPRESA = "IDEMPRESA";
    public static final String IDFILIAL = "IDFILIAL";
    public static final String DESCRICAO = "DESCRICAO";
    public static final String REFERENCIA = "REFERENCIA";
    public static final String EMBALAGEM = "EMBALAGEM";
    public static final String PRECO_VENDA = "PRECO_VENDA";
    public static final String CODBAR = "CODBAR";
    public static final String ESTOQUE = "ESTOQUE";
    public static final String ATIVO = "ATIVO";
    public static final String EXCLUIDO = "EXCLUIDO";
    public static final String URL_IMAGEM = "URL_IMAGEM";

    public static final String[] COLUNAS = new String[]{ID, IDEMPRESA,IDFILIAL,IDPRODUTO, DESCRICAO, REFERENCIA, EMBALAGEM, PRECO_VENDA, CODBAR, ESTOQUE,ATIVO, EXCLUIDO, URL_IMAGEM };

}

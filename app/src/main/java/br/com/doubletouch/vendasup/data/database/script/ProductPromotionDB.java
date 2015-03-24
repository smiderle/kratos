package br.com.doubletouch.vendasup.data.database.script;

import java.util.Date;

/**
 * Created by LADAIR on 23/03/2015.
 */
public class ProductPromotionDB {

    private ProductPromotionDB() {
    }

    public static String create(){
        String SQL = "CREATE TABLE IF NOT EXISTS "+ TABELA+" (" +
                ID + " INTEGER NOT NULL , " +
                IDPRODUTO+ " INTEGER NOT NULL , "+
                IDEMPRESA+ " INTEGER NOT NULL , "+
                IDFILIAL+ " INTEGER NOT NULL , " +
                PRECO +  " DOUBLE,"+
                DATA_FINAL +  " LONG,"+
                DATA_INICIAL + " LONG,"+
                EXCLUIDO + " BOOLEAN,"+
                "PRIMARY KEY( "+ ID +" )  );";
        return SQL;
    }

    public static final String TABELA = "PRODUTO_PROMOCAO";
    public static final String ID = "_id";
    public static final String IDPRODUTO = "IDPRODUTO";
    public static final String IDEMPRESA = "IDEMPRESA";
    public static final String IDFILIAL = "IDFILIAL";
    public static final String PRECO = "PRECO";
    public static final String DATA_INICIAL = "DATA_INICIAL";
    public static final String DATA_FINAL = "DATA_FINAL";
    public static final String EXCLUIDO = "EXCLUIDO";

    public static final String[] COLUNAS = new String[]{ID, IDEMPRESA,IDFILIAL,IDPRODUTO,PRECO, DATA_INICIAL, DATA_FINAL, EXCLUIDO};



}

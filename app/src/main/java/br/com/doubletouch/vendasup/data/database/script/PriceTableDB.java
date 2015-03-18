package br.com.doubletouch.vendasup.data.database.script;

import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.PriceTable;

/**
 * Created by LADAIR on 17/02/2015.
 */
public final class PriceTableDB {

    private PriceTableDB() {
    }

    public static String create(){

        String SQL = "CREATE TABLE IF NOT EXISTS "+ TABELA+" (" +
                ID + " INTEGER NOT NULL , " +
                IDEMPRESA+ " INTEGER NOT NULL, " +
                IDFILIAL+ " INTEGER NOT NULL, " +
                IDTABELAPRECO+ " INTEGER , " +
                DESCRICAO + " TEXT, " +
                PERCENTAGE +" DOUBLE, "+
                ACRESCIMO +" BOOLEAN, "+
                ATIVO+" BOOLEAN, "+
                EXCLUIDO+" BOOLEAN, "+
                "PRIMARY KEY( "+ ID+" )  );";
        return SQL;
    }


    public static final String TABELA = "TABELA_PRECO";
    public static final String ID = "_id";
    public static final String IDEMPRESA = "IDEMPRESA";
    public static final String IDFILIAL = "IDFILIAL";
    public static final String IDTABELAPRECO = "IDTABELAPRECO";
    public static final String DESCRICAO = "DESCRICAO";
    public static final String PERCENTAGE = "PERCENTAGE";
    public static final String ACRESCIMO = "ACRESCIMO";
    public static final String ATIVO = "ATIVO";
    public static final String EXCLUIDO = "EXCLUIDO";

    public static final String[] COLUNAS = new String[]{ID, IDEMPRESA, IDFILIAL, IDTABELAPRECO, DESCRICAO, PERCENTAGE, ACRESCIMO, ATIVO, EXCLUIDO};
}

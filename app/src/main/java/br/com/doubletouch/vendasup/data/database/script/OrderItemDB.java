package br.com.doubletouch.vendasup.data.database.script;

/**
 * Created by LADAIR on 20/03/2015.
 */
public class OrderItemDB {

    public static final String TABELA = "PEDIDO_ITEM";
    public static final String ID = "_id";
    public static final String IDEMPRESA = "IDEMPRESA";
    public static final String IDFILIAL = "IDFILIAL";
    public static final String SEQUENCE = "SEQUENCE";
    public static final String IDPEDIDO = "IDPEDIDO";
    public static final String IDPRODUTO = "IDPRODUTO";
    public static final String QUANTIDADE = "QUANTIDADE";
    public static final String PRECO = "PRECO";
    public static final String OBSERVACAO = "OBSERVACAO";
    public static final String TABELA_PRECO = "TABELA_PRECO";



    public static final String[] COLUNAS = new String[]{ID, IDEMPRESA,IDFILIAL,SEQUENCE,IDPEDIDO, IDPRODUTO, QUANTIDADE, PRECO, TABELA_PRECO, OBSERVACAO };


    public static String create() {
        String SQL = "CREATE TABLE IF NOT EXISTS "+ TABELA+" (" +
                ID + " INTEGER NOT NULL , " +
                IDEMPRESA + " INTEGER NOT NULL , "+
                IDFILIAL + " INTEGER NOT NULL , "+
                SEQUENCE + " INTEGER NOT NULL , "+
                IDPEDIDO + " INTEGER NOT NULL , "+
                IDPRODUTO + " INTEGER NOT NULL , "+
                QUANTIDADE + " DOUBLE, "+
                PRECO + " DOUBLE, "+
                TABELA_PRECO + "  INTEGER, "+
                OBSERVACAO + " TEXT,"+
                "PRIMARY KEY( "+ ID+" )  );";
        return SQL;
    }






}

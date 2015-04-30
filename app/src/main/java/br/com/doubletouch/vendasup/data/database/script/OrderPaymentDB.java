package br.com.doubletouch.vendasup.data.database.script;

/**
 * Created by LADAIR on 20/03/2015.
 */
public class OrderPaymentDB {


    public static final String TABELA = "PEDIDO_PAGAMENTO";
    public static final String ID = "_id";
    public static final String IDEMPRESA = "IDEMPRESA";
    public static final String IDFILIAL = "IDFILIAL";
    public static final String SEQUENCE = "SEQUENCE";
    public static final String IDPEDIDO = "IDPEDIDO";
    public static final String DATA_VENCIMENTO = "DATA_VENCIMENTO";
    public static final String DATA_PAGAMENTO = "DATA_PAGAMENTO";
    public static final String VALOR = "VALOR";
    public static final String NUMERO_DOCUMENTO = "NUMERO_DOCUMENTO";
    public static final String OBSERVACAO = "OBSERVACAO";



    public static final String[] COLUNAS = new String[]{ID, IDEMPRESA,IDFILIAL,SEQUENCE,IDPEDIDO, DATA_VENCIMENTO, DATA_PAGAMENTO, VALOR, NUMERO_DOCUMENTO, OBSERVACAO };


    public static String create() {
        String SQL = "CREATE TABLE IF NOT EXISTS "+
                TABELA+" (" +
                ID + " INTEGER NOT NULL , " +
                IDEMPRESA + " INTEGER NOT NULL , "+
                IDFILIAL + " INTEGER NOT NULL , "+
                SEQUENCE + " INTEGER , "+
                IDPEDIDO + " INTEGER NOT NULL , "+
                DATA_VENCIMENTO + " LONG, "+
                DATA_PAGAMENTO + " LONG,"+
                VALOR + " DOUBLE, "+
                NUMERO_DOCUMENTO + " TEXT, "+
                OBSERVACAO + " TEXT,"+
                "PRIMARY KEY( "+ ID+" )  );";
        return SQL;
    }






}

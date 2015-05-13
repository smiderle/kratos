package br.com.doubletouch.vendasup.data.database.script;

/**
 * Created by LADAIR on 20/03/2015.
 */
public class OrderDB {
    public static final String TABELA = "PEDIDO";
    public static final String ID = "_id";
    public static final String IDEMPRESA = "IDEMPRESA";
    public static final String IDFILIAL = "IDFILIAL";
    public static final String IDCLIENTE = "IDCLIENTE";
    public static final String IDUSUARIO = "IDUSUARIO";
    public static final String IDPARCELAMENTO = "IDPARCELAMENTO";
    public static final String VALOR_LIQUIDO = "VALOR_LIQUIDO";
    public static final String VALOR_BRUTO = "VALOR_BRUTO";
    public static final String DESCONTO_TOTAL = "DESCONTO_TOTAL";
    public static final String OBSERVACAO = "OBSERVACAO";
    public static final String DATA_EMISSAO = "DATA_EMISSAO";
    public static final String FORMA_PAGAMENTO = "FORMA_PAGAMENTO";
    public static final String EXCLUIDO = "EXCLUIDO";
    public static final String SYNC_PENDENTE= "SYNC_PENDENTE";
    public static final String ID_MOBILE = "ID_MOBILE";
    public static final String TIPO = "TIPO";



    public static final String[] COLUNAS = new String[]{ID, IDEMPRESA,IDFILIAL,IDCLIENTE,IDUSUARIO, IDPARCELAMENTO, VALOR_LIQUIDO, VALOR_BRUTO, DESCONTO_TOTAL, DATA_EMISSAO,FORMA_PAGAMENTO, OBSERVACAO, EXCLUIDO, SYNC_PENDENTE, ID_MOBILE, TIPO };


    public static String create() {
        String SQL = "CREATE TABLE IF NOT EXISTS "+ TABELA+" (" +
                ID + " INTEGER NOT NULL , " +
                IDEMPRESA+ " INTEGER NOT NULL , "+
                IDFILIAL+ " INTEGER NOT NULL , "+
                IDCLIENTE+ " INTEGER NOT NULL , "+
                IDUSUARIO+ " INTEGER NOT NULL , "+
                IDPARCELAMENTO+ " INTEGER NOT NULL , "+
                VALOR_LIQUIDO + " DOUBLE, "+
                VALOR_BRUTO + " DOUBLE, "+
                DESCONTO_TOTAL + " DOUBLE, "+
                DATA_EMISSAO+ " LONG, "+
                FORMA_PAGAMENTO + " INTEGER, "+
                OBSERVACAO + " TEXT,"+
                ID_MOBILE + " INTEGER,"+
                EXCLUIDO + " BOOLEAN,"+
                SYNC_PENDENTE + " BOOLEAN,"+
                TIPO + " INTEGER, "+
                "PRIMARY KEY( "+ ID+" )  );";
        return SQL;
    }






}

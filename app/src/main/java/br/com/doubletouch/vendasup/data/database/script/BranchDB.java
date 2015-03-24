package br.com.doubletouch.vendasup.data.database.script;

/**
 * Created by LADAIR on 23/03/2015.
 */
public class BranchDB {

    private BranchDB() {
    }

    public static String create() {
        String SQL = "CREATE TABLE IF NOT EXISTS "+ TABELA+" (" +
                ID + " INTEGER NOT NULL , " +
                IDEMPRESA+ " INTEGER NOT NULL , "+
                RAZAO_SOCIAL + " TEXT, " +
                NOME_FANTASIA+ " TEXT, " +
                FONE_FAX + " TEXT, " +
                FONE + " TEXT, " +
                RUA + " TEXT, " +
                BAIRRO + " TEXT, " +
                NUMERO + " TEXT, " +
                CEP + " TEXT, " +
                EMAIL + " TEXT, " +
                WEB_SITE+ " TEXT,"+
                GERENTE + " TEXT,"+
                CNPJ + " TEXT,"+
                COMPLEMENTO + " TEXT,"+
                DESCONTO_MAXIMO + " DOUBLE,"+
                NOTIFICAO_EMAIL + " BOOLEAN,"+
                MOSTRAR_ESTOQUE + " BOOLEAN,"+
                PERMITE_CADASTRO_CLIENTE + " BOOLEAN,"+
                PERMITE_ESTOQUE_NEGATIVO + " BOOLEAN,"+
                ENVIO_EMAIL_CLIENTE + " BOOLEAN,"+
                ACAO_LIMITE_CREDITO + " TEXT,"+
                ACAO_TITULO_VENCIDO + " TEXT,"+
                "PRIMARY KEY( "+ ID + "," + IDEMPRESA+ " )  );";
        return SQL;
    }

    public static final String TABELA = "FILIAL";
    public static final String ID = "_id";
    public static final String IDEMPRESA = "IDEMPRESA";
    public static final String RAZAO_SOCIAL = "RAZAO_SOCIAL";
    public static final String NOME_FANTASIA = "NOME_FANTASIA";
    public static final String FONE_FAX = "FONE_FAX";
    public static final String FONE = "FONE";
    public static final String RUA = "RUA";
    public static final String BAIRRO = "BAIRRO";
    public static final String NUMERO = "NUMERO";
    public static final String CEP = "CEP";
    public static final String EMAIL = "EMAIL";
    public static final String WEB_SITE = "WEB_SITE";
    public static final String GERENTE = "GERENTE";
    public static final String CNPJ = "CNPJ";
    public static final String COMPLEMENTO = "COMPLEMENTO";
    public static final String DESCONTO_MAXIMO = "DESCONTO_MAXIMO";
    public static final String NOTIFICAO_EMAIL = "NOTIFICAO_EMAIL";
    public static final String MOSTRAR_ESTOQUE = "MOSTRAR_ESTOQUE";
    public static final String PERMITE_ESTOQUE_NEGATIVO = "PERMITE_ESTOQUE_NEGATIVO";
    public static final String PERMITE_CADASTRO_CLIENTE = "PERMITE_CADASTRO_CLIENTE";
    public static final String ENVIO_EMAIL_CLIENTE = "ENVIO_EMAIL_CLIENTE";
    public static final String ACAO_TITULO_VENCIDO = "ACAO_TITULO_VENCIDO";
    public static final String ACAO_LIMITE_CREDITO = "ACAO_LIMITE_CREDITO";


    public static final String[] COLUNAS = new String[]{ID, IDEMPRESA, RAZAO_SOCIAL,NOME_FANTASIA, CEP, COMPLEMENTO,FONE_FAX, FONE,RUA,BAIRRO,NUMERO,EMAIL,WEB_SITE, GERENTE, CNPJ, DESCONTO_MAXIMO,
            NOTIFICAO_EMAIL, MOSTRAR_ESTOQUE, PERMITE_ESTOQUE_NEGATIVO, PERMITE_CADASTRO_CLIENTE, ENVIO_EMAIL_CLIENTE, ACAO_TITULO_VENCIDO, ACAO_LIMITE_CREDITO};

}

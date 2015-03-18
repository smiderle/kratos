package br.com.doubletouch.vendasup.data.database.script;

/**
 * Created by LADAIR on 16/03/2015.
 */
public class InstallmentDB {

    public InstallmentDB() {
    }

    public static String create(){

        String SQL = "CREATE TABLE IF NOT EXISTS "+ TABELA+" (" +
                ID + " INTEGER NOT NULL , " +
                IDEMPRESA+ " INTEGER NOT NULL , "+
                IDFILIAL+ " INTEGER NOT NULL , " +
                IDPARCELAMENTO + " TEXT, " +
                DESCRICAO + " TEXT,"+
                DIAS + " TEXT,"+
                JUROS + " DOUBLE,"+
                ATIVO + " BOOLEAN,"+
                EXCLUIDO + " BOOLEAN,"+
                "PRIMARY KEY( "+ ID+" )  );";
        return SQL;
    }



    public static final String TABELA = "PARCELAMENTO";
    public static final String ID = "_id";
    public static final String IDEMPRESA = "IDEMPRESA";
    public static final String IDFILIAL = "IDFILIAL";
    public static final String IDPARCELAMENTO = "IDPARCELAMENTO";
    public static final String DESCRICAO = "DESCRICAO";
    public static final String DIAS = "DIAS";
    public static final String JUROS = "JUROS";
    public static final String ATIVO = "ATIVO";
    public static final String EXCLUIDO = "EXCLUIDO";


    public static final String[] COLUNAS = new String[]{ID, IDEMPRESA,IDFILIAL,ATIVO, EXCLUIDO, IDPARCELAMENTO, DESCRICAO, DIAS, JUROS};

}

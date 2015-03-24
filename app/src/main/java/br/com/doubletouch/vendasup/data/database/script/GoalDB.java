package br.com.doubletouch.vendasup.data.database.script;

/**
 * Created by LADAIR on 23/03/2015.
 */
public class GoalDB {

    private Integer month;

    private Integer year;

    private Integer yearMonth;

    private Double value;



    public static String create(){
        String SQL = "CREATE TABLE IF NOT EXISTS "+ TABELA+" (" +
                ID + " INTEGER NOT NULL , " +
                IDUSUARIO+ " INTEGER NOT NULL , "+
                IDEMPRESA+ " INTEGER NOT NULL , "+
                IDFILIAL+ " INTEGER NOT NULL , " +
                ANO_MES +  " INTEGER,"+
                MES +  " INTEGER,"+
                ANO + " INTEGER,"+
                VALOR + " DOUBLE,"+
                "PRIMARY KEY( "+ ID +" )  );";
        return SQL;
    }

    public static final String TABELA = "META";
    public static final String ID = "_id";
    public static final String IDUSUARIO = "IDUSUARIO";
    public static final String IDEMPRESA = "IDEMPRESA";
    public static final String IDFILIAL = "IDFILIAL";
    public static final String MES = "MES";
    public static final String ANO = "ANO";
    public static final String ANO_MES = "ANO_MES";
    public static final String VALOR = "VALOR";


    public static final String[] COLUNAS = new String[]{ID, IDEMPRESA,IDFILIAL,IDUSUARIO,MES, ANO,ANO_MES, VALOR};

}

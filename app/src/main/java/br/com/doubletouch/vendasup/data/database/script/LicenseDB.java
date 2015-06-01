package br.com.doubletouch.vendasup.data.database.script;

/**
 * Created by LADAIR on 20/05/2015.
 */
public class LicenseDB {



    public static String create(){

        String SQL = "CREATE TABLE IF NOT EXISTS "+ TABELA+" (" +
                ID + " INTEGER NOT NULL , " +
                SERIAL+ " TEXT , "+
                USUARIO+ " INTEGER , " +
                KEY+ " TEXT , " +
                TIPO_VERSAO + " INTEGER, " +
                EXPIRADO + " BOOLEAN, " +
                DATA_EXPIRACAO + " LONG,"+
                DATA_ALTERACAO + " LONG,"+
                DATA_CRIACAO + " LONG,"+
                "PRIMARY KEY( "+ ID+" )  );";
        return SQL;
    }

    public static final String TABELA = "LICENSA";
    public static final String ID = "_id";
    public static final String SERIAL = "SERIAL";
    public static final String USUARIO = "USUARIO";
    public static final String KEY = "KEY";
    public static final String TIPO_VERSAO = "TIPO_VERSAO";
    public static final String DATA_EXPIRACAO = "DATA_EXPIRACAO";
    public static final String DATA_ALTERACAO = "DATA_ALTERACAO";
    public static final String DATA_CRIACAO = "DATA_CRIACAO";
    public static final String EXPIRADO = "EXPIRADO";


    public static final String[] COLUNAS = new String[]{ID, SERIAL,USUARIO,TIPO_VERSAO,KEY, DATA_EXPIRACAO, DATA_ALTERACAO, DATA_CRIACAO, EXPIRADO};

}

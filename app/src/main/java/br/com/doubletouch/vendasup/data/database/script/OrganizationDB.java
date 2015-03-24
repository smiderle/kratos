package br.com.doubletouch.vendasup.data.database.script;

/**
 * Created by LADAIR on 20/03/2015.
 */
public class OrganizationDB {

    public static String create(){
        String SQL = "CREATE TABLE IF NOT EXISTS "+ TABELA+" (" +
                ID + " INTEGER NOT NULL , " +
                NOME+ " TEXT NOT NULL , " +
                "PRIMARY KEY( "+ ID+" )  );";
        return SQL;
    }


    public static final String TABELA = "EMPRESA";
    public static final String ID = "_id";
    public static final String NOME = "NOME";



    public static final String[] COLUNAS = new String[]{ID,NOME};







}

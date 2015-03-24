package br.com.doubletouch.vendasup.data.database.script;

/**
 * Created by LADAIR on 20/03/2015.
 */
public class UserBranchDB {

    public static String create(){
        String SQL = "CREATE TABLE IF NOT EXISTS "+ TABELA+" (" +
                IDFILIAL+ " INTEGER NOT NULL , "+
                IDEMPRESA+ " INTEGER NOT NULL , "+
                IDUSUARIO+ " TEXT NOT NULL , " +
                ATIVO + " BOOLEAN, " +

                "PRIMARY KEY( "+ IDFILIAL+","+ IDEMPRESA+","+ IDUSUARIO +" )  );";
        return SQL;
    }


    public static final String TABELA = "USUARIO_FILIAL";
    public static final String IDFILIAL = "IDFILIAL";
    public static final String IDEMPRESA = "IDEMPRESA";
    public static final String IDUSUARIO = "IDUSUARIO";
    public static final String ATIVO = "ATIVO";


    public static final String[] COLUNAS = new String[]{IDFILIAL,IDEMPRESA,IDUSUARIO, ATIVO};







}

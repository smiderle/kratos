package br.com.doubletouch.vendasup.data.database.script;

import br.com.doubletouch.vendasup.data.entity.Sincronizacao;

/**
 * Created by LADAIR on 29/01/2015.
 */
public class SincronizacaoDB {

    private SincronizacaoDB(){}

    public static String create(){
        String SQL = "CREATE TABLE IF NOT EXISTS "+TABELA+" (" +
                NOME_TABELA + " TEXT NOT NULL,"+
                ULTIMA_SINCRONIZACAO + " LONG,"+
                "PRIMARY KEY( "+ NOME_TABELA +"  )  );";
        return SQL;
    }


    public static final String TABELA = "SINCRONIZACAO";
    public static final String NOME_TABELA = "NOME_TABELA";
    public static final String ULTIMA_SINCRONIZACAO = "ULTIMA_SINCRONIZACAO";

    public static final String[] COLUNAS = new String[]{ NOME_TABELA, ULTIMA_SINCRONIZACAO };
}

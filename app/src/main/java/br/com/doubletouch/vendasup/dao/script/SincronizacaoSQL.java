package br.com.doubletouch.vendasup.dao.script;

import br.com.doubletouch.vendasup.data.entity.Sincronizacao;

/**
 * Created by LADAIR on 29/01/2015.
 */
public class SincronizacaoSQL {

    public static String create(){
        String SQL = "CREATE TABLE IF NOT EXISTS "+Sincronizacao.SincronizacaoDB.TABELA+" (" +
                Sincronizacao.SincronizacaoDB.NOME_TABELA + " TEXT NOT NULL,"+
                Sincronizacao.SincronizacaoDB.ULTIMA_SINCRONIZACAO + " LONG,"+
                "PRIMARY KEY( "+ Sincronizacao.SincronizacaoDB.NOME_TABELA +"  )  );";
        return SQL;
    }
}

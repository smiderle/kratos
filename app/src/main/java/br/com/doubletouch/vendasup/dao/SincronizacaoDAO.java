package br.com.doubletouch.vendasup.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.doubletouch.vendasup.data.entity.Sincronizacao;

/**
 *
 * Created by LADAIR on 09/02/2015.
 */
public class SincronizacaoDAO {
    SQLiteDatabase db;

    public SincronizacaoDAO(){
        db = SQLiteHelper.getInstance().getWritableDatabase();
    }

    public void saveOrUpdate( Sincronizacao sincronizacao ){

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT OR REPLACE INTO ");
        sb.append(Sincronizacao.SincronizacaoDB.TABELA);
        sb.append("(");
        sb.append(Sincronizacao.SincronizacaoDB.NOME_TABELA);
        sb.append(",");
        sb.append(Sincronizacao.SincronizacaoDB.ULTIMA_SINCRONIZACAO);
        sb.append(")");
        sb.append("VALUES(?,?)");

        String[] values = {
                String.valueOf( sincronizacao.getTableName() ),
                String.valueOf( sincronizacao.getLastSync() )
        };

        db.execSQL(sb.toString(), values);
    }

    public Sincronizacao findByTableName(String tableName) {
        String where = Sincronizacao.SincronizacaoDB.NOME_TABELA + " = ?";
        Cursor cursor = db.query(Sincronizacao.SincronizacaoDB.TABELA, Sincronizacao.SincronizacaoDB.COLUNAS, where, new String[]{ tableName }, null, null, null, null);
        return find(cursor);
    }

    private Sincronizacao find(Cursor cursor){
        Sincronizacao sincronizacao = null;
        if(cursor.moveToFirst()) {
            int idxIdUltimaSincronizacao = cursor.getColumnIndex(Sincronizacao.SincronizacaoDB.ULTIMA_SINCRONIZACAO);
            int idxIdNomeTabela = cursor.getColumnIndex(Sincronizacao.SincronizacaoDB.NOME_TABELA);

            sincronizacao = new Sincronizacao();
            sincronizacao.setLastSync( cursor.getLong(idxIdUltimaSincronizacao) );
            sincronizacao.setTableName(cursor.getString(idxIdNomeTabela));

        }
        cursor.close();

        return sincronizacao;
    }

}

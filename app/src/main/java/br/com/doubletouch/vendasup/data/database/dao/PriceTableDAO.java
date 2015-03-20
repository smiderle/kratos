package br.com.doubletouch.vendasup.data.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.doubletouch.vendasup.dao.SQLiteHelper;
import br.com.doubletouch.vendasup.data.database.script.PriceTableDB;
import br.com.doubletouch.vendasup.data.entity.PriceTable;

/**
 * Created by LADAIR on 10/03/2015.
 */
public class PriceTableDAO {

    SQLiteDatabase db;

    public PriceTableDAO(){
        db = SQLiteHelper.getInstance().getWritableDatabase();
    }

    public PriceTable get(Integer id) {
        PriceTable priceTable = null;
        String where = PriceTableDB.ID+" = ?  AND " + PriceTableDB.EXCLUIDO +" = ? ";
        Cursor c = db.query(PriceTableDB.TABELA, PriceTableDB.COLUNAS, where, new String[]{String.valueOf(id), "0"}, null, null, null, null);

        if(c.moveToFirst()){
            priceTable = getByCursor(c);
        }

        c.close();
        return priceTable;
    }

    public List<PriceTable> getAll(Integer branchId) {
        ArrayList<PriceTable> tabelas = new ArrayList<>();
        String where = PriceTableDB.IDFILIAL + " = ? AND " + PriceTableDB.EXCLUIDO +" = ?";
        String orderBy = PriceTableDB.ID +" ASC ";
        Cursor c = db.query(PriceTableDB.TABELA, PriceTableDB.COLUNAS, where, new String[]{String.valueOf(branchId), "0"}, null, null, orderBy, null);

        if(c.moveToFirst()){
            do {
                tabelas.add(getByCursor(c));
            } while (c.moveToNext());
        }
        c.close();
        return  tabelas;
    }

    public void insert(PriceTable priceTable) {
        db.insertWithOnConflict(PriceTableDB.TABELA, null, getContentValues(priceTable), SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void insert(List<PriceTable> tabelas) {
        db.beginTransaction();
        for(PriceTable tabelaPreco : tabelas){
            db.insertWithOnConflict(PriceTableDB.TABELA, null, getContentValues(tabelaPreco), SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void update(PriceTable priceTable) {
        String where = PriceTableDB.ID +" = ? ";
        db.update(PriceTableDB.TABELA, getContentValues(priceTable), where, new String[]{ String.valueOf(priceTable.getID()) });
    }

    public void delete(PriceTable priceTable) {
        new UnsupportedOperationException("Não implementado");
    }

    private ContentValues getContentValues( PriceTable priceTable){
        ContentValues cv = new ContentValues();
        cv.put(PriceTableDB.ATIVO, priceTable.isActive());
        cv.put(PriceTableDB.ACRESCIMO, priceTable.isIncrease());
        cv.put(PriceTableDB.DESCRICAO, priceTable.getDescription());
        cv.put(PriceTableDB.EXCLUIDO, priceTable.isExcluded());
        cv.put(PriceTableDB.ID, priceTable.getID());
        cv.put(PriceTableDB.IDEMPRESA, priceTable.getOrganizationID());
        cv.put(PriceTableDB.IDFILIAL, priceTable.getBranchID());
        cv.put(PriceTableDB.IDTABELAPRECO, priceTable.getPriceTableID());
        cv.put(PriceTableDB.PERCENTAGE, priceTable.getPercentage());
        return cv;
    }

    private PriceTable getByCursor(Cursor c){
        int idxId = c.getColumnIndex(PriceTableDB.ID);
        int idxIdTabelaPreco = c.getColumnIndex(PriceTableDB.IDTABELAPRECO);
        int idxIdEmpresa = c.getColumnIndex(PriceTableDB.IDEMPRESA);
        int idxIdFilial = c.getColumnIndex(PriceTableDB.IDFILIAL);
        int idxIdAcrescimo = c.getColumnIndex(PriceTableDB.ACRESCIMO);
        int idxAtivo = c.getColumnIndex(PriceTableDB.ATIVO);
        int idxIdDescricao = c.getColumnIndex(PriceTableDB.DESCRICAO);
        int idxIdPercentage = c.getColumnIndex(PriceTableDB.PERCENTAGE);
        int idxExcluido = c.getColumnIndex(PriceTableDB.EXCLUIDO);

        PriceTable priceTable = new PriceTable();
        priceTable.setActive( c.getInt( idxAtivo )== 1);
        priceTable.setExcluded(c.getInt(idxExcluido) == 1);
        priceTable.setID(c.getInt(idxId));
        priceTable.setPriceTableID(c.getInt(idxIdTabelaPreco));
        priceTable.setOrganizationID(c.getInt(idxIdEmpresa));
        priceTable.setBranchID(c.getInt(idxIdFilial));
        priceTable.setIncrease(c.getInt(idxIdAcrescimo) == 1);
        priceTable.setDescription(c.getString(idxIdDescricao));
        priceTable.setPercentage(c.getDouble(idxIdPercentage));


        return priceTable;
    }
}

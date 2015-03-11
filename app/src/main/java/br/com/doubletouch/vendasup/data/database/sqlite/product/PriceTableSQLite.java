package br.com.doubletouch.vendasup.data.database.sqlite.product;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.doubletouch.vendasup.dao.SQLiteHelper;
import br.com.doubletouch.vendasup.data.database.PriceTablePersistence;
import br.com.doubletouch.vendasup.data.entity.PriceTable;

/**
 * Created by LADAIR on 10/03/2015.
 */
public class PriceTableSQLite implements PriceTablePersistence {

    SQLiteDatabase db;

    public PriceTableSQLite(){
        db = SQLiteHelper.getInstance().getWritableDatabase();
    }

    @Override
    public PriceTable get(Integer id) {
        PriceTable priceTable = null;
        String where = PriceTable.PriceTableDB.ID+" = ? ";
        Cursor c = db.query(PriceTable.PriceTableDB.TABELA, PriceTable.PriceTableDB.COLUNAS, where, new String[]{String.valueOf(id)}, null, null, null, null);

        if(c.moveToFirst()){
            priceTable = getByCursor(c);
        }

        c.close();
        return priceTable;
    }

    @Override
    public List<PriceTable> getAll(Integer branchId) {
        ArrayList<PriceTable> tabelas = new ArrayList<>();
        String where = PriceTable.PriceTableDB.IDFILIAL + " = ? ";
        Cursor c = db.query(PriceTable.PriceTableDB.TABELA, PriceTable.PriceTableDB.COLUNAS, where, new String[]{String.valueOf(branchId)}, null, null, null, null);

        if(c.moveToFirst()){
            do {
                tabelas.add(getByCursor(c));
            } while (c.moveToNext());
        }
        c.close();
        return  tabelas;
    }

    @Override
    public void insert(PriceTable priceTable) {
        db.insertWithOnConflict(PriceTable.PriceTableDB.TABELA, null, getContentValues(priceTable), SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public void insert(List<PriceTable> tabelas) {
        db.beginTransaction();
        for(PriceTable tabelaPreco : tabelas){
            db.insertWithOnConflict(PriceTable.PriceTableDB.TABELA, null, getContentValues(tabelaPreco), SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    @Override
    public void update(PriceTable priceTable) {
        String where = PriceTable.PriceTableDB.ID +" = ? ";
        db.update(PriceTable.PriceTableDB.TABELA, getContentValues(priceTable), where, new String[]{ String.valueOf(priceTable.getID()) });
    }

    @Override
    public void delete(PriceTable priceTable) {
        new UnsupportedOperationException("Não implementado");
    }

    private ContentValues getContentValues( PriceTable priceTable){
        ContentValues cv = new ContentValues();
        cv.put(PriceTable.PriceTableDB.ATIVO, priceTable.isActive());
        cv.put(PriceTable.PriceTableDB.ACRESCIMO, priceTable.isIncrease());
        cv.put(PriceTable.PriceTableDB.DESCRICAO, priceTable.getDescription());
        cv.put(PriceTable.PriceTableDB.EXCLUIDO, priceTable.isExcluded());
        cv.put(PriceTable.PriceTableDB.ID, priceTable.getID());
        cv.put(PriceTable.PriceTableDB.IDEMPRESA, priceTable.getOrganizationID());
        cv.put(PriceTable.PriceTableDB.IDFILIAL, priceTable.getBranchID());
        cv.put(PriceTable.PriceTableDB.IDTABELAPRECO, priceTable.getPriceTableID());
        cv.put(PriceTable.PriceTableDB.PERCENTAGE, priceTable.getPercentage());
        return cv;
    }

    private PriceTable getByCursor(Cursor c){
        int idxId = c.getColumnIndex(PriceTable.PriceTableDB.ID);
        int idxIdTabelaPreco = c.getColumnIndex(PriceTable.PriceTableDB.IDTABELAPRECO);
        int idxIdEmpresa = c.getColumnIndex(PriceTable.PriceTableDB.IDEMPRESA);
        int idxIdFilial = c.getColumnIndex(PriceTable.PriceTableDB.IDFILIAL);
        int idxIdAcrescimo = c.getColumnIndex(PriceTable.PriceTableDB.ACRESCIMO);
        int idxAtivo = c.getColumnIndex(PriceTable.PriceTableDB.ATIVO);
        int idxIdDescricao = c.getColumnIndex(PriceTable.PriceTableDB.DESCRICAO);
        int idxIdPercentage = c.getColumnIndex(PriceTable.PriceTableDB.PERCENTAGE);
        int idxExcluido = c.getColumnIndex(PriceTable.PriceTableDB.EXCLUIDO);

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

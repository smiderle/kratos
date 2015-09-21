package br.com.doubletouch.vendasup.data.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.doubletouch.vendasup.dao.SQLiteHelper;
import br.com.doubletouch.vendasup.data.database.script.CustomerDB;
import br.com.doubletouch.vendasup.data.database.script.InstallmentDB;
import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.Installment;


/**
 * Created by LADAIR on 10/03/2015.
 */
public class InstallmentDAO {

    SQLiteDatabase db;

    public InstallmentDAO(){
        db = SQLiteHelper.getInstance().getWritableDatabase();
    }

    public Installment get(Integer id) {
        Installment installment = null;
        String where = InstallmentDB.ID+" = ? ";
        Cursor c = db.query(InstallmentDB.TABELA, InstallmentDB.COLUNAS, where, new String[]{String.valueOf(id)}, null, null, null, null);

        if(c.moveToFirst()){
            installment = getByCursor(c);
        }

        c.close();
        return installment;
    }

    public List<Installment> getAll(Integer branchId) {
        ArrayList<Installment> tabelas = new ArrayList<>();
        String where = InstallmentDB.IDFILIAL + " = ?  AND (" + InstallmentDB.EXCLUIDO +" = ? OR " + InstallmentDB.EXCLUIDO +" IS NULL )";
        String orderBy = InstallmentDB.ID +" ASC ";
        Cursor c = db.query(InstallmentDB.TABELA, InstallmentDB.COLUNAS, where, new String[]{String.valueOf(branchId), "0"}, null, null, orderBy, null);

        if(c.moveToFirst()){
            do {
                tabelas.add(getByCursor(c));
            } while (c.moveToNext());
        }
        c.close();
        return  tabelas;
    }

    public void insert(Installment installment) {
        db.insertWithOnConflict(InstallmentDB.TABELA, null, getContentValues(installment), SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void insert(List<Installment> parcelas) {
        db.beginTransaction();
        for(Installment installment : parcelas){
            db.insertWithOnConflict(InstallmentDB.TABELA, null, getContentValues(installment), SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void update(Installment installment) {
        String where = InstallmentDB.ID +" = ? ";
        db.update(InstallmentDB.TABELA, getContentValues(installment), where, new String[]{String.valueOf(installment.getID())});
    }

    public void delete(Installment installment) {
        new UnsupportedOperationException("Não implementado");
    }

    public Integer count() {
        Integer count = 0;

        String sql = "SELECT COUNT("+ InstallmentDB.ID +") AS quantidade FROM " + InstallmentDB.TABELA + " WHERE "+ InstallmentDB.EXCLUIDO + " = 0 OR  "+ InstallmentDB.EXCLUIDO + " IS NULL " ;

        Cursor cursor = db.rawQuery( sql, null );

        if(cursor.moveToFirst()){
            count = cursor.getInt( 0 );
        }

        return count;
    }

    public Integer min() {
        Integer min = 0;

        String sql = "SELECT MIN("+ InstallmentDB.ID +") AS max_id FROM " + InstallmentDB.TABELA;

        Cursor cursor = db.rawQuery( sql, null );

        if(cursor.moveToFirst()){
            min = cursor.getInt( 0 );
        }

        return min;
    }


    public void updateByIdMobile( Installment installment) {

        String sql = "UPDATE " + InstallmentDB.TABELA + " SET " + InstallmentDB.ID +" = " + installment.getID() +"," + InstallmentDB.SYNC_PENDENTE+" = 0 "+
                "WHERE "+ InstallmentDB.ID_MOBILE +" = " + installment.getIdMobile();

        db.execSQL(sql);
    }



    public List<Installment> getAllSyncPendendeNovos(Integer branchId) {
        ArrayList<Installment> customers = new ArrayList<>();

        String where = InstallmentDB.IDFILIAL+" = ? AND  " +InstallmentDB.SYNC_PENDENTE +" = 1 AND " + InstallmentDB.ID +" < 0";
        Cursor c = db.query(InstallmentDB.TABELA, InstallmentDB.COLUNAS, where, new String[]{String.valueOf(branchId)}, null, null, null, null);

        if(c.moveToFirst()){
            do {
                customers.add(getByCursor(c));
            } while (c.moveToNext());
        }
        c.close();
        return  customers;
    }

    public List<Installment> getAllSyncPendendeAtualizados(Integer branchId) {
        ArrayList<Installment> customers = new ArrayList<>();

        String where = InstallmentDB.IDFILIAL+" = ? AND  " +InstallmentDB.SYNC_PENDENTE +" = 1 AND " + InstallmentDB.ID +" > 0";
        Cursor c = db.query(InstallmentDB.TABELA, InstallmentDB.COLUNAS, where, new String[]{String.valueOf(branchId)}, null, null, null, null);

        if(c.moveToFirst()){
            do {
                customers.add(getByCursor(c));
            } while (c.moveToNext());
        }
        c.close();
        return  customers;
    }


    private ContentValues getContentValues( Installment installment ){
        ContentValues cv = new ContentValues();
        cv.put(InstallmentDB.ATIVO,  installment.isActive());
        cv.put(InstallmentDB.JUROS, installment.getTax());
        cv.put(InstallmentDB.DESCRICAO, installment.getDescription());
        cv.put(InstallmentDB.EXCLUIDO, installment.isExcluded());
        cv.put(InstallmentDB.ID, installment.getID());
        cv.put(InstallmentDB.IDEMPRESA, installment.getOrganizationID());
        cv.put(InstallmentDB.IDFILIAL, installment.getBranchID());
        cv.put(InstallmentDB.DIAS, installment.getInstallmentsDays());
        cv.put(InstallmentDB.ID_MOBILE, installment.getIdMobile());
        cv.put(InstallmentDB.SYNC_PENDENTE, installment.isSetSyncPending());
        return cv;
    }

    private Installment getByCursor(Cursor c){
        int idxId = c.getColumnIndex(InstallmentDB.ID);
        int idxIdParcelamento = c.getColumnIndex(InstallmentDB.IDPARCELAMENTO);
        int idxIdEmpresa = c.getColumnIndex(InstallmentDB.IDEMPRESA);
        int idxIdFilial = c.getColumnIndex(InstallmentDB.IDFILIAL);
        int idxIdJuros = c.getColumnIndex(InstallmentDB.JUROS);
        int idxAtivo = c.getColumnIndex(InstallmentDB.ATIVO);
        int idxIdDescricao = c.getColumnIndex(InstallmentDB.DESCRICAO);
        int idxIdDias = c.getColumnIndex(InstallmentDB.DIAS);
        int idxExcluido = c.getColumnIndex(InstallmentDB.EXCLUIDO);
        int idxSync = c.getColumnIndex(InstallmentDB.SYNC_PENDENTE);
        int idxIdMobile = c.getColumnIndex(InstallmentDB.ID_MOBILE);

        Installment installment = new Installment();
        installment.setActive( c.getInt( idxAtivo )== 1);
        installment.setExcluded(c.getInt(idxExcluido) == 1);
        installment.setSetSyncPending(c.getInt(idxSync) == 1);
        installment.setID(c.getInt(idxId));
        installment.setIdMobile(c.getInt(idxIdMobile));
        installment.setInstallmentID(c.getInt(idxIdParcelamento));
        installment.setOrganizationID(c.getInt(idxIdEmpresa));
        installment.setBranchID(c.getInt(idxIdFilial));
        installment.setTax(c.getDouble(idxIdJuros));
        installment.setDescription(c.getString(idxIdDescricao));
        installment.setInstallmentsDays(c.getString(idxIdDias));


        return installment;
    }
}

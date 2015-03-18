package br.com.doubletouch.vendasup.data.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.doubletouch.vendasup.dao.SQLiteHelper;
import br.com.doubletouch.vendasup.data.database.script.InstallmentDB;
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
        String where = InstallmentDB.ID+" = ? AND " + InstallmentDB.EXCLUIDO +" = ?";
        Cursor c = db.query(InstallmentDB.TABELA, InstallmentDB.COLUNAS, where, new String[]{String.valueOf(id), "0"}, null, null, null, null);

        if(c.moveToFirst()){
            installment = getByCursor(c);
        }

        c.close();
        return installment;
    }

    public List<Installment> getAll(Integer branchId) {
        ArrayList<Installment> tabelas = new ArrayList<>();
        String where = InstallmentDB.IDFILIAL + " = ?  AND " + InstallmentDB.EXCLUIDO +" = ?";
        Cursor c = db.query(InstallmentDB.TABELA, InstallmentDB.COLUNAS, where, new String[]{String.valueOf(branchId), "0"}, null, null, null, null);

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
        db.update(InstallmentDB.TABELA, getContentValues(installment), where, new String[]{ String.valueOf(installment.getID()) });
    }

    public void delete(Installment installment) {
        new UnsupportedOperationException("Não implementado");
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
        cv.put(InstallmentDB.IDPARCELAMENTO, installment.getID());
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

        Installment installment = new Installment();
        installment.setActive( c.getInt( idxAtivo )== 1);
        installment.setExcluded(c.getInt(idxExcluido) == 1);
        installment.setID(c.getInt(idxId));
        installment.setInstallmentID(c.getInt(idxIdParcelamento));
        installment.setOrganizationID(c.getInt(idxIdEmpresa));
        installment.setBranchID(c.getInt(idxIdFilial));
        installment.setTax(c.getDouble(idxIdJuros));
        installment.setDescription(c.getString(idxIdDescricao));
        installment.setInstallmentsDays(c.getString(idxIdDias));


        return installment;
    }
}

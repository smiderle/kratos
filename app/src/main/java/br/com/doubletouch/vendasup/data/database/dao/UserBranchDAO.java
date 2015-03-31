package br.com.doubletouch.vendasup.data.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.doubletouch.vendasup.dao.SQLiteHelper;
import br.com.doubletouch.vendasup.data.entity.BranchOffice;
import br.com.doubletouch.vendasup.data.database.script.UserBranchDB;
import br.com.doubletouch.vendasup.data.entity.Organization;
import br.com.doubletouch.vendasup.data.entity.UserBranchOffice;


/**
 * Created by LADAIR on 17/02/2015.
 */
public class UserBranchDAO {

    public static final String LIMIT = "50";

    SQLiteDatabase db;

    public UserBranchDAO(){
        db = SQLiteHelper.getInstance().getWritableDatabase();
    }


    public UserBranchOffice get(Integer branchId, Integer organizarionId,Integer userId ) {
        UserBranchOffice user = null;
        String where = UserBranchDB.IDFILIAL+" = ? AND " + UserBranchDB.IDEMPRESA+" = ? AND "+UserBranchDB.IDUSUARIO+" = ?  ";
        Cursor c = db.query(UserBranchDB.TABELA, UserBranchDB.COLUNAS, where, new String[]{ String.valueOf(branchId), String.valueOf(organizarionId), String.valueOf(userId) }, null, null, null, null);

        if(c.moveToFirst()){
            user = getByCursor(c);
        }

        c.close();
        return user;
    }



    public List<UserBranchOffice> getAll() {
        {

            ArrayList<UserBranchOffice> users = new ArrayList<>();
            Cursor c = db.query(UserBranchDB.TABELA, UserBranchDB.COLUNAS, null,null , null, null, null, LIMIT);

            if(c.moveToFirst()){
                do {
                    users.add(getByCursor(c));
                } while (c.moveToNext());
            }
            c.close();
            return  users;
        }
    }



    public void insert(UserBranchOffice user) {
        db.insertWithOnConflict(UserBranchDB.TABELA, null, getContentValues(user), SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void insert(List<UserBranchOffice> users) {
        db.beginTransaction();
        for(UserBranchOffice user : users){
            db.insertWithOnConflict(UserBranchDB.TABELA, null, getContentValues(user), SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void delete(UserBranchOffice user) {
        throw new UnsupportedOperationException("Não implementado.");
    }


    private ContentValues getContentValues( UserBranchOffice user ) {
        ContentValues cv = new ContentValues();

        cv.put(UserBranchDB.ATIVO, user.isEnable());
        cv.put(UserBranchDB.IDFILIAL, user.getBranchOffice().getBranchOfficeID());
        cv.put(UserBranchDB.IDEMPRESA, user.getBranchOffice().getOrganization().getOrganizationID());
        cv.put(UserBranchDB.IDUSUARIO, user.getUserID());


        return cv;
    }


    private UserBranchOffice getByCursor(Cursor c) {

        int idxBranchId = c.getColumnIndex(UserBranchDB.IDFILIAL);
        int idxOrganization = c.getColumnIndex(UserBranchDB.IDEMPRESA);
        int idxActive = c.getColumnIndex(UserBranchDB.ATIVO);
        int idxUser = c.getColumnIndex(UserBranchDB.IDUSUARIO);

        UserBranchOffice user = new UserBranchOffice();
        user.setBranchOffice(new BranchOffice(c.getInt(idxBranchId)));
        user.getBranchOffice().setOrganization( new Organization( c.getInt(idxOrganization) ));
        user.setUserID(c.getInt(idxUser));
        user.setEnable(c.getInt(idxActive) == 1);

        return  user;
    }

}

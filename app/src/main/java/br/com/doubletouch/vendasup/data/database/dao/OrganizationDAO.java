package br.com.doubletouch.vendasup.data.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import br.com.doubletouch.vendasup.dao.SQLiteHelper;
import br.com.doubletouch.vendasup.data.database.script.OrganizationDB;
import br.com.doubletouch.vendasup.data.entity.Organization;


/**
 * Created by LADAIR on 17/02/2015.
 */
public class OrganizationDAO {

    public static final String LIMIT = "50";

    SQLiteDatabase db;

    public OrganizationDAO(){
        db = SQLiteHelper.getInstance().getWritableDatabase();
    }


    public Organization get(Integer id) {
        Organization organization = null;
        String where = OrganizationDB.ID+" = ? ";
        Cursor c = db.query(OrganizationDB.TABELA, OrganizationDB.COLUNAS, where, new String[]{String.valueOf(id)}, null, null, null, null);

        if(c.moveToFirst()){
            organization = getByCursor(c);
        }

        c.close();
        return organization;
    }


    public void insert(Organization organization) {

        db.insertWithOnConflict(OrganizationDB.TABELA, null, getContentValues(organization), SQLiteDatabase.CONFLICT_REPLACE);

    }


    private ContentValues getContentValues( Organization organization ) {
        ContentValues cv = new ContentValues();

        cv.put(OrganizationDB.ID, organization.getOrganizationID());
        cv.put(OrganizationDB.NOME, organization.getName());

        return cv;
    }


    private Organization getByCursor(Cursor c) {

        int idxId = c.getColumnIndex(OrganizationDB.ID);
        int idxName = c.getColumnIndex(OrganizationDB.NOME);

        Organization organization = new Organization();
        organization.setName(c.getString(idxName));
        organization.setOrganizationID(c.getInt(idxId));

        return  organization;
    }

}

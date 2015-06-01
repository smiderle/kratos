package br.com.doubletouch.vendasup.data.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.doubletouch.vendasup.dao.SQLiteHelper;
import br.com.doubletouch.vendasup.data.database.script.LicenseDB;
import br.com.doubletouch.vendasup.data.entity.License;
import br.com.doubletouch.vendasup.data.entity.User;
import br.com.doubletouch.vendasup.data.entity.enumeration.VersionType;

/**
 * Created by LADAIR on 20/05/2015.
 */
public class LicenseDAO {

    SQLiteDatabase db;

    public LicenseDAO(){
        db = SQLiteHelper.getInstance().getWritableDatabase();
    }

    public License get(Integer id) {
        License license = null;

        Cursor c = db.query(LicenseDB.TABELA, LicenseDB.COLUNAS, null, null, null, null, null, null);

        if(c.moveToFirst()){
            license = getByCursor(c);
        }

        c.close();
        return license;
    }


    public List<License> getAll() {
            List<License> licenses = new ArrayList<>();
            License license = null;

            Cursor c = db.query(LicenseDB.TABELA, LicenseDB.COLUNAS, null, null, null, null, null, null);

            if( c.moveToFirst() ) {
                do {
                    licenses.add(getByCursor(c));
                } while (c.moveToNext());
            }
            c.close();
            return  licenses;
    }

    public License getByUserId(Integer userId) {
        License license = null;

        String where = LicenseDB.USUARIO +" = ?" ;

        Cursor c = db.query(LicenseDB.TABELA, LicenseDB.COLUNAS, where, new String[]{ String.valueOf( userId ) } , null, null, null, null);

        if(c.moveToFirst()){
            license = getByCursor(c);
        }

        c.close();
        return license;
    }

    public void insert(License license) {
        db.insertWithOnConflict(LicenseDB.TABELA, null, getContentValues(license), SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void update(License license) {
        db.update(LicenseDB.TABELA, getContentValues(license), null, null);
    }



    private ContentValues getContentValues( License license){
        ContentValues cv = new ContentValues();
        cv.put(LicenseDB.ID, license.getId());
        cv.put(LicenseDB.SERIAL, license.getSerial());
        cv.put(LicenseDB.KEY, license.getKey());
        cv.put(LicenseDB.USUARIO, license.getUser().getUserID());
        cv.put(LicenseDB.DATA_ALTERACAO, license.getChangeTime());
        cv.put(LicenseDB.DATA_CRIACAO, license.getRegistrationDate());
        cv.put(LicenseDB.DATA_EXPIRACAO, license.getExpirationDate());
        cv.put(LicenseDB.TIPO_VERSAO, license.getVersionType().ordinal());
        cv.put(LicenseDB.EXPIRADO, license.isExpired() );
        return cv;
    }


    private License getByCursor(Cursor c){

        int idxId = c.getColumnIndex(LicenseDB.ID);
        int idxDtAlteracao = c.getColumnIndex(LicenseDB.DATA_ALTERACAO);
        int idxDtCriacao = c.getColumnIndex(LicenseDB.DATA_CRIACAO);
        int idxDtExpiracao = c.getColumnIndex(LicenseDB.DATA_EXPIRACAO);
        int idxKey = c.getColumnIndex(LicenseDB.KEY);
        int idxSerial = c.getColumnIndex(LicenseDB.SERIAL);
        int idxTipoVersao = c.getColumnIndex(LicenseDB.TIPO_VERSAO);
        int idxUsuario = c.getColumnIndex(LicenseDB.USUARIO);
        int idxExpirado = c.getColumnIndex(LicenseDB.EXPIRADO);

        License license = new License();
        license.setId( c.getInt( idxId ) );
        license.setExpirationDate( c.getLong( idxDtExpiracao ) );
        license.setChangeTime( c.getLong( idxDtAlteracao ) );
        license.setRegistrationDate( c.getLong( idxDtCriacao ) );
        license.setSerial( c.getString( idxSerial ) );
        license.setVersionType( VersionType.values()[ c.getInt( idxTipoVersao ) ] );
        license.setUser( new User( c.getInt(idxUsuario) ));
        license.setKey( c.getString( idxKey ) );
        license.setExpired( c.getInt( idxExpirado ) == 1 );

        return license;

    }
}

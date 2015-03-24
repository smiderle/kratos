package br.com.doubletouch.vendasup.data.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.doubletouch.vendasup.dao.SQLiteHelper;
import br.com.doubletouch.vendasup.data.database.script.UserDB;
import br.com.doubletouch.vendasup.data.entity.User;


/**
 * Created by LADAIR on 17/02/2015.
 */
public class UserDAO {

    public static final String LIMIT = "50";

    SQLiteDatabase db;

    public UserDAO(){
        db = SQLiteHelper.getInstance().getWritableDatabase();
    }


    public User get(Integer id) {
        User user = null;
        String where = UserDB.ID+" = ? ";
        Cursor c = db.query(UserDB.TABELA, UserDB.COLUNAS, where, new String[]{String.valueOf(id)}, null, null, null, null);

        if(c.moveToFirst()){
            user = getByCursor(c);
        }

        c.close();
        return user;
    }

    public List<User> getAll() {
        {

            ArrayList<User> users = new ArrayList<>();
            Cursor c = db.query(UserDB.TABELA, UserDB.COLUNAS, null,null , null, null, null, LIMIT);

            if(c.moveToFirst()){
                do {
                    users.add(getByCursor(c));
                } while (c.moveToNext());
            }
            c.close();
            return  users;
        }
    }



    public void insert(User user) {
        db.insertWithOnConflict(UserDB.TABELA, null, getContentValues(user), SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void insert(List<User> users) {
        db.beginTransaction();
        for(User user : users){
            db.insertWithOnConflict(UserDB.TABELA, null, getContentValues(user), SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void update(User user) {
        String where = UserDB.ID +" = ? ";
        db.update(UserDB.TABELA, getContentValues(user), where, new String[]{ String.valueOf(user.getUserID()) });
    }

    public void delete(User user) {
        throw new UnsupportedOperationException("Não implementado.");
    }

    public List<User> getByName(String name ) {
        List<User> products = new ArrayList<>();

        StringBuilder where = new StringBuilder();
        where.append("(");
        where.append(UserDB.NOME);
        where.append(" like '%");
        where.append(name);
        where.append("%' OR ");
        where.append(UserDB.ID);
        where.append(" = ? )");

        Cursor c = db.query(UserDB.TABELA, UserDB.COLUNAS, where.toString(), null, null, null, null, LIMIT);

        if(c.moveToFirst()){
            do {
                products.add( getByCursor(c) );
            } while (c.moveToNext());
        }
        c.close();

        return products;
    }

    private ContentValues getContentValues( User user ) {
        ContentValues cv = new ContentValues();

        cv.put(UserDB.ID, user.getUserID() );
        cv.put(UserDB.IDEMPRESA, user.getOrganizationID());
        cv.put(UserDB.SENHA, user.getPassword());
        cv.put(UserDB.TELEFONE, user.getPhoneNumber());
        cv.put(UserDB.NOME, user.getName());
        cv.put(UserDB.CELULAR, user.getCellPhone());
        cv.put(UserDB.LINK_FACEBOOK, user.getLinkFacebook());
        cv.put(UserDB.CPF_CNPJ, user.getCpfCnpj());
        cv.put(UserDB.INSCRICAO, user.getInscricao());
        cv.put(UserDB.LINK_GOOGLE_PLUS, user.getLinkGooglePlus());
        cv.put(UserDB.SKYPE, user.getSkype());
        cv.put(UserDB.CONTATO, user.getContactName());
        cv.put(UserDB.CEP, user.getPostalCode());
        cv.put(UserDB.OBSERVACAO, user.getObservation());
        cv.put(UserDB.RUA, user.getStreet());
        cv.put(UserDB.BAIRRO, user.getDistrict());
        cv.put(UserDB.NUMERO, user.getNumber());
        cv.put(UserDB.EMAIL, user.getEmail());
        cv.put(UserDB.ATIVO, user.isActive());
        cv.put(UserDB.URL_IMAGEM, user.getPictureUrl());

        return cv;
    }


    private User getByCursor(Cursor c) {

        int idxId = c.getColumnIndex(UserDB.ID);
        int organizationId = c.getColumnIndex(UserDB.IDEMPRESA);
        int name = c.getColumnIndex(UserDB.NOME);
        int idxCnpjCpf = c.getColumnIndex(UserDB.CPF_CNPJ);
        int idxInscricao = c.getColumnIndex(UserDB.INSCRICAO);
        int idxPostalCode = c.getColumnIndex(UserDB.CEP);
        int idxObservation = c.getColumnIndex(UserDB.OBSERVACAO);
        int idxStreet = c.getColumnIndex(UserDB.RUA);
        int idxDistrict = c.getColumnIndex(UserDB.BAIRRO);
        int idxNumber = c.getColumnIndex(UserDB.NUMERO);
        int idxEmail = c.getColumnIndex(UserDB.EMAIL);
        int idxActive = c.getColumnIndex(UserDB.ATIVO);
        int idxPicture = c.getColumnIndex(UserDB.URL_IMAGEM);
        int idxPassword = c.getColumnIndex(UserDB.SENHA);
        int idxPhone = c.getColumnIndex(UserDB.TELEFONE);
        int idxCellPhone = c.getColumnIndex(UserDB.CELULAR);
        int idxLinkFacebook = c.getColumnIndex(UserDB.LINK_FACEBOOK);
        int idxLinkGooglePlus = c.getColumnIndex(UserDB.LINK_GOOGLE_PLUS);
        int idxSkype = c.getColumnIndex(UserDB.SKYPE);
        int idxContanct = c.getColumnIndex(UserDB.CONTATO);

        User user = new User();
        user.setUserID(c.getInt(idxId));
        user.setOrganizationID(c.getInt(organizationId));
        user.setPassword(c.getString(idxPassword));
        user.setName(c.getString(name));
        user.setPhoneNumber(c.getString(idxPhone));
        user.setCellPhone(c.getString(idxCellPhone));
        user.setLinkFacebook(c.getString(idxLinkFacebook));
        user.setLinkGooglePlus(c.getString(idxLinkGooglePlus));
        user.setSkype(c.getString(idxSkype));
        user.setContactName(c.getString(idxContanct));
        user.setCpfCnpj(c.getString(idxCnpjCpf));
        user.setPostalCode(c.getString(idxPostalCode));
        user.setObservation(c.getString(idxObservation));
        user.setStreet(c.getString(idxStreet));
        user.setDistrict(c.getString(idxDistrict));
        user.setNumber(c.getString(idxNumber));
        user.setEmail(c.getString(idxEmail));
        user.setActive(c.getInt(idxActive) == 1);
        user.setPictureUrl(c.getString(idxPicture));
        user.setInscricao(c.getString(idxInscricao));


        return  user;
    }

}

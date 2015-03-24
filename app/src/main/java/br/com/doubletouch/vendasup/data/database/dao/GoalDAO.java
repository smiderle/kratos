package br.com.doubletouch.vendasup.data.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.doubletouch.vendasup.dao.SQLiteHelper;
import br.com.doubletouch.vendasup.data.database.script.GoalDB;
import br.com.doubletouch.vendasup.data.entity.Goal;


/**
 * Created by LADAIR on 17/02/2015.
 */
public class GoalDAO {

    public static final String LIMIT = "50";

    SQLiteDatabase db;

    public GoalDAO(){
        db = SQLiteHelper.getInstance().getWritableDatabase();
    }


    public Goal get(Integer id) {
        Goal goal = null;
        String where = GoalDB.ID+" = ? ";
        Cursor c = db.query(GoalDB.TABELA, GoalDB.COLUNAS, where, new String[]{String.valueOf(id)}, null, null, null, null);

        if(c.moveToFirst()){
            goal = getByCursor(c);
        }

        c.close();
        return goal;
    }

    public List<Goal> getAll() {

            ArrayList<Goal> goals = new ArrayList<>();
            Cursor c = db.query(GoalDB.TABELA, GoalDB.COLUNAS, null,null , null, null, null, LIMIT);

            if(c.moveToFirst()){
                do {
                    goals.add(getByCursor(c));
                } while (c.moveToNext());
            }
            c.close();
            return  goals;
    }


    public void insert(List<Goal> goals) {
        db.beginTransaction();
        for(Goal goal : goals){
            db.insertWithOnConflict(GoalDB.TABELA, null, getContentValues(goal), SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    private ContentValues getContentValues( Goal goal ) {
        ContentValues cv = new ContentValues();

        cv.put(GoalDB.ID, goal.getID() );
        cv.put(GoalDB.IDEMPRESA, goal.getOrganizationID());
        cv.put(GoalDB.IDFILIAL, goal.getBranchID());
        cv.put(GoalDB.IDUSUARIO, goal.getUserID());
        cv.put(GoalDB.MES, goal.getMonth());
        cv.put(GoalDB.ANO, goal.getYear());
        cv.put(GoalDB.ANO_MES, goal.getYearMonth());
        cv.put(GoalDB.VALOR, goal.getValue());

        return cv;
    }


    private Goal getByCursor(Cursor c) {

        int idxId = c.getColumnIndex(GoalDB.ID);
        int idEmpresa = c.getColumnIndex(GoalDB.IDEMPRESA);
        int idFilial = c.getColumnIndex(GoalDB.IDFILIAL);
        int idUsuario = c.getColumnIndex(GoalDB.IDUSUARIO);
        int ano = c.getColumnIndex(GoalDB.ANO);
        int mes = c.getColumnIndex(GoalDB.MES);
        int anoMes = c.getColumnIndex(GoalDB.ANO_MES);
        int valor = c.getColumnIndex(GoalDB.VALOR);

        Goal goal = new Goal();
        goal.setBranchID( c.getInt(  idFilial ));
        goal.setOrganizationID( c.getInt(  idEmpresa ));
        goal.setID(c.getInt( idxId ));
        goal.setUserID( c.getInt(  idUsuario ) );
        goal.setMonth(c.getInt( mes ) );
        goal.setYear(c.getInt( ano ) );
        goal.setYearMonth(c.getInt( anoMes ) );
        goal.setValue(c.getDouble( valor ) );

        return  goal;
    }

}

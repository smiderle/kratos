package br.com.doubletouch.vendasup.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.database.dao.UserDAO;
import br.com.doubletouch.vendasup.data.database.script.BranchDB;
import br.com.doubletouch.vendasup.data.database.script.GoalDB;
import br.com.doubletouch.vendasup.data.database.script.InstallmentDB;
import br.com.doubletouch.vendasup.data.database.script.LicenseDB;
import br.com.doubletouch.vendasup.data.database.script.OrderDB;
import br.com.doubletouch.vendasup.data.database.script.OrderItemDB;
import br.com.doubletouch.vendasup.data.database.script.OrderPaymentDB;
import br.com.doubletouch.vendasup.data.database.script.OrganizationDB;
import br.com.doubletouch.vendasup.data.database.script.ProductPromotionDB;
import br.com.doubletouch.vendasup.data.database.script.SincronizacaoDB;
import br.com.doubletouch.vendasup.data.database.script.ProductDB;
import br.com.doubletouch.vendasup.data.database.script.PriceTableDB;
import br.com.doubletouch.vendasup.data.database.script.CustomerDB;
import br.com.doubletouch.vendasup.data.database.script.UserBranchDB;
import br.com.doubletouch.vendasup.data.database.script.UserDB;
import br.com.doubletouch.vendasup.data.entity.OrderPayment;

/**
 * Created by LADAIR on 26/01/2015.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private static  SQLiteHelper sInstance;

    public static final String DATABASE_NAME = "KRATOS";
    public static final Integer VERSION = 4;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public static SQLiteHelper getInstance( ){
        return getInstance(VendasUp.getAppContext());
    }

    public static SQLiteHelper getInstance( Context context ){
        if( sInstance == null ){
            sInstance = new SQLiteHelper(context);
        }
        return  sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       String[] scripts = scripts();
        for ( String script : scripts ){
            db.execSQL(script);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        switch (newVersion){
            case 4:

                String sql2 = "ALTER TABLE " + OrderDB.TABELA+" ADD COLUMN "+ OrderDB.TIPO+ " INTEGER ";
                db.execSQL(sql2);

                break;

        }




    }

    private String[] scripts() {
        return new String[]{
                ProductDB.create(),
                SincronizacaoDB.create(),
                CustomerDB.create(),
                PriceTableDB.create(),
                InstallmentDB.create(),
                UserDB.create(),
                UserBranchDB.create(),
                OrganizationDB.create(),
                BranchDB.create(),
                ProductPromotionDB.create(),
                GoalDB.create(),
                OrderDB.create(),
                OrderItemDB.create(),
                OrderPaymentDB.create(),
                LicenseDB.create()
        };

    }
}


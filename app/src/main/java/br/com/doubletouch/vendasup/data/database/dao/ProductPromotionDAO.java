package br.com.doubletouch.vendasup.data.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;
import java.util.List;

import br.com.doubletouch.vendasup.dao.SQLiteHelper;
import br.com.doubletouch.vendasup.data.database.script.ProductPromotionDB;
import br.com.doubletouch.vendasup.data.entity.ProductPromotion;

/**
 * Created by LADAIR on 23/03/2015.
 */
public class ProductPromotionDAO {


    public static final String LIMIT = "50";

    SQLiteDatabase db;

    public ProductPromotionDAO(){
        db = SQLiteHelper.getInstance().getWritableDatabase();
    }


    public ProductPromotion get(Integer productID) {
        ProductPromotion promotion = null;
        String where = ProductPromotionDB.IDPRODUTO+" = ? ";
        Cursor c = db.query(ProductPromotionDB.TABELA, ProductPromotionDB.COLUNAS, where, new String[]{String.valueOf(productID)}, null, null, null, null);

        if(c.moveToFirst()){
            promotion = getByCursor(c);
        }

        c.close();
        return promotion;
    }



    public void insert(List<ProductPromotion> promotions) {
        db.beginTransaction();
        for(ProductPromotion promotion : promotions){
            db.insertWithOnConflict(ProductPromotionDB.TABELA, null, getContentValues(promotion), SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }


    private ContentValues getContentValues( ProductPromotion promotion) {

        ContentValues cv = new ContentValues();

        cv.put(ProductPromotionDB.ID, promotion.getID());
        cv.put(ProductPromotionDB.IDEMPRESA, promotion.getOrganizationID());
        cv.put(ProductPromotionDB.IDFILIAL, promotion.getBranchID());
        cv.put(ProductPromotionDB.IDPRODUTO, promotion.getProductID());
        cv.put(ProductPromotionDB.PRECO, promotion.getPromotionPrice());
        cv.put(ProductPromotionDB.EXCLUIDO, promotion.isExcluded());
        cv.put(ProductPromotionDB.DATA_FINAL, promotion.getFinalDate().getTime() );
        cv.put(ProductPromotionDB.DATA_INICIAL, promotion.getInitialDate().getTime());

        return cv;
    }


    private ProductPromotion getByCursor(Cursor c) {

        int id = c.getColumnIndex(ProductPromotionDB.ID);
        int dataFinal = c.getColumnIndex(ProductPromotionDB.DATA_FINAL);
        int dataInicial = c.getColumnIndex(ProductPromotionDB.DATA_INICIAL);
        int excluido = c.getColumnIndex(ProductPromotionDB.EXCLUIDO);
        int idEmpresa = c.getColumnIndex(ProductPromotionDB.IDEMPRESA);
        int idFilial = c.getColumnIndex(ProductPromotionDB.IDFILIAL);
        int idProduto = c.getColumnIndex(ProductPromotionDB.IDPRODUTO);
        int preco = c.getColumnIndex(ProductPromotionDB.PRECO);


        ProductPromotion promotion = new ProductPromotion();

        promotion.setID(c.getInt(id));
        promotion.setBranchID(c.getInt(idFilial));
        promotion.setOrganizationID(c.getInt(idEmpresa));
        promotion.setProductID(c.getInt(idProduto));
        promotion.setExcluded(c.getInt(excluido) == 1);
        promotion.setFinalDate( new Date( c.getLong(dataFinal) ) );
        promotion.setInitialDate(new Date( c.getLong(dataInicial) ) );
        promotion.setPromotionPrice( c.getDouble(preco));

        return  promotion;
    }

}

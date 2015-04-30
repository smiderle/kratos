package br.com.doubletouch.vendasup.data.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.doubletouch.vendasup.dao.SQLiteHelper;
import br.com.doubletouch.vendasup.data.database.script.OrderItemDB;
import br.com.doubletouch.vendasup.data.entity.Order;
import br.com.doubletouch.vendasup.data.entity.OrderItem;
import br.com.doubletouch.vendasup.data.entity.PriceTable;
import br.com.doubletouch.vendasup.data.entity.Product;

/**
 * Created by LADAIR on 21/04/2015.
 */
public class OrderItemDAO {



    public static final String LIMIT = "50";

    SQLiteDatabase db;

    public OrderItemDAO() {

        db = SQLiteHelper.getInstance().getWritableDatabase();

    }

    public void insert(OrderItem orderItem) {
        db.insertWithOnConflict(OrderItemDB.TABELA, null, getContentValues(orderItem), SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void insert(List<OrderItem> ordersItens) {
        db.beginTransaction();
        for(OrderItem orderItem : ordersItens){
            db.insertWithOnConflict(OrderItemDB.TABELA, null, getContentValues(orderItem), SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public OrderItem get(Integer id) {
        OrderItem order = null;
        String where = OrderItemDB.ID+" = ? ";
        Cursor c = db.query(OrderItemDB.TABELA, OrderItemDB.COLUNAS, where, new String[]{String.valueOf(id)}, null, null, null, null);

        if(c.moveToFirst()){
            order = getByCursor(c);
        }

        c.close();
        return order;
    }

    public List<OrderItem> getAll(Integer branchId){
        ArrayList<OrderItem> ordersItem = new ArrayList<>();

        String where = OrderItemDB.IDFILIAL+" = ? ";
        Cursor c = db.query(OrderItemDB.TABELA, OrderItemDB.COLUNAS, where, new String[]{String.valueOf(branchId)}, null, null, null, LIMIT);

        if(c.moveToFirst()){
            do {
                ordersItem.add(getByCursor(c));
            } while (c.moveToNext());
        }
        c.close();
        return  ordersItem;
    }



    private OrderItem getByCursor(Cursor c) {

        int idxId = c.getColumnIndex(OrderItemDB.ID);
        int idxIdFilial = c.getColumnIndex(OrderItemDB.IDFILIAL );
        int idxIdEmpresa = c.getColumnIndex(OrderItemDB.IDEMPRESA);
        int idxPedido = c.getColumnIndex(OrderItemDB.IDPEDIDO);
        int idxSequencia = c.getColumnIndex(OrderItemDB.SEQUENCE);
        int idxIdProduto = c.getColumnIndex(OrderItemDB.IDPRODUTO);
        int idxQuantidade = c.getColumnIndex(OrderItemDB.QUANTIDADE);
        int idxPreco = c.getColumnIndex(OrderItemDB.PRECO);
        int idxObservacao = c.getColumnIndex(OrderItemDB.OBSERVACAO);
        int idxTabelaPreco = c.getColumnIndex(OrderItemDB.TABELA_PRECO);

        OrderItem orderItem = new OrderItem();
        orderItem.setID( c.getLong( idxId ) );
        orderItem.setOrganizationID( c.getInt(idxIdEmpresa) );
        orderItem.setBranchID(c.getInt(idxIdFilial));
        orderItem.setOrder( new Order( c.getLong(idxPedido) ) );
        orderItem.setSequence(c.getInt(idxSequencia));
        orderItem.setProduct(new Product( c.getInt(idxIdProduto) ));
        orderItem.setQuantity(c.getDouble(idxQuantidade));
        orderItem.setPrice(c.getDouble(idxPreco));
        orderItem.setObservation(c.getString(idxObservacao));
        orderItem.setPriceTable(new PriceTable( c.getInt(idxTabelaPreco) ));

        return orderItem;
    }



    private ContentValues getContentValues( OrderItem orderItem) {

        ContentValues cv = new ContentValues();
        cv.put(OrderItemDB.ID, orderItem.getID());
        cv.put(OrderItemDB.IDEMPRESA, orderItem.getOrganizationID());
        cv.put(OrderItemDB.IDFILIAL, orderItem.getBranchID());
        cv.put(OrderItemDB.SEQUENCE, orderItem.getSequence());
        cv.put(OrderItemDB.QUANTIDADE, orderItem.getQuantity());
        cv.put(OrderItemDB.PRECO, orderItem.getPrice());
        cv.put(OrderItemDB.OBSERVACAO, orderItem.getObservation());


        if( orderItem.getOrder().getID() != null ){
            cv.put(OrderItemDB.IDPEDIDO, orderItem.getOrder().getID());
        }

        if(orderItem.getProduct() != null){
            cv.put(OrderItemDB.IDPRODUTO, orderItem.getProduct().getID());
        }

        if(orderItem.getPriceTable() != null){
            cv.put(OrderItemDB.TABELA_PRECO, orderItem.getPriceTable().getID());
        }

        return cv;
    }
}

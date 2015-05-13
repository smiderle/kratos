package br.com.doubletouch.vendasup.data.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.doubletouch.vendasup.dao.SQLiteHelper;
import br.com.doubletouch.vendasup.data.database.script.OrderDB;
import br.com.doubletouch.vendasup.data.database.script.OrderItemDB;
import br.com.doubletouch.vendasup.data.database.script.OrderPaymentDB;
import br.com.doubletouch.vendasup.data.entity.Order;
import br.com.doubletouch.vendasup.data.entity.OrderItem;
import br.com.doubletouch.vendasup.data.entity.OrderPayment;

/**
 * Created by LADAIR on 21/04/2015.
 */
public class OrderPaymentDAO {



    public static final String LIMIT = "50";

    SQLiteDatabase db;

    public OrderPaymentDAO() {

        db = SQLiteHelper.getInstance().getWritableDatabase();

    }

    public void insert(OrderPayment orderPayment) {
        db.insertWithOnConflict(OrderPaymentDB.TABELA, null, getContentValues(orderPayment), SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void insert(List<OrderPayment> ordersPayments) {
        db.beginTransaction();
        for(OrderPayment orderPayment : ordersPayments){
            db.insertWithOnConflict(OrderPaymentDB.TABELA, null, getContentValues(orderPayment), SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public OrderPayment get(Integer id) {
        OrderPayment order = null;
        String where = OrderPaymentDB.ID+" = ? ";
        Cursor c = db.query(OrderPaymentDB.TABELA, OrderPaymentDB.COLUNAS, where, new String[]{String.valueOf(id)}, null, null, null, null);

        if(c.moveToFirst()){
            order = getByCursor(c);
        }

        c.close();
        return order;
    }

    public List<OrderPayment> getAll(Integer branchId){
        ArrayList<OrderPayment> ordersPayments = new ArrayList<>();

        String where = OrderPaymentDB.IDFILIAL+" = ? ";
        Cursor c = db.query(OrderPaymentDB.TABELA, OrderPaymentDB.COLUNAS, where, new String[]{String.valueOf(branchId)}, null, null, null, LIMIT);

        if(c.moveToFirst()){
            do {
                ordersPayments.add(getByCursor(c));
            } while (c.moveToNext());
        }
        c.close();
        return  ordersPayments;
    }

    public List<OrderPayment> findByOrderID( Long orderItemID ) {

        ArrayList<OrderPayment> ordersPayments = new ArrayList<>();

        String where = OrderPaymentDB.IDPEDIDO+" = ? ";
        Cursor c = db.query(OrderPaymentDB.TABELA, OrderPaymentDB.COLUNAS, where, new String[]{String.valueOf(orderItemID)}, null, null, null, LIMIT);

        if(c.moveToFirst()){
            do {
                ordersPayments.add(getByCursor(c));
            } while (c.moveToNext());
        }
        c.close();
        return  ordersPayments;

    }

    public void deleteByOrderId( Long orderItemID  ){

        String where = OrderPaymentDB.IDPEDIDO + " = " + orderItemID;
        db.delete(OrderPaymentDB.TABELA,where, null );

    }


    public void updateNewOrder( Long oldOrderID, Long newOrderId) {

        String sql = "UPDATE " + OrderPaymentDB.TABELA + " SET " + OrderPaymentDB.IDPEDIDO +" = " + newOrderId +
                " WHERE "+ OrderPaymentDB.IDPEDIDO +" = " + oldOrderID;

        db.execSQL(sql);
    }



    private OrderPayment getByCursor(Cursor c) {

        int idxId = c.getColumnIndex(OrderPaymentDB.ID);
        int idxIdFilial = c.getColumnIndex(OrderPaymentDB.IDFILIAL );
        int idxIdEmpresa = c.getColumnIndex(OrderPaymentDB.IDEMPRESA);
        int idxPedido = c.getColumnIndex(OrderPaymentDB.IDPEDIDO);
        int idxSequencia = c.getColumnIndex(OrderPaymentDB.SEQUENCE);
        int idxDataVencimento = c.getColumnIndex(OrderPaymentDB.DATA_VENCIMENTO);
        int idxDataPagamento = c.getColumnIndex(OrderPaymentDB.DATA_PAGAMENTO);
        int idxValor = c.getColumnIndex(OrderPaymentDB.VALOR);
        int idxNumeroDocumento = c.getColumnIndex(OrderPaymentDB.NUMERO_DOCUMENTO);
        int idxObservacao = c.getColumnIndex(OrderPaymentDB.OBSERVACAO);

        OrderPayment orderPayment = new OrderPayment();
        orderPayment.setID(c.getLong(idxId));
        orderPayment.setOrganizationID(c.getInt(idxIdEmpresa));
        orderPayment.setBranchID(c.getInt(idxIdFilial));
        orderPayment.setOrder(new Order(c.getLong(idxPedido)));
        orderPayment.setSequence(c.getInt(idxSequencia));
        orderPayment.setExpirationDate( c.getLong(idxDataVencimento));
        orderPayment.setPaymentDate( c.getLong(idxDataPagamento ));
        orderPayment.setInstallmentValue(c.getDouble(idxValor));
        orderPayment.setDocumentNumber(c.getString(idxNumeroDocumento));
        orderPayment.setObservation(c.getString(idxObservacao));

        return orderPayment;
    }



    private ContentValues getContentValues( OrderPayment orderPayment) {

        ContentValues cv = new ContentValues();
        cv.put(OrderPaymentDB.ID, orderPayment.getID());
        cv.put(OrderPaymentDB.IDEMPRESA, orderPayment.getOrganizationID());
        cv.put(OrderPaymentDB.IDFILIAL, orderPayment.getBranchID());
        cv.put(OrderPaymentDB.SEQUENCE, orderPayment.getSequence());
        cv.put(OrderPaymentDB.VALOR, orderPayment.getSequence());
        cv.put(OrderPaymentDB.NUMERO_DOCUMENTO, orderPayment.getDocumentNumber());
        cv.put(OrderPaymentDB.OBSERVACAO, orderPayment.getObservation());
        cv.put(OrderPaymentDB.DATA_VENCIMENTO, orderPayment.getExpirationDate());
        cv.put(OrderPaymentDB.DATA_PAGAMENTO, orderPayment.getPaymentDate());

        if( orderPayment.getOrder() != null ){
            cv.put(OrderPaymentDB.IDPEDIDO, orderPayment.getOrder().getID());
        }



        return cv;
    }
}

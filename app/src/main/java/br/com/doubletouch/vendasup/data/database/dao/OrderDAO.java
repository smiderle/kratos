package br.com.doubletouch.vendasup.data.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.dao.SQLiteHelper;
import br.com.doubletouch.vendasup.data.database.script.OrderDB;
import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.Installment;
import br.com.doubletouch.vendasup.data.entity.Order;
import br.com.doubletouch.vendasup.data.entity.User;

/**
 * Created by LADAIR on 21/04/2015.
 */
public class OrderDAO {



    public static final String LIMIT = "50";

    SQLiteDatabase db;

    public OrderDAO() {

        db = SQLiteHelper.getInstance().getWritableDatabase();

    }

    public Long insert(Order order) {

        return db.insertWithOnConflict(OrderDB.TABELA, null, getContentValues(order), SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void insert(List<Order> orders) {
        db.beginTransaction();
        for(Order order : orders){
            db.insertWithOnConflict(OrderDB.TABELA, null, getContentValues(order), SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public Order get(Integer id) {
        Order order = null;
        String where = OrderDB.ID+" = ? ";
        Cursor c = db.query(OrderDB.TABELA, OrderDB.COLUNAS, where, new String[]{String.valueOf(id)}, null, null, null, null);

        if(c.moveToFirst()){
            order = getByCursor(c);
        }

        c.close();
        return order;
    }

    public List<Order> getAll(Integer branchId){
        ArrayList<Order> orders = new ArrayList<>();

        String where = OrderDB.IDFILIAL+" = ? ";
        Cursor c = db.query(OrderDB.TABELA, OrderDB.COLUNAS, where, new String[]{String.valueOf(branchId)}, null, null, null, LIMIT);

        if(c.moveToFirst()){
            do {
                orders.add( getByCursor(c) );
            } while (c.moveToNext());
        }

        c.close();
        return  orders;
    }

    public Double getSumSales(Integer userID, Integer branchID, Integer organizationID, Long inicio, Long fim){

        String where = " WHERE "+ OrderDB.IDUSUARIO +" = " + userID +" AND " + OrderDB.IDEMPRESA +" = " + organizationID +" AND "
                + OrderDB.IDFILIAL + " = " + branchID + " AND " + OrderDB.DATA_EMISSAO + " > " + inicio +  " AND " + OrderDB.DATA_EMISSAO + " < " + fim ;

        String query = "SELECT SUM("+OrderDB.VALOR_LIQUIDO+") FROM "+ OrderDB.TABELA +  where ;

        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        Double total = c.getDouble(0);

        c.close();

        return total;

    }



    private Order getByCursor(Cursor c){

        int idxIdFilial = c.getColumnIndex( OrderDB.IDFILIAL );
        int idxId = c.getColumnIndex(OrderDB.ID);
        int idxIdEmpresa = c.getColumnIndex(OrderDB.IDEMPRESA);
        int idxCliente = c.getColumnIndex(OrderDB.IDCLIENTE);
        int idxParcelamento = c.getColumnIndex(OrderDB.IDPARCELAMENTO);
        int idxUsuario = c.getColumnIndex(OrderDB.IDUSUARIO);
        int idxExcluido = c.getColumnIndex(OrderDB.EXCLUIDO);
        int idxDataEmissao = c.getColumnIndex(OrderDB.DATA_EMISSAO);
        int idxValorLiquido = c.getColumnIndex(OrderDB.VALOR_LIQUIDO);
        int idxValorBruto = c.getColumnIndex(OrderDB.VALOR_BRUTO);
        int idxDescontoTotal = c.getColumnIndex(OrderDB.DESCONTO_TOTAL);
        int idxObservacao = c.getColumnIndex(OrderDB.OBSERVACAO);
        int idxFormaPagamento = c.getColumnIndex(OrderDB.FORMA_PAGAMENTO);

        Order order = new Order();
        order.setID( c.getLong( idxId ) );
        order.setBranchID(c.getInt(idxIdFilial));
        order.setOrganizationID(c.getInt(idxIdEmpresa));
        order.setCustomer( new Customer( c.getInt( idxCliente )));
        order.setInstallment( new Installment( c.getInt( idxParcelamento )));
        order.setUser(new User(c.getInt(idxUsuario))) ;
        order.setUserID(c.getInt(idxUsuario)); ;
        order.setIssuanceTime( new Date( c.getLong( idxDataEmissao ) ) );
        order.setNetValue( c.getDouble( idxValorLiquido ));
        order.setGrossValue( c.getDouble( idxValorBruto ));
        order.setTotalDiscount( c.getDouble( idxDescontoTotal ));
        order.setObservation( c.getString( idxObservacao ));
        order.setFormPayment( c.getInt( idxFormaPagamento ));
        order.setExcluded( c.getInt( idxExcluido ) == 1 );

        return order;
    }



    private ContentValues getContentValues( Order order) {

        ContentValues cv = new ContentValues();
        cv.put(OrderDB.ID, order.getID());
        cv.put(OrderDB.IDEMPRESA, order.getOrganizationID());
        cv.put(OrderDB.IDFILIAL, order.getBranchID());
        cv.put(OrderDB.IDUSUARIO, order.getUserID());
        cv.put(OrderDB.EXCLUIDO, order.isExcluded());
        cv.put(OrderDB.VALOR_LIQUIDO, order.getNetValue());
        cv.put(OrderDB.VALOR_BRUTO, order.getGrossValue());
        cv.put(OrderDB.DESCONTO_TOTAL, order.getTotalDiscount());
        cv.put(OrderDB.OBSERVACAO, order.getObservation());
        cv.put(OrderDB.FORMA_PAGAMENTO, order.getFormPayment());

        if(order.getInstallment() != null) {
            cv.put(OrderDB.IDPARCELAMENTO, order.getInstallment().getInstallmentID());
        }

        if(order.getCustomer() != null) {
            cv.put(OrderDB.IDCLIENTE, order.getCustomer().getCustomerID());
        }

        if(order.getIssuanceTime() != null) {
            cv.put(OrderDB.DATA_EMISSAO, order.getIssuanceTime().getTime());
        }

        return cv;
    }
}

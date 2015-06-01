package br.com.doubletouch.vendasup.data.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.dao.SQLiteHelper;
import br.com.doubletouch.vendasup.data.database.script.CustomerDB;
import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.Installment;


/**
 * Created by LADAIR on 17/02/2015.
 */
public class CustomerDAO {

    public static final String LIMIT = "50";

    SQLiteDatabase db;

    public CustomerDAO(){
        db = SQLiteHelper.getInstance().getWritableDatabase();
    }


    public Customer get(Integer id) {
        Customer customer = null;
        String where = CustomerDB.ID+" = ? ";
        Cursor c = db.query(CustomerDB.TABELA, CustomerDB.COLUNAS, where, new String[]{String.valueOf(id)}, null, null, null, null);

        if(c.moveToFirst()){
            customer = getByCursor(c);
        }

        c.close();
        return customer;
    }

    public List<Customer> getAll(Integer branchId) {
        {
            String where = CustomerDB.IDFILIAL +" = ? ";
            ArrayList<Customer> customers = new ArrayList<>();
            Cursor c = db.query(CustomerDB.TABELA, CustomerDB.COLUNAS, where, new String[]{String.valueOf(branchId)}, null, null, null, LIMIT);

            if(c.moveToFirst()){
                do {
                    customers.add(getByCursor(c));
                } while (c.moveToNext());
            }
            c.close();
            return  customers;
        }
    }


    public Integer max() {

        Integer max = 0;

        String sql = "SELECT MAX("+ CustomerDB.ID +") AS max_id FROM " + CustomerDB.TABELA;

        Cursor cursor = db.rawQuery( sql, null );

        if(cursor.moveToFirst()){
            max = cursor.getInt( 0 );
        }

        return max;


    }

    public void updateByIdMobile( Customer customer) {

        String sql = "UPDATE " + CustomerDB.TABELA + " SET " + CustomerDB.ID +" = " + customer.getID() +"," + CustomerDB.SYNC_PENDENTE+" = 0 "+
                "WHERE "+ CustomerDB.ID_MOBILE +" = " + customer.getIdMobile();

        db.execSQL(sql);
    }



    public void insert(Customer customer) {
        db.insertWithOnConflict(CustomerDB.TABELA, null, getContentValues(customer), SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void insert(List<Customer> customers) {
        db.beginTransaction();
        for(Customer customer : customers){
            db.insertWithOnConflict(CustomerDB.TABELA, null, getContentValues(customer), SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void update(Customer customer) {
        String where = CustomerDB.ID +" = ? ";
        db.update(CustomerDB.TABELA, getContentValues(customer), where, new String[]{ String.valueOf(customer.getID()) });
    }

    public void delete(Customer customer) {
        throw new UnsupportedOperationException("Não implementado.");
    }

    public List<Customer> getByNameOrCustomerId(String name, String customerId, Integer branchId) {
        List<Customer> products = new ArrayList<>();

        StringBuilder where = new StringBuilder();
        where.append("(");
        where.append(CustomerDB.NOME);
        where.append(" like '%");
        where.append(name);
        where.append("%' OR ");
        where.append(CustomerDB.ID);
        where.append(" = ? )");
        where.append(" AND ");
        where.append(CustomerDB.IDFILIAL);
        where.append(" = ? ");

        Cursor c = db.query(CustomerDB.TABELA, CustomerDB.COLUNAS, where.toString(), new String[]{ customerId, String.valueOf( branchId )}, null, null, null, LIMIT);

        if(c.moveToFirst()){
            do {
                products.add(getByCursor(c));
            } while (c.moveToNext());
        }
        c.close();

        return products;
    }


    public List<Customer> getAllSyncPending(Integer branchId) {
        ArrayList<Customer> customers = new ArrayList<>();

        String where = CustomerDB.IDFILIAL+" = ? AND  " +CustomerDB.SYNC_PENDENTE +" = 1 " ;
        Cursor c = db.query(CustomerDB.TABELA, CustomerDB.COLUNAS, where, new String[]{String.valueOf(branchId)}, null, null, null, null);

        if(c.moveToFirst()){
            do {
                customers.add(getByCursor(c));
            } while (c.moveToNext());
        }
        c.close();
        return  customers;
    }

    private ContentValues getContentValues( Customer customer) {
        ContentValues cv = new ContentValues();
        cv.put(CustomerDB.ID, customer.getID());
        cv.put(CustomerDB.IDEMPRESA, customer.getOrganizationID());
        cv.put(CustomerDB.IDFILIAL, customer.getBranchID());
        cv.put(CustomerDB.IDCLIENTE, customer.getCustomerID());
        cv.put(CustomerDB.NOME, customer.getName());
        cv.put(CustomerDB.APELIDO, customer.getNickName());
        cv.put(CustomerDB.TIPO_PESSOA, customer.getPersonType());
        cv.put(CustomerDB.CPF_CNPJ, customer.getCpfCnpj());
        cv.put(CustomerDB.INSCRICAO, customer.getIncricao());
        cv.put(CustomerDB.FONE_COMERCIAL, customer.getCommercialPhone());
        cv.put(CustomerDB.FONE_RESIDENCIAL, customer.getHomePhone());
        cv.put(CustomerDB.FONE_CELULAR, customer.getCellPhone());
        cv.put(CustomerDB.CEP, customer.getPostalCode());
        cv.put(CustomerDB.COMPLEMENTO, customer.getAddressComplement());
        cv.put(CustomerDB.OBSERVACAO, customer.getObservation());
        cv.put(CustomerDB.FAX, customer.getFaxNumber());
        cv.put(CustomerDB.RUA, customer.getStreet());
        cv.put(CustomerDB.BAIRRO, customer.getDistrict());
        cv.put(CustomerDB.NUMERO, customer.getNumber());
        cv.put(CustomerDB.EMAIL, customer.getEmail());
        cv.put(CustomerDB.DESCONTO_PADRAO, customer.getDefaultDiscount());
        cv.put(CustomerDB.ATIVO, customer.isActive());
        cv.put(CustomerDB.EXCLUIDO, customer.isExcluded());
        cv.put(CustomerDB.URL_IMAGEM, customer.getPictureUrl());
        cv.put(CustomerDB.TABELA_PRECO, customer.getPriceTable());
        cv.put(CustomerDB.FORMA_PAGAMENTO, customer.getFormPayment());
        cv.put(CustomerDB.LIMITE_CREDITO, customer.getCreditLimit());
        cv.put(CustomerDB.ID_MOBILE, customer.getIdMobile());
        cv.put(CustomerDB.SYNC_PENDENTE, customer.isSyncPending());
        cv.put(CustomerDB.VENDEDOR_PADRAO, customer.getDefaultSeller());


        if(customer.getInstallmentId() != null){
            cv.put(CustomerDB.PARCELAMENTO, customer.getInstallmentId());
        }

        if(customer.getInstallment() != null && customer.getInstallment().getID() != null){
            cv.put(CustomerDB.PARCELAMENTO, customer.getInstallment().getID());
        }

        return cv;
    }


    private Customer getByCursor(Cursor c){
        int idxId = c.getColumnIndex(CustomerDB.ID);
        int organizationId = c.getColumnIndex(CustomerDB.IDEMPRESA);
        int branchId = c.getColumnIndex(CustomerDB.IDFILIAL);
        int customerId = c.getColumnIndex(CustomerDB.IDCLIENTE);
        int name = c.getColumnIndex(CustomerDB.NOME);
        int nickName = c.getColumnIndex(CustomerDB.APELIDO);
        int idxTypePerson = c.getColumnIndex(CustomerDB.TIPO_PESSOA);
        int idxCnpjCpf = c.getColumnIndex(CustomerDB.CPF_CNPJ);
        int idxInscricao = c.getColumnIndex(CustomerDB.INSCRICAO);
        int idxPhoneComercial = c.getColumnIndex(CustomerDB.FONE_COMERCIAL);
        int idxPhoneHome = c.getColumnIndex(CustomerDB.FONE_RESIDENCIAL);
        int idxPhoneCell = c.getColumnIndex(CustomerDB.FONE_CELULAR);
        int idxPostalCode = c.getColumnIndex(CustomerDB.CEP);
        int idxAddressComplement = c.getColumnIndex(CustomerDB.COMPLEMENTO);
        int idxObservation = c.getColumnIndex(CustomerDB.OBSERVACAO);
        int idxFax = c.getColumnIndex(CustomerDB.FAX);
        int idxStreet = c.getColumnIndex(CustomerDB.RUA);
        int idxDistrict = c.getColumnIndex(CustomerDB.BAIRRO);
        int idxNumber = c.getColumnIndex(CustomerDB.NUMERO);
        int idxEmail = c.getColumnIndex(CustomerDB.EMAIL);
        int idxDefaultDiscount = c.getColumnIndex(CustomerDB.DESCONTO_PADRAO);
        int idxActive = c.getColumnIndex(CustomerDB.ATIVO);
        int idxExcluded = c.getColumnIndex(CustomerDB.EXCLUIDO);
        int idxPicture = c.getColumnIndex(CustomerDB.URL_IMAGEM);
        int idxPriceTable = c.getColumnIndex(CustomerDB.TABELA_PRECO);
        int idxInstallment = c.getColumnIndex(CustomerDB.PARCELAMENTO);
        int idxPayment = c.getColumnIndex(CustomerDB.FORMA_PAGAMENTO);
        int idxLimiteCredito = c.getColumnIndex(CustomerDB.LIMITE_CREDITO);
        int idxIdClienteMobile = c.getColumnIndex(CustomerDB.ID_MOBILE);
        int idxSincronizado = c.getColumnIndex(CustomerDB.SYNC_PENDENTE);
        int idxVendedor = c.getColumnIndex(CustomerDB.VENDEDOR_PADRAO);


        Customer customer = new Customer();
        customer.setID(c.getInt(idxId));
        customer.setOrganizationID(c.getInt(organizationId));
        customer.setBranchID(c.getInt(branchId));
        customer.setCustomerID(c.getString(customerId));
        customer.setName(c.getString(name));
        customer.setNickName(c.getString(nickName));
        customer.setPersonType(c.getInt(idxTypePerson));
        customer.setCpfCnpj(c.getString(idxCnpjCpf));
        customer.setIncricao(c.getString(idxInscricao));
        customer.setCommercialPhone(c.getString(idxPhoneComercial));
        customer.setHomePhone(c.getString(idxPhoneHome));
        customer.setCellPhone(c.getString(idxPhoneCell));
        customer.setPostalCode(c.getString(idxPostalCode));
        customer.setAddressComplement(c.getString(idxAddressComplement));
        customer.setObservation(c.getString(idxObservation));
        customer.setFaxNumber(c.getString(idxFax));
        customer.setStreet(c.getString(idxStreet));
        customer.setDistrict(c.getString(idxDistrict));
        customer.setNumber(c.getString(idxNumber));
        customer.setEmail(c.getString(idxEmail));
        customer.setDefaultDiscount(c.getDouble(idxDefaultDiscount));
        customer.setActive(c.getInt(idxActive) == 1);
        customer.setExcluded(c.getInt(idxExcluded) == 1);
        customer.setSyncPending(c.getInt( idxSincronizado ) == 1);
        customer.setCreditLimit(c.getDouble( idxLimiteCredito ));
        customer.setIdMobile(c.getInt(idxIdClienteMobile));

        customer.setPictureUrl(c.getString(idxPicture));
        customer.setPriceTable(c.getInt(idxPriceTable));
        customer.setInstallmentId( c.getInt(idxInstallment) );
        customer.setInstallment( new Installment( c.getInt(idxInstallment) ) );
        customer.setFormPayment(c.getInt(idxPayment));
        customer.setDefaultSeller( c.getInt( idxVendedor ) );

        return  customer;
    }

}

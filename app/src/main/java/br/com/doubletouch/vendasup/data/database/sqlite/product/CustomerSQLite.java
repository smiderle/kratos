package br.com.doubletouch.vendasup.data.database.sqlite.product;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.doubletouch.vendasup.dao.SQLiteHelper;
import br.com.doubletouch.vendasup.data.database.CustomerPersistence;
import br.com.doubletouch.vendasup.data.entity.Customer;


/**
 * Created by LADAIR on 17/02/2015.
 */
public class CustomerSQLite implements CustomerPersistence {

    SQLiteDatabase db;

    public CustomerSQLite(){
        db = SQLiteHelper.getInstance().getWritableDatabase();
    }


    @Override
    public Customer get(Integer id) {
        Customer customer = null;
        String where = Customer.CustomerDB.ID+" = ? ";
        Cursor c = db.query(Customer.CustomerDB.TABELA, Customer.CustomerDB.COLUNAS, where, new String[]{String.valueOf(id)}, null, null, null, null);

        if(c.moveToFirst()){
            customer = getByCursor(c);
        }

        c.close();
        return customer;
    }

    @Override
    public List<Customer> getAll(Integer branchId) {
        {
            String where = Customer.CustomerDB.IDFILIAL +" = ? ";
            ArrayList<Customer> customers = new ArrayList<>();
            Cursor c = db.query(Customer.CustomerDB.TABELA, Customer.CustomerDB.COLUNAS, where, new String[]{String.valueOf(branchId)}, null, null, null, CustomerPersistence.LIMIT);

            if(c.moveToFirst()){
                do {
                    customers.add(getByCursor(c));
                } while (c.moveToNext());
            }
            c.close();
            return  customers;
        }
    }



    @Override
    public void insert(Customer customer) {
        db.insertWithOnConflict(Customer.CustomerDB.TABELA, null, getContentValues(customer), SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public void insert(List<Customer> customers) {
            db.beginTransaction();
            for(Customer customer : customers){
                db.insertWithOnConflict(Customer.CustomerDB.TABELA, null, getContentValues(customer), SQLiteDatabase.CONFLICT_REPLACE);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
    }

    @Override
    public void update(Customer customer) {
        String where = Customer.CustomerDB.ID +" = ? ";
        db.update(Customer.CustomerDB.TABELA, getContentValues(customer), where, new String[]{ String.valueOf(customer.getID()) });
    }

    @Override
    public void delete(Customer customer) {
        throw new UnsupportedOperationException("Não implementado.");
    }


    @Override
    public List<Customer> getByNameOrCustomerId(String name, String customerId, Integer branchId) {
            List<Customer> products = new ArrayList<>();

            StringBuilder where = new StringBuilder();
            where.append("(");
            where.append(Customer.CustomerDB.NOME);
            where.append(" like '%");
            where.append(name);
            where.append("%' OR ");
            where.append(Customer.CustomerDB.ID);
            where.append(" = ? )");
            where.append(" AND ");
            where.append(Customer.CustomerDB.IDFILIAL);
            where.append(" = ? ");

            Cursor c = db.query(Customer.CustomerDB.TABELA, Customer.CustomerDB.COLUNAS, where.toString(), new String[]{ customerId, String.valueOf( branchId )}, null, null, null, CustomerPersistence.LIMIT);

            if(c.moveToFirst()){
                do {
                    products.add(getByCursor(c));
                } while (c.moveToNext());
            }
            c.close();

            return products;
    }

    private ContentValues getContentValues( Customer customer) {
        ContentValues cv = new ContentValues();
        cv.put(Customer.CustomerDB.ID, customer.getID());
        cv.put(Customer.CustomerDB.IDEMPRESA, customer.getOrganizationID());
        cv.put(Customer.CustomerDB.IDFILIAL, customer.getBranchID());
        cv.put(Customer.CustomerDB.IDCLIENTE, customer.getCustomerID());
        cv.put(Customer.CustomerDB.NOME, customer.getName());
        cv.put(Customer.CustomerDB.APELIDO, customer.getNickName());
        cv.put(Customer.CustomerDB.TIPO_PESSOA, customer.getPersonType());
        cv.put(Customer.CustomerDB.CPF_CNPJ, customer.getCpfCnpj());
        cv.put(Customer.CustomerDB.INSCRICAO, customer.getIncricao());
        cv.put(Customer.CustomerDB.FONE_COMERCIAL, customer.getCommercialPhone());
        cv.put(Customer.CustomerDB.FONE_RESIDENCIAL, customer.getHomePhone());
        cv.put(Customer.CustomerDB.FONE_CELULAR, customer.getCellPhone());
        cv.put(Customer.CustomerDB.CEP, customer.getPostalCode());
        cv.put(Customer.CustomerDB.COMPLEMENTO, customer.getAddressComplement());
        cv.put(Customer.CustomerDB.OBSERVACAO, customer.getObservation());
        cv.put(Customer.CustomerDB.FAX, customer.getFaxNumber());
        cv.put(Customer.CustomerDB.RUA, customer.getStreet());
        cv.put(Customer.CustomerDB.BAIRRO, customer.getDistrict());
        cv.put(Customer.CustomerDB.NUMERO, customer.getNumber());
        cv.put(Customer.CustomerDB.EMAIL, customer.getEmail());
        cv.put(Customer.CustomerDB.DESCONTO_PADRAO, customer.getDefaultDiscount());
        cv.put(Customer.CustomerDB.ATIVO, customer.isActive());
        cv.put(Customer.CustomerDB.EXCLUIDO, customer.isExcluded());
        cv.put(Customer.CustomerDB.URL_IMAGEM, customer.getPictureUrl());
        cv.put(Customer.CustomerDB.TABELA_PRECO, customer.getPriceTable());
        //cv.put(Customer.CustomerDB.PARCELAMENTO, customer.getInstallment());
        cv.put(Customer.CustomerDB.FORMA_PAGAMENTO, customer.getFormPayment());
        cv.put(Customer.CustomerDB.SYNC_PENDENTE, customer.isSyncPending());
        return cv;
    }


    private Customer getByCursor(Cursor c){
        int idxId = c.getColumnIndex(Customer.CustomerDB.ID);
        int organizationId = c.getColumnIndex(Customer.CustomerDB.IDEMPRESA);
        int branchId = c.getColumnIndex(Customer.CustomerDB.IDFILIAL);
        int customerId = c.getColumnIndex(Customer.CustomerDB.IDCLIENTE);
        int name = c.getColumnIndex(Customer.CustomerDB.NOME);
        int nickName = c.getColumnIndex(Customer.CustomerDB.APELIDO);
        int idxTypePerson = c.getColumnIndex(Customer.CustomerDB.TIPO_PESSOA);
        int idxCnpjCpf = c.getColumnIndex(Customer.CustomerDB.CPF_CNPJ);
        int idxInscricao = c.getColumnIndex(Customer.CustomerDB.INSCRICAO);
        int idxPhoneComercial = c.getColumnIndex(Customer.CustomerDB.FONE_COMERCIAL);
        int idxPhoneHome = c.getColumnIndex(Customer.CustomerDB.FONE_RESIDENCIAL);
        int idxPhoneCell = c.getColumnIndex(Customer.CustomerDB.FONE_CELULAR);
        int idxPostalCode = c.getColumnIndex(Customer.CustomerDB.CEP);
        int idxAddressComplement = c.getColumnIndex(Customer.CustomerDB.COMPLEMENTO);
        int idxObservation = c.getColumnIndex(Customer.CustomerDB.OBSERVACAO);
        int idxFax = c.getColumnIndex(Customer.CustomerDB.FAX);
        int idxStreet = c.getColumnIndex(Customer.CustomerDB.RUA);
        int idxDistrict = c.getColumnIndex(Customer.CustomerDB.BAIRRO);
        int idxNumber = c.getColumnIndex(Customer.CustomerDB.NUMERO);
        int idxEmail = c.getColumnIndex(Customer.CustomerDB.EMAIL);
        int idxDefaultDiscount = c.getColumnIndex(Customer.CustomerDB.DESCONTO_PADRAO);
        int idxActive = c.getColumnIndex(Customer.CustomerDB.ATIVO);
        int idxExcluded = c.getColumnIndex(Customer.CustomerDB.EXCLUIDO);
        int idxPicture = c.getColumnIndex(Customer.CustomerDB.URL_IMAGEM);
        int idxPriceTable = c.getColumnIndex(Customer.CustomerDB.TABELA_PRECO);
        int idxInstallment = c.getColumnIndex(Customer.CustomerDB.PARCELAMENTO);
        int idxPayment = c.getColumnIndex(Customer.CustomerDB.FORMA_PAGAMENTO);
        int idxSincronizado = c.getColumnIndex(Customer.CustomerDB.SYNC_PENDENTE);

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

        customer.setPictureUrl(c.getString(idxPicture));
        customer.setPriceTable(c.getInt(idxPriceTable));
        //customer.setInstallment(c.getInt(idxInstallment));
        customer.setFormPayment(c.getInt(idxPayment));

        return  customer;
    }

}

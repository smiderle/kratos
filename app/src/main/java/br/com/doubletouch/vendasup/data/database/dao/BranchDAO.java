package br.com.doubletouch.vendasup.data.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

import br.com.doubletouch.vendasup.dao.SQLiteHelper;
import br.com.doubletouch.vendasup.data.database.script.BranchDB;
import br.com.doubletouch.vendasup.data.entity.BranchOffice;
import br.com.doubletouch.vendasup.data.entity.Organization;

/**
 * Created by LADAIR on 23/03/2015.
 */
public class BranchDAO {


    public static final String LIMIT = "50";

    SQLiteDatabase db;

    public BranchDAO(){
        db = SQLiteHelper.getInstance().getWritableDatabase();
    }


    public BranchOffice get(Integer id) {
        BranchOffice branch = null;
        String where = BranchDB.ID+" = ? ";
        Cursor c = db.query(BranchDB.TABELA, BranchDB.COLUNAS, where, new String[]{String.valueOf(id)}, null, null, null, null);

        if(c.moveToFirst()){
            branch = getByCursor(c);
        }

        c.close();
        return branch;
    }


    public List<BranchOffice> getAll() {
        {

            ArrayList<BranchOffice> branches = new ArrayList<>();
            Cursor c = db.query(BranchDB.TABELA, BranchDB.COLUNAS, null,null , null, null, null, LIMIT);

            if(c.moveToFirst()){
                do {
                    branches.add(getByCursor(c));
                } while (c.moveToNext());
            }
            c.close();
            return  branches;
        }
    }



    public void insert(List<BranchOffice> branches) {
        db.beginTransaction();
        for(BranchOffice branch : branches){
            db.insertWithOnConflict(BranchDB.TABELA, null, getContentValues(branch), SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }


    private ContentValues getContentValues( BranchOffice branch) {

        ContentValues cv = new ContentValues();

        cv.put(BranchDB.ID, branch.getBranchOfficeID());
        cv.put(BranchDB.IDEMPRESA, branch.getOrganization().getOrganizationID());
        cv.put(BranchDB.RAZAO_SOCIAL, branch.getRealName());
        cv.put(BranchDB.NOME_FANTASIA, branch.getFancyName());
        cv.put(BranchDB.FONE_FAX, branch.getFaxNumber());
        cv.put(BranchDB.FONE, branch.getStreet());
        cv.put(BranchDB.RUA, branch.getStreet());
        cv.put(BranchDB.BAIRRO, branch.getDistrict());
        cv.put(BranchDB.NUMERO, branch.getNumber());
        cv.put(BranchDB.CEP, branch.getPostalCode());
        cv.put(BranchDB.EMAIL, branch.getEmail());
        cv.put(BranchDB.WEB_SITE, branch.getWebsite());
        cv.put(BranchDB.GERENTE, branch.getManager());
        cv.put(BranchDB.CNPJ, branch.getCnpj());
        cv.put(BranchDB.COMPLEMENTO, branch.getAddressComplement());
        cv.put(BranchDB.DESCONTO_MAXIMO, branch.getMaximumDiscount());
        cv.put(BranchDB.NOTIFICAO_EMAIL, branch.isEmailNotification());
        cv.put(BranchDB.PERMITE_ESTOQUE_NEGATIVO, branch.isNegativeStockProduct());
        cv.put(BranchDB.PERMITE_CADASTRO_CLIENTE, branch.isSellerRegisterCustomer());
        cv.put(BranchDB.ENVIO_EMAIL_CLIENTE, branch.isSendEmailCustomer());
        cv.put(BranchDB.ACAO_LIMITE_CREDITO, branch.getActionCreditLimit());
        cv.put(BranchDB.ACAO_TITULO_VENCIDO, branch.getActionOverdue());

        return cv;
    }


    private BranchOffice getByCursor(Cursor c) {

        int id = c.getColumnIndex(BranchDB.ID);
        int organizationId = c.getColumnIndex(BranchDB.IDEMPRESA);
        int razaoSocial = c.getColumnIndex(BranchDB.RAZAO_SOCIAL);
        int nomeFantasia = c.getColumnIndex(BranchDB.NOME_FANTASIA);
        int fax = c.getColumnIndex(BranchDB.FONE_FAX);
        int fone = c.getColumnIndex(BranchDB.FONE);
        int rua = c.getColumnIndex(BranchDB.RUA);
        int bairro = c.getColumnIndex(BranchDB.BAIRRO);
        int numero = c.getColumnIndex(BranchDB.NUMERO);
        int cep = c.getColumnIndex(BranchDB.CEP);
        int email = c.getColumnIndex(BranchDB.EMAIL);
        int webSite = c.getColumnIndex(BranchDB.WEB_SITE);
        int gerente = c.getColumnIndex(BranchDB.GERENTE);
        int cnpj = c.getColumnIndex(BranchDB.CNPJ);
        int complemento = c.getColumnIndex(BranchDB.COMPLEMENTO);
        int descontoMaximo = c.getColumnIndex(BranchDB.DESCONTO_MAXIMO);
        int notificaoEmail = c.getColumnIndex(BranchDB.NOTIFICAO_EMAIL);
        int mostrarEstoque = c.getColumnIndex(BranchDB.MOSTRAR_ESTOQUE);
        int permiteEstoqueNegativo = c.getColumnIndex(BranchDB.PERMITE_ESTOQUE_NEGATIVO);
        int permiteCastroCliente = c.getColumnIndex(BranchDB.PERMITE_CADASTRO_CLIENTE);
        int envioCliente  = c.getColumnIndex(BranchDB.ENVIO_EMAIL_CLIENTE);
        int acaoTituloVencido = c.getColumnIndex(BranchDB.ACAO_TITULO_VENCIDO);
        int acaoLimiteCredito = c.getColumnIndex(BranchDB.ACAO_LIMITE_CREDITO);

        BranchOffice branch = new BranchOffice();
        branch.setBranchOfficeID(c.getInt(id));
        branch.setOrganization( new Organization( c.getInt(organizationId) ));
        branch.setRealName(c.getString(razaoSocial));
        branch.setFancyName(c.getString(nomeFantasia));
        branch.setFaxNumber(c.getString(fax));
        branch.setPhoneNumber(c.getString(fone));
        branch.setStreet(c.getString(rua));
        branch.setDistrict(c.getString(bairro));
        branch.setNumber(c.getString(numero));
        branch.setPostalCode(c.getString(cep));
        branch.setEmail(c.getString(email));
        branch.setWebsite(c.getString(webSite));
        branch.setManager(c.getString(gerente));
        branch.setCnpj(c.getString(cnpj));
        branch.setAddressComplement(c.getString(complemento));
        branch.setMaximumDiscount( c.getDouble( descontoMaximo) );
        branch.setEmailNotification( c.getInt( notificaoEmail) == 1 );
        branch.setShowStockProduct( c.getInt( mostrarEstoque) == 1 );
        branch.setNegativeStockProduct( c.getInt( permiteEstoqueNegativo) == 1 );
        branch.setSellerRegisterCustomer( c.getInt( permiteCastroCliente) == 1 );
        branch.setSendEmailCustomer( c.getInt( envioCliente) == 1 );
        branch.setActionOverdue(c.getString(acaoTituloVencido));
        branch.setActionCreditLimit(c.getString(acaoLimiteCredito));

        return  branch;
    }

}

package br.com.doubletouch.vendasup.dao.script;

import br.com.doubletouch.vendasup.data.entity.Customer;

/**
 * Created by LADAIR on 17/02/2015.
 */
public class CustomerSQL {

    public static String create(){

        String SQL = "CREATE TABLE IF NOT EXISTS "+ Customer.CustomerDB.TABELA+" (" +
                Customer.CustomerDB.ID + " INTEGER NOT NULL , " +
                Customer.CustomerDB.IDCLIENTE + " TEXT , " +
                Customer.CustomerDB.IDEMPRESA+ " INTEGER NOT NULL , "+
                Customer.CustomerDB.IDFILIAL+ " INTEGER NOT NULL , " +
                Customer.CustomerDB.NOME + " TEXT, " +
                Customer.CustomerDB.TIPO_PESSOA + " TEXT,"+
                Customer.CustomerDB.APELIDO + " TEXT,"+
                Customer.CustomerDB.CPF_CNPJ + " TEXT,"+
                Customer.CustomerDB.INSCRICAO + " TEXT,"+
                Customer.CustomerDB.FONE_COMERCIAL + " TEXT,"+
                Customer.CustomerDB.FONE_RESIDENCIAL + " TEXT,"+
                Customer.CustomerDB.FONE_CELULAR + " TEXT,"+
                Customer.CustomerDB.CEP + " TEXT,"+
                Customer.CustomerDB.COMPLEMENTO + " TEXT,"+
                Customer.CustomerDB.OBSERVACAO + " TEXT,"+
                Customer.CustomerDB.FAX + " TEXT,"+
                Customer.CustomerDB.RUA + " TEXT,"+
                Customer.CustomerDB.BAIRRO + " TEXT,"+
                Customer.CustomerDB.NUMERO + " TEXT,"+
                Customer.CustomerDB.EMAIL + " TEXT,"+
                Customer.CustomerDB.DESCONTO_PADRAO + " TEXT,"+
                Customer.CustomerDB.ATIVO + " BOOLEAN,"+
                Customer.CustomerDB.EXCLUIDO + " BOOLEAN,"+
                Customer.CustomerDB.TABELA_PRECO + " INTEGER,"+
                Customer.CustomerDB.PARCELAMENTO + " INTEGER,"+
                Customer.CustomerDB.FORMA_PAGAMENTO + " INTEGER,"+
                Customer.CustomerDB.URL_IMAGEM + " TEXT,"+
                Customer.CustomerDB.SYNC_PENDENTE + " BOOLEAN,"+
                "PRIMARY KEY( "+ Customer.CustomerDB.ID+" )  );";
        return SQL;
    }
}

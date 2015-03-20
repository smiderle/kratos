package br.com.doubletouch.vendasup.data.database.script;

import br.com.doubletouch.vendasup.data.entity.Customer;

/**
 * Created by LADAIR on 17/02/2015.
 */
public final class CustomerDB {

    private CustomerDB(){}

    public static String create(){

        String SQL = "CREATE TABLE IF NOT EXISTS "+ TABELA+" (" +
                ID + " INTEGER NOT NULL , " +
                IDCLIENTE + " TEXT , " +
                ID_MOBILE + " TEXT , " +
                IDEMPRESA+ " INTEGER NOT NULL , "+
                IDFILIAL+ " INTEGER NOT NULL , " +
                NOME + " TEXT, " +
                TIPO_PESSOA + " TEXT,"+
                APELIDO + " TEXT,"+
                CPF_CNPJ + " TEXT,"+
                INSCRICAO + " TEXT,"+
                FONE_COMERCIAL + " TEXT,"+
                FONE_RESIDENCIAL + " TEXT,"+
                FONE_CELULAR + " TEXT,"+
                CEP + " TEXT,"+
                COMPLEMENTO + " TEXT,"+
                OBSERVACAO + " TEXT,"+
                FAX + " TEXT,"+
                RUA + " TEXT,"+
                BAIRRO + " TEXT,"+
                NUMERO + " TEXT,"+
                EMAIL + " TEXT,"+
                DESCONTO_PADRAO + " TEXT,"+
                ATIVO + " BOOLEAN,"+
                EXCLUIDO + " BOOLEAN,"+
                TABELA_PRECO + " INTEGER,"+
                PARCELAMENTO + " INTEGER,"+
                FORMA_PAGAMENTO + " INTEGER,"+
                LIMITE_CREDITO+ " DOUBLE,"+
                URL_IMAGEM + " TEXT,"+
                SYNC_PENDENTE + " BOOLEAN,"+
                "PRIMARY KEY( "+ ID+" )  );";
        return SQL;
    }


    public static final String TABELA = "CLIENTE";
    public static final String ID = "_id";
    public static final String IDCLIENTE = "IDCLIENTE";
    public static final String ID_MOBILE = "ID_MOBILE";
    public static final String IDEMPRESA = "IDEMPRESA";
    public static final String IDFILIAL = "IDFILIAL";
    public static final String NOME = "NOME";
    public static final String APELIDO = "APELIDO";
    public static final String TIPO_PESSOA = "TIPO_PESSOA";
    public static final String CPF_CNPJ = "CPF_CNPJ";
    public static final String INSCRICAO = "INSCRICAO";
    public static final String FONE_COMERCIAL = "FONE_COMERCIAL";
    public static final String FONE_RESIDENCIAL = "FONE_RESIDENCIAL";
    public static final String FONE_CELULAR = "FONE_CELULAR";
    public static final String CEP = "CEP";
    public static final String COMPLEMENTO = "COMPLEMENTO";
    public static final String OBSERVACAO = "OBSERVACAO";
    public static final String FAX = "FAX";
    public static final String RUA = "RUA";
    public static final String BAIRRO = "BAIRRO";
    public static final String NUMERO = "NUMERO";
    public static final String EMAIL = "EMAIL";
    public static final String LIMITE_CREDITO = "LIMITE_CREDITO";

    public static final String DESCONTO_PADRAO = "DESCONTO_PADRAO";
    public static final String ATIVO = "ATIVO";
    public static final String EXCLUIDO = "EXCLUIDO";
    public static final String TABELA_PRECO = "TABELA_PRECO";
    public static final String PARCELAMENTO = "PARCELAMENTO";
    public static final String FORMA_PAGAMENTO = "FORMA_PAGAMENTO";
    public static final String URL_IMAGEM = "URL_IMAGEM";
    public static final String SYNC_PENDENTE= "SYNC_PENDENTE";

    public static final String[] COLUNAS = new String[]{ID, IDEMPRESA,IDFILIAL,IDCLIENTE, NOME, APELIDO, TIPO_PESSOA, CPF_CNPJ, INSCRICAO, FONE_COMERCIAL,FONE_RESIDENCIAL,FONE_CELULAR, CEP, COMPLEMENTO,
            OBSERVACAO,FAX,RUA,BAIRRO,NUMERO,EMAIL,DESCONTO_PADRAO,ATIVO, EXCLUIDO, TABELA_PRECO, PARCELAMENTO, FORMA_PAGAMENTO,LIMITE_CREDITO,ID_MOBILE, URL_IMAGEM, SYNC_PENDENTE};


}

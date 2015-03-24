package br.com.doubletouch.vendasup.data.database.script;

/**
 * Created by LADAIR on 20/03/2015.
 */
public class UserDB {

    public static final String TABELA = "USUARIO";
    public static final String ID = "_id";
    public static final String IDEMPRESA = "IDEMPRESA";
    public static final String EMAIL = "EMAIL";
    public static final String SENHA = "SENHA";
    public static final String NOME = "NOME";
    public static final String ATIVO = "ATIVO";
    public static final String TELEFONE = "TELEFONE";
    public static final String CELULAR = "CELULAR";
    public static final String URL_IMAGEM = "URL_IMAGEM";
    public static final String LINK_FACEBOOK = "LINK_FACEBOOK";
    public static final String LINK_GOOGLE_PLUS = "LINK_GOOGLE_PLUS";
    public static final String SKYPE = "SKYPE";
    public static final String RUA = "RUA";
    public static final String BAIRRO = "BAIRRO";
    public static final String NUMERO = "NUMERO";
    public static final String CEP = "CEP";
    public static final String CPF_CNPJ = "CPF_CNPJ";
    public static final String INSCRICAO = "INSCRICAO";
    public static final String CONTATO = "CONTATO";
    public static final String OBSERVACAO = "OBSERVACAO";


    public static final String[] COLUNAS = new String[]{ID, IDEMPRESA,SENHA,TELEFONE, NOME, CELULAR, LINK_FACEBOOK, CPF_CNPJ, INSCRICAO, LINK_GOOGLE_PLUS,SKYPE,CONTATO, CEP, OBSERVACAO,RUA,BAIRRO,NUMERO,EMAIL,ATIVO, URL_IMAGEM};


    public static String create(){
        String SQL = "CREATE TABLE IF NOT EXISTS "+ TABELA+" (" +
                ID + " INTEGER NOT NULL , " +
                IDEMPRESA+ " INTEGER NOT NULL , "+
                SENHA+ " TEXT NOT NULL , " +
                NOME + " TEXT, " +
                TELEFONE + " TEXT,"+
                CELULAR + " TEXT,"+
                CPF_CNPJ + " TEXT,"+
                INSCRICAO + " TEXT,"+
                LINK_FACEBOOK + " TEXT,"+
                LINK_GOOGLE_PLUS + " TEXT,"+
                SKYPE + " TEXT,"+
                CEP + " TEXT,"+
                CONTATO + " TEXT,"+
                OBSERVACAO + " TEXT,"+
                RUA + " TEXT,"+
                BAIRRO + " TEXT,"+
                NUMERO + " TEXT,"+
                EMAIL + " TEXT,"+
                ATIVO + " BOOLEAN,"+
                URL_IMAGEM + " TEXT,"+
                "PRIMARY KEY( "+ ID+" )  );";
        return SQL;
    }






}

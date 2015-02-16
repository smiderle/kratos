package br.com.doubletouch.vendasup.data.entity;

/**
 * Created by LADAIR on 09/02/2015.
 */
public class Sincronizacao {

    public Sincronizacao() {
    }

    public Sincronizacao(Long lastSync, String tableName) {
        this.lastSync = lastSync;
        this.tableName = tableName;
    }

    /**
     * Data e Hora da ultima sincronização de determinada tabela.
     * Essa data e hora são obtidas do servidor.
     */
    private Long lastSync;

    /**
     * Nome da tabela sincronizada.
     */
    private String tableName;

    public Long getLastSync() {
        return lastSync;
    }

    public void setLastSync(Long lastSync) {
        this.lastSync = lastSync;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public static final class SincronizacaoDB {

        private SincronizacaoDB(){}

        public static final String TABELA = "SINCRONIZACAO";
        public static final String NOME_TABELA = "NOME_TABELA";
        public static final String ULTIMA_SINCRONIZACAO = "ULTIMA_SINCRONIZACAO";

        public static final String[] COLUNAS = new String[]{ NOME_TABELA, ULTIMA_SINCRONIZACAO };
    }
}

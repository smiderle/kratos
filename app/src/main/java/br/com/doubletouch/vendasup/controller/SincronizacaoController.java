package br.com.doubletouch.vendasup.controller;

import br.com.doubletouch.vendasup.dao.SincronizacaoDAO;
import br.com.doubletouch.vendasup.data.entity.Sincronizacao;

/**
 * Created by LADAIR on 09/02/2015.
 */
public class SincronizacaoController {

    public Sincronizacao findByTableName(String tableName){
        SincronizacaoDAO sincronizacaoDAO = new SincronizacaoDAO();
        return sincronizacaoDAO.findByTableName(tableName);
    }

    /**
     * Retorna a hora da ultima sincronização de determinada tabela. Se o registro não existir, retorna o valor 1L.
     * @return
     */
    public Long findLastSync(String tableName){
        Sincronizacao sincronizacao = findByTableName(tableName);
        if(sincronizacao == null){
            return 1L;
        } else {
            return sincronizacao.getLastSync();
        }
    }

    /**
     * Atualiza o registro com a ultima data de sincronização
     * @param lastSync
     * @param tableName
     */
    public void updateLastSync(Long lastSync,  String tableName){
        Sincronizacao sincronizacao = new Sincronizacao(lastSync, tableName);
        SincronizacaoDAO sincronizacaoDAO = new SincronizacaoDAO();
        sincronizacaoDAO.saveOrUpdate(sincronizacao);
    }
}

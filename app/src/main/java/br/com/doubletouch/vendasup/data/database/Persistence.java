package br.com.doubletouch.vendasup.data.database;

import java.util.List;

/**
 * Created by LADAIR on 13/02/2015.
 */
public interface Persistence<T> {

    /**
     * Retorna o registro pelo ID.
     * @param id
     * @return
     */
    T get(Integer id);

    /**
     * Retorna todos os registros.
     * @return
     */
    List<T> getAll(Integer branchId);

    /**
     * Insere um registro.
     * @param t
     */
    void insert(T t);

    /**
     * Insere uma lista de registros.
     * @param t
     */
    void insert(List<T> t);

    /**
     * Atualiza um registro.
     * @param t
     */
    void update(T t);

    /**
     * Remove um registro.
     * @param t
     */
    void delete(T t);
}

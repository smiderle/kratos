package br.com.doubletouch.vendasup.dao.script;

import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.PriceTable;

/**
 * Created by LADAIR on 17/02/2015.
 */
public class PriceTableSQL {

    public static String create(){

        String SQL = "CREATE TABLE IF NOT EXISTS "+ PriceTable.PriceTableDB.TABELA+" (" +
                PriceTable.PriceTableDB.ID + " INTEGER NOT NULL , " +
                PriceTable.PriceTableDB.IDEMPRESA+ " INTEGER NOT NULL, " +
                PriceTable.PriceTableDB.IDFILIAL+ " INTEGER NOT NULL, " +
                PriceTable.PriceTableDB.IDTABELAPRECO+ " INTEGER , " +
                PriceTable.PriceTableDB.DESCRICAO + " TEXT, " +
                PriceTable.PriceTableDB.PERCENTAGE +" DOUBLE "+
                PriceTable.PriceTableDB.ACRESCIMO +" BOOLEAN "+
                PriceTable.PriceTableDB.ATIVO+" BOOLEAN "+
                PriceTable.PriceTableDB.EXCLUIDO+" BOOLEAN "+
                "PRIMARY KEY( "+ Customer.CustomerDB.ID+" )  );";
        return SQL;
    }
}

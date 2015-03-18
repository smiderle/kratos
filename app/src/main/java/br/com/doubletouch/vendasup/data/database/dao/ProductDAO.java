package br.com.doubletouch.vendasup.data.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.doubletouch.vendasup.dao.SQLiteHelper;
import br.com.doubletouch.vendasup.data.database.script.ProductDB;
import br.com.doubletouch.vendasup.data.entity.Product;

/**
 * Created by LADAIR on 13/02/2015.
 */
public class ProductDAO {

    public static final String LIMIT = "50";

    SQLiteDatabase db;

    public ProductDAO(){
        db = SQLiteHelper.getInstance().getWritableDatabase();
    }

    public Product get(Integer id) {
        Product product = null;
        String where = ProductDB.ID+" = ? ";
        Cursor c = db.query(ProductDB.TABELA, ProductDB.COLUNAS, where, new String[]{String.valueOf(id)}, null, null, null, null);

        if(c.moveToFirst()){
            product = getByCursor(c);
        }

        c.close();
        return product;
    }

    public List<Product> getAll(Integer branchId){
        ArrayList<Product> products = new ArrayList<>();

        String where = ProductDB.IDFILIAL+" = ? ";
        Cursor c = db.query(ProductDB.TABELA, ProductDB.COLUNAS, where, new String[]{String.valueOf(branchId)}, null, null, null, LIMIT);

        if(c.moveToFirst()){
            do {
                products.add(getByCursor(c));
            } while (c.moveToNext());
        }
        c.close();
        return  products;
    }

    public void insert( Product product){
        db.insertWithOnConflict(ProductDB.TABELA, null, getContentValues(product), SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void insert(List<Product> products) {
        db.beginTransaction();
        for(Product product : products){
            db.insertWithOnConflict(ProductDB.TABELA, null, getContentValues(product), SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void update( Product product){
        String where = ProductDB.ID +" = ? ";
        db.update(ProductDB.TABELA, getContentValues(product), where, new String[]{ String.valueOf(product.getID()) });
    }


    public List<Product> getByDescriptionOrProductId(String description, String productId, Integer branchId) {
        List<Product> products = new ArrayList<>();

        StringBuilder where = new StringBuilder();
        where.append("(");
        where.append(ProductDB.DESCRICAO);
        where.append(" like '%");
        where.append(description);
        where.append("%' OR ");
        where.append(ProductDB.ID);
        where.append(" = ? )");
        where.append(" AND ");
        where.append(ProductDB.IDFILIAL);
        where.append(" = ? ");

        Cursor c = db.query(ProductDB.TABELA, ProductDB.COLUNAS, where.toString(), new String[]{ productId, String.valueOf( branchId )}, null, null, null, LIMIT);

        if(c.moveToFirst()){
            do {
                products.add(getByCursor(c));
            } while (c.moveToNext());
        }
        c.close();

        return products;
    }

    public void delete(Product product) {
        new UnsupportedOperationException("Não implementado");
    }

    private ContentValues getContentValues( Product product){
        ContentValues cv = new ContentValues();
        cv.put(ProductDB.ATIVO, product.getActive());
        cv.put(ProductDB.CODBAR, product.getBarcode());
        cv.put(ProductDB.DESCRICAO, product.getDescription());
        cv.put(ProductDB.EMBALAGEM, product.getPackaging());
        cv.put(ProductDB.ESTOQUE, product.getStockAmount());
        cv.put(ProductDB.EXCLUIDO, product.getExcluded());
        cv.put(ProductDB.ID, product.getID());
        cv.put(ProductDB.IDEMPRESA, product.getOrganizationID());
        cv.put(ProductDB.IDFILIAL, product.getBranchID());
        cv.put(ProductDB.IDPRODUTO, product.getProductID());
        cv.put(ProductDB.PRECO_VENDA, product.getSalePrice());
        cv.put(ProductDB.URL_IMAGEM, product.getPictureUrl());
        cv.put(ProductDB.REFERENCIA, product.getReference());

        return cv;
    }

    /**
     * Converte o cursor passado por parametro em um produto. Quem esta chamando esse metodo deve OBRIGATORIAMENTE, fechar o cursor.
     * @param c
     * @return produto
     */
    private Product getByCursor(Cursor c){
        int idxIdProduto = c.getColumnIndex(ProductDB.IDPRODUTO);
        int idxIdFilial = c.getColumnIndex(ProductDB.IDFILIAL);
        int idxId = c.getColumnIndex(ProductDB.ID);
        int idxIdEmpresa = c.getColumnIndex(ProductDB.IDEMPRESA);
        int idxDescricao = c.getColumnIndex(ProductDB.DESCRICAO);
        int idxEstoque = c.getColumnIndex(ProductDB.ESTOQUE);
        int idxAtivo = c.getColumnIndex(ProductDB.ATIVO);
        int idxCodbar = c.getColumnIndex(ProductDB.CODBAR);
        int idxPreco = c.getColumnIndex(ProductDB.PRECO_VENDA);
        int idxEmbalagem = c.getColumnIndex(ProductDB.EMBALAGEM);
        int idxExcluido = c.getColumnIndex(ProductDB.EXCLUIDO);
        int idxReferencia = c.getColumnIndex(ProductDB.REFERENCIA);
        int idxUrlImagen = c.getColumnIndex(ProductDB.URL_IMAGEM);


        Product product = new Product();
        product.setID(c.getInt(idxId));
        product.setProductID(c.getString(idxIdProduto));
        product.setOrganizationID(c.getInt(idxIdEmpresa));
        product.setBranchID(c.getInt(idxIdFilial));
        product.setStockAmount(c.getDouble(idxEstoque));
        product.setSalePrice(c.getDouble(idxPreco));
        product.setBarcode(c.getString(idxCodbar));
        product.setExcluded( c.getInt(idxExcluido) == 1 );
        product.setReference(c.getString(idxReferencia));
        product.setPictureUrl(c.getString(idxUrlImagen));
        product.setDescription(c.getString(idxDescricao));
        product.setActive( c.getInt(idxAtivo) == 1 );
        product.setPackaging(c.getString(idxEmbalagem));

        return product;
    }

}

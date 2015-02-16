package br.com.doubletouch.vendasup.data.database.sqlite.product;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.doubletouch.vendasup.dao.SQLiteHelper;
import br.com.doubletouch.vendasup.data.database.ProductPersistence;
import br.com.doubletouch.vendasup.data.entity.Product;

/**
 * Created by LADAIR on 13/02/2015.
 */
public class ProductSQLite implements ProductPersistence {

    SQLiteDatabase db;

    public ProductSQLite(){
        db = SQLiteHelper.getInstance().getWritableDatabase();
    }

    @Override
    public Product get(Integer id) {
        Product product = null;
        String where = Product.ProductDB.ID+" = ? ";
        Cursor c = db.query(Product.ProductDB.TABELA, Product.ProductDB.COLUNAS, where, new String[]{String.valueOf(id)}, null, null, null, null);

        if(c.moveToFirst()){
            product = getByCursor(c);
        }

        c.close();
        return product;
    }

    @Override
    public List<Product> getAll(){
        ArrayList<Product> products = new ArrayList<Product>();
        Cursor c = db.query(Product.ProductDB.TABELA, Product.ProductDB.COLUNAS, null, null, null, null, null, "2500");

        if(c.moveToFirst()){
            do {
                products.add(getByCursor(c));
            } while (c.moveToNext());
        }
        c.close();
        return  products;
    }

    @Override
    public void insert( Product product){
        db.insertOrThrow(Product.ProductDB.TABELA, null, getContentValues(product));
    }

    @Override
    public void update( Product product){
        String where = Product.ProductDB.ID +" = ? ";
        db.update(Product.ProductDB.TABELA, getContentValues(product), where, new String[]{ String.valueOf(product.getID()) });
    }

    @Override
    public void delete(Product product) {
        new UnsupportedOperationException("Não implementado");
    }

    private ContentValues getContentValues( Product product){
        ContentValues cv = new ContentValues();
        cv.put(Product.ProductDB.ATIVO, product.getActive());
        cv.put(Product.ProductDB.CODBAR, product.getBarcode());
        cv.put(Product.ProductDB.DESCRICAO, product.getDescription());
        cv.put(Product.ProductDB.EMBALAGEM, product.getPackaging());
        cv.put(Product.ProductDB.ESTOQUE, product.getStockAmount());
        cv.put(Product.ProductDB.EXCLUIDO, product.getExcluded());
        cv.put(Product.ProductDB.ID, product.getID());
        cv.put(Product.ProductDB.IDEMPRESA, product.getOrganizationID());
        cv.put(Product.ProductDB.IDFILIAL, product.getBranchID());
        cv.put(Product.ProductDB.IDPRODUTO, product.getProductID());
        cv.put(Product.ProductDB.PRECO_VENDA, product.getSalePrice());
        cv.put(Product.ProductDB.URL_IMAGEM, product.getPictureUrl());
        cv.put(Product.ProductDB.REFERENCIA, product.getReference());

        return cv;
    }

    /**
     * Converte o cursor passado por parametro em um produto. Quem esta chamando esse metodo deve OBRIGATORIAMENTE, fechar o cursor.
     * @param c
     * @return produto
     */
    private Product getByCursor(Cursor c){
        int idxIdProduto = c.getColumnIndex(Product.ProductDB.IDPRODUTO);
        int idxIdFilial = c.getColumnIndex(Product.ProductDB.IDFILIAL);
        int idxId = c.getColumnIndex(Product.ProductDB.ID);
        int idxIdEmpresa = c.getColumnIndex(Product.ProductDB.IDEMPRESA);
        int idxDescricao = c.getColumnIndex(Product.ProductDB.DESCRICAO);
        int idxEstoque = c.getColumnIndex(Product.ProductDB.ESTOQUE);
        int idxAtivo = c.getColumnIndex(Product.ProductDB.ATIVO);
        int idxCodbar = c.getColumnIndex(Product.ProductDB.CODBAR);
        int idxPreco = c.getColumnIndex(Product.ProductDB.PRECO_VENDA);
        int idxEmbalagem = c.getColumnIndex(Product.ProductDB.EMBALAGEM);
        int idxExcluido = c.getColumnIndex(Product.ProductDB.EXCLUIDO);
        int idxReferencia = c.getColumnIndex(Product.ProductDB.REFERENCIA);
        int idxUrlImagen = c.getColumnIndex(Product.ProductDB.URL_IMAGEM);


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

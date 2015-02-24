package br.com.doubletouch.vendasup.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Product;

/**
 * Created by LADAIR on 26/01/2015.
 */
@Deprecated
public class ProdutoDAO extends AbstractDAO {

    SQLiteDatabase db;

    public ProdutoDAO(){
        db = SQLiteHelper.getInstance().getWritableDatabase();
    }

    public void insert( Product product){
        ContentValues cv = getContentValues(product);
        db.insert(Product.ProductDB.TABELA, null, cv);
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

    public long save( Product product){
        return db.insertOrThrow(Product.ProductDB.TABELA, null, getContentValues(product));
    }


    public long save( List<Product> products){
        int i = 0;
        //return db.insertOrThrow(Product.ProductDB.TABELA, null, getContentValues(product));
        Log.i("KRATOS", "INICIOU OS INSERTS" + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        db.beginTransaction();
        for(Product product : products){
            db.insertWithOnConflict(Product.ProductDB.TABELA, null, getContentValues(product), SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        Log.i("KRATOS", "FINALIZOU OS INSERTS" + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        return 1l;
        //return db.insertWithOnConflict(Product.ProductDB.TABELA, null, getContentValues(product), SQLiteDatabase.CONFLICT_REPLACE);
    }

    public long update( Product product){
        String where = Product.ProductDB.ID +" = ? ";
        return db.update(Product.ProductDB.TABELA, getContentValues(product), where, new String[]{ String.valueOf(product.getID()) });
    }

    public void saveOrUpdate( Product product){

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT OR REPLACE INTO ");
        sb.append(Product.ProductDB.TABELA);
        sb.append("(");
        sb.append(Product.ProductDB.IDEMPRESA);
        sb.append(",");
        sb.append(Product.ProductDB.IDFILIAL);
        sb.append(",");
        sb.append(Product.ProductDB.ID);
        sb.append(",");
        sb.append(Product.ProductDB.IDPRODUTO);
        sb.append(",");
        sb.append(Product.ProductDB.ATIVO);
        sb.append(",");
        sb.append(Product.ProductDB.CODBAR);
        sb.append(",");
        sb.append(Product.ProductDB.DESCRICAO);
        sb.append(",");
        sb.append(Product.ProductDB.EMBALAGEM);
        sb.append(",");
        sb.append(Product.ProductDB.ESTOQUE);
        sb.append(",");
        sb.append(Product.ProductDB.EXCLUIDO);
        sb.append(",");
        sb.append(Product.ProductDB.PRECO_VENDA);
        sb.append(",");
        sb.append(Product.ProductDB.REFERENCIA);
        sb.append(",");
        sb.append(Product.ProductDB.URL_IMAGEM);
        sb.append(")");
        sb.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");

        String[] values = {
                String.valueOf( product.getOrganizationID()),
                String.valueOf( product.getBranchID() ),
                String.valueOf( product.getID() ),
                String.valueOf( product.getProductID() ),
                String.valueOf( product.getActive() ),
                String.valueOf(product.getBarcode()),
                String.valueOf(product.getDescription()),
                String.valueOf(product.getPackaging()),
                String.valueOf(product.getStockAmount()),
                String.valueOf(product.getExcluded() ),
                String.valueOf(product.getSalePrice()),
                String.valueOf(product.getReference()),
                String.valueOf(product.getPictureUrl())
        };

        db.execSQL(sb.toString(), values);


    }

    public ArrayList<Product> getAll(){
        Cursor c = db.query(Product.ProductDB.TABELA, Product.ProductDB.COLUNAS, null, null, null, null, null, "2500");
        return getAll(c);
    }

    public ArrayList<Product> getAll(Cursor c){
        ArrayList<Product> products = new ArrayList<>();
        if(c.moveToFirst()){
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

            do{
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
                products.add(product);
            } while(c.moveToNext());
        }
        c.close();

        return products;
    }
}

package br.com.doubletouch.vendasup.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collection;
import java.util.List;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.util.DoubleUtil;
import br.com.doubletouch.vendasup.util.image.ImageLoader;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Adapter que gerencia a collection {@link br.com.doubletouch.vendasup.data.entity.Usuario}
 * Created by LADAIR on 12/02/2015.
 */
public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    private ImageLoader imageLoader;

    public interface OnItemClickListener {
        void onProductItemClicked(Product product);
    }

    private List<Product> productsCollection;
    private final LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;

    public ProductsAdapter(Context context, Collection<Product> productsCollection) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.productsCollection = (List<Product>) productsCollection;

        imageLoader = new ImageLoader(context);
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = this.layoutInflater.inflate(R.layout.row_product, parent, false);
        ProductViewHolder productViewHolder = new ProductViewHolder(view);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        final Product product = this.productsCollection.get(position);

        holder.lblProdutoDescricao.setText(product.getProductIdAndDescription());
        holder.lblProdutoEstoque.setText(DoubleUtil.formatToCurrency(product.getStockAmount(), false));
        holder.lblProdutoPreco.setText( DoubleUtil.formatToCurrency( product.getSalePrice() , true));

        imageLoader.displayImage(product.getPictureUrl(), holder.imgProduto);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ProductsAdapter.this.onItemClickListener != null){
                    ProductsAdapter.this.onItemClickListener.onProductItemClicked(product);
                }
            }
        });
    }

    public void setProductsCollection(Collection<Product> productsCollection){
        this.validateProductsCollection(productsCollection);
        this.productsCollection = (List<Product>) productsCollection;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (this.productsCollection != null) ? this.productsCollection.size() : 0;
    }


    @Override public long getItemId(int position) {
        return position;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    private void validateProductsCollection(Collection<Product> productsCollection){
        if(productsCollection == null){
            throw new IllegalArgumentException("A lista não pode ser null");
        }
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.lblProdutoDescricao)
        TextView lblProdutoDescricao;

        @InjectView(R.id.tv_stock)
        TextView lblProdutoEstoque;

        @InjectView(R.id.lblProdutoPreco)
        TextView lblProdutoPreco;

        @InjectView(R.id.imgImagemProduto)
        ImageView imgProduto;

        ProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

}

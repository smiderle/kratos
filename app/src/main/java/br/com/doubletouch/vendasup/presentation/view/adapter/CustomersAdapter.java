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
import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.util.image.ImageLoader;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Adapter que gerencia a collection {@link br.com.doubletouch.vendasup.data.entity.Usuario}
 * Created by LADAIR on 12/02/2015.
 */
public class CustomersAdapter extends RecyclerView.Adapter<CustomersAdapter.CustomerViewHolder> {

    private ImageLoader imageLoader;

    public interface OnItemClickListener {
        void onCustomerItemClicked(Customer customer);
    }

    private List<Customer> customersCollection;
    private final LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;

    public CustomersAdapter(Context context, Collection<Customer> customersCollection) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.customersCollection = (List<Customer>) customersCollection;

        imageLoader = new ImageLoader(context);
    }

    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = this.layoutInflater.inflate(R.layout.row_customer, parent, false);
        CustomerViewHolder customerViewHolder = new CustomerViewHolder(view);
        return customerViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomerViewHolder holder, int position) {
        final Customer customer = this.customersCollection.get(position);

        holder.tv_customer_name.setText(customer.getCustomerIdAndName());
        holder.tv_customer_phone.setText(getSomePhone(customer));

        imageLoader.displayImage(customer.getPictureUrl(), holder.img_customer_picture, R.drawable.jorge);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CustomersAdapter.this.onItemClickListener != null){
                    CustomersAdapter.this.onItemClickListener.onCustomerItemClicked(customer);
                }
            }
        });
    }

    public void setCustomersCollection(Collection<Customer> customersCollection){
        this.validateCustomersCollection(customersCollection);
        this.customersCollection = (List<Customer>) customersCollection;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (this.customersCollection != null) ? this.customersCollection.size() : 0;
    }


    @Override public long getItemId(int position) {
        return position;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    private void validateCustomersCollection(Collection<Customer> customersCollection){
        if(customersCollection == null){
            throw new IllegalArgumentException("A lista não pode ser null");
        }
    }

    private String getSomePhone(Customer customer){
        String phone = "Nenhum Telefone Cadastrado";
        if(customer.getCellPhone() != null){
            phone = customer.getCellPhone();
        } else if(customer.getCommercialPhone() != null){
            phone = customer.getCommercialPhone();
        } else  if(customer.getHomePhone() != null){
            phone = customer.getHomePhone();
        }

        return  phone;
    }

    static class CustomerViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.tv_customer_name)
        TextView tv_customer_name;

        @InjectView(R.id.tv_customer_phone)
        TextView tv_customer_phone;

        @InjectView(R.id.iv_order_status)
        ImageView img_customer_picture;

        CustomerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

}

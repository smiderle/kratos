package br.com.doubletouch.vendasup.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.Installment;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by LADAIR on 27/07/2015.
 */
public class InstallmentAdapter extends RecyclerView.Adapter<InstallmentAdapter.InstallmentViewHolder>  {


    private final LayoutInflater layoutInflater;
    private List<Installment> installments;


    public interface OnInstallmentClickListener {
        void onInstallmentItemClicked(Installment installment);
    }

    private OnInstallmentClickListener onInstallmentClickListener;

    public InstallmentAdapter(Context context, List<Installment> installments) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.installments = installments;
    }

    @Override
    public InstallmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.row_installment, parent, false);
        InstallmentViewHolder installmentViewHolder = new InstallmentViewHolder(view);

        return installmentViewHolder;
    }

    @Override
    public void onBindViewHolder(InstallmentViewHolder holder, int position) {
        final Installment installment = installments.get( position );
        holder.tv_installment_descrition.setText( installment.getDescription() );
        holder.tv_installment_condition.setText( installment.getInstallmentsDays() );

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(InstallmentAdapter.this.onInstallmentClickListener != null){
                    InstallmentAdapter.this.onInstallmentClickListener.onInstallmentItemClicked(installment);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.installments.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class InstallmentViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.tv_installment_descrition)
        TextView tv_installment_descrition;

        @InjectView(R.id.et_installment_condition)
        TextView tv_installment_condition;

        InstallmentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    public void setInstallmentCollection(List<Installment> installments){
        this.installments = installments;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnInstallmentClickListener onInstallmentClickListener){
        this.onInstallmentClickListener = onInstallmentClickListener;
    }
}

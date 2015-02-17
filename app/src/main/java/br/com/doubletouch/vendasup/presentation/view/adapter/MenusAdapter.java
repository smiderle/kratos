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
import br.com.doubletouch.vendasup.presentation.MenuModel;
import br.com.doubletouch.vendasup.util.DoubleUtil;
import br.com.doubletouch.vendasup.util.image.ImageLoader;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 *
 * Created by LADAIR on 17/02/2015.
 */
public class MenusAdapter  extends RecyclerView.Adapter<MenusAdapter.MenuViewHolder>{

    private final LayoutInflater layoutInflater;
    private List<MenuModel> menus ;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onMenuItemClicked(MenuModel menu);
    }



    public MenusAdapter(Context context, List<MenuModel> menus) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.menus = menus;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.row_menu, parent, false);
        MenuViewHolder menuViewHolder = new MenuViewHolder(view);
        return menuViewHolder;
    }


    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        final MenuModel menuModel = this.menus.get(position);


        holder.tv_menu_description.setText(menuModel.getTitle());
        holder.iv_menu_icon.setImageResource(menuModel.getIcon());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MenusAdapter.this.onItemClickListener != null){
                    MenusAdapter.this.onItemClickListener.onMenuItemClicked(menuModel);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return menus.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    static class MenuViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.iv_menu_icon)
        ImageView iv_menu_icon;

        @InjectView(R.id.tv_menu_description)
        TextView tv_menu_description;

        MenuViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    public void setMenusCollection(List<MenuModel> menus){
        this.menus = menus;
        this.notifyDataSetChanged();
    }
}

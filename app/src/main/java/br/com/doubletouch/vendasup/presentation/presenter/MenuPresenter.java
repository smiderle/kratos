package br.com.doubletouch.vendasup.presentation.presenter;

import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.presentation.MenuModel;
import br.com.doubletouch.vendasup.presentation.view.MenuView;
import br.com.doubletouch.vendasup.presentation.view.activity.ProductListActivity;

/**
 * Created by LADAIR on 17/02/2015.
 */
public class MenuPresenter implements Presenter {

    private MenuView menuView;


    public MenuPresenter(MenuView menuView) {
        this.menuView = menuView;
    }

    public void initialize() {
        this.loadMenuList();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    public void loadMenuList(){
        menuView.renderMenuView(createMenuModel());
    }

    public List<MenuModel>  createMenuModel(){
        List<MenuModel> menus = new ArrayList<>(5);
        menus.add(new MenuModel(ProductListActivity.class, "Novo Pedido", R.drawable.ic_cube_outline,0));
        menus.add(new MenuModel(ProductListActivity.class, "Produtos", R.drawable.ic_cube_outline,1));
        menus.add(new MenuModel(ProductListActivity.class, "Clientes", R.drawable.ic_cube_outline,2));
        menus.add(new MenuModel(ProductListActivity.class, "Histórico", R.drawable.ic_cube_outline,3));
        return menus;
    }

    public void onMenuClicked(MenuModel menuModel){
        this.menuView.goTo(menuModel);
    }
}

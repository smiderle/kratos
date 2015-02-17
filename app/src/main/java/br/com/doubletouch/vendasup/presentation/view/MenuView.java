package br.com.doubletouch.vendasup.presentation.view;

import java.util.List;

import br.com.doubletouch.vendasup.presentation.MenuModel;

/**
 * Created by LADAIR on 17/02/2015.
 */
public interface MenuView {

    void renderMenuView(List<MenuModel> menus);

    void goTo(MenuModel menuModel);
}

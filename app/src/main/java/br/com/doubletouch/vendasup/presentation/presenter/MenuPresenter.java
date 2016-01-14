package br.com.doubletouch.vendasup.presentation.presenter;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.Installment;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.interactor.target.GetTotalDailySalesUseCase;
import br.com.doubletouch.vendasup.presentation.MenuModel;
import br.com.doubletouch.vendasup.presentation.exception.ErrorMessageFactory;
import br.com.doubletouch.vendasup.presentation.view.MenuView;
import br.com.doubletouch.vendasup.presentation.view.activity.CustomerDetailsActivity;
import br.com.doubletouch.vendasup.presentation.view.activity.CustomerListActivity;
import br.com.doubletouch.vendasup.presentation.view.activity.InstallmentActivity;
import br.com.doubletouch.vendasup.presentation.view.activity.LoginActivity;
import br.com.doubletouch.vendasup.presentation.view.activity.SynchronizationActivity;
import br.com.doubletouch.vendasup.presentation.view.activity.UserDetailsActivity;
import br.com.doubletouch.vendasup.presentation.view.activity.chart.ChartListActivity;
import br.com.doubletouch.vendasup.presentation.view.activity.order.OrderActivity;
import br.com.doubletouch.vendasup.presentation.view.activity.ProductListActivity;
import br.com.doubletouch.vendasup.presentation.view.activity.order.OrderListActivity;
import br.com.doubletouch.vendasup.util.DateUtil;

/**
 * Created by LADAIR on 17/02/2015.
 */
public class MenuPresenter implements Presenter {

    private MenuView menuView;

    private final GetTotalDailySalesUseCase getTotalDailySalesUseCase;

    public MenuPresenter(MenuView menuView, GetTotalDailySalesUseCase getTotalDailySalesUseCase) {
        this.menuView = menuView;
        this.getTotalDailySalesUseCase = getTotalDailySalesUseCase;
    }

    public void initialize() {
        this.loadMenuList();
        loadTotalDailySales();

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    private void loadTotalDailySales() {

        Calendar dtInicio = DateUtil.getMinimunDate(new Date());
        Calendar dtFim = DateUtil.getMaximunDate(new Date());


        getTotalDailySalesUseCase.execute(dtInicio.getTimeInMillis(), dtFim.getTimeInMillis() , getTotalDailySalesCallback);
    }

    public void loadMenuList(){
        menuView.renderMenuView(createMenuModel());
    }

    public List<MenuModel>  createMenuModel(){
        List<MenuModel> menus = new ArrayList<>(5);
        menus.add(new MenuModel(OrderActivity.class, "Nova Venda", R.drawable.ic_shopping_cart_grey600_36dp,0));
        menus.add(new MenuModel(ProductListActivity.class, "Produtos", R.drawable.ic_cube_outline,1));
        menus.add(new MenuModel(CustomerListActivity.class, "Clientes", R.drawable.ic_person_grey600_36dp,2));
        menus.add(new MenuModel(OrderListActivity.class, "Histórico de Vendas", R.drawable.ic_history_grey600_36dp,3));
        menus.add(new MenuModel(InstallmentActivity.class, "Parcelamento", R.drawable.ic_format_list_numbered_grey600_36dp,4));
        menus.add(new MenuModel(ChartListActivity.class, "Relatórios", R.drawable.ic_insert_chart_grey600_36dp,4));
        menus.add(new MenuModel(UserDetailsActivity.class, "Perfil", R.drawable.ic_account_circle_grey600_36dp,4));
        menus.add(new MenuModel(SynchronizationActivity.class, "Enviar e Receber Dados", R.drawable.ic_import_export_grey600_36dp,4));
        menus.add(new MenuModel(LoginActivity.class, "Sair", R.drawable.ic_exit_to_app_grey600_36dp,4));

        //menus.add(new MenuModel(TFCustomerDetails.class, "TFCustomerDetails", R.drawable.ic_cube_outline,3));

        return menus;
    }

    public void onMenuClicked(MenuModel menuModel){
        this.menuView.goTo(menuModel);
    }

    private GetTotalDailySalesUseCase.Callback getTotalDailySalesCallback = new GetTotalDailySalesUseCase.Callback() {

        @Override
        public void onDataLoaded( Double value ) {

            if( value == null ){
                value = 0.0;
            }

            menuView.loadTotalDailySales( value );
        }

        @Override
        public void onError(ErrorBundle errorBundle) {

            showErrorMessage( errorBundle );
        }
    };

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.menuView.getContext(),
                errorBundle.getException());
        this.menuView.showError(errorMessage);
    }

}

package br.com.doubletouch.vendasup.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.entity.enumeration.ViewMode;
import br.com.doubletouch.vendasup.data.executor.JobExecutor;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.domain.interactor.target.GetTotalDailySalesUseCase;
import br.com.doubletouch.vendasup.domain.interactor.target.GetTotalDailySalesUseCaseImpl;
import br.com.doubletouch.vendasup.presentation.MenuModel;
import br.com.doubletouch.vendasup.presentation.UIThread;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.presenter.MenuPresenter;
import br.com.doubletouch.vendasup.presentation.view.MenuView;
import br.com.doubletouch.vendasup.presentation.view.activity.LoginActivity;
import br.com.doubletouch.vendasup.presentation.view.activity.order.OrderActivity;
import br.com.doubletouch.vendasup.presentation.view.adapter.KratosLayoutManager;
import br.com.doubletouch.vendasup.presentation.view.adapter.MenusAdapter;
import br.com.doubletouch.vendasup.util.DoubleUtil;
import br.com.doubletouch.vendasup.util.image.ImageLoader;
import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 *
 * Created by LADAIR on 17/02/2015.
 */
public class MenuFragment extends BaseFragment implements MenuView {


    public interface MenuListListener {
        void onMenuClicked(final MenuModel menuModel);
    }

    private MenuPresenter menuPresenter;
    private MenusAdapter menuApdater;
    private MenuListListener menuListListener;
    private KratosLayoutManager kratosLayoutManager;

    @InjectView(R.id.rv_menu)
    RecyclerView rv_menus;

    @InjectView(R.id.img_user_picture)
    ImageView img_user_picture;

    @InjectView(R.id.tv_menu_daily_sales)
    TextView tv_menu_daily_sales;

    private ImageLoader imageLoader;

    private Navigator navigator;

    private Activity activity;

    private static final int RESULT_MENU = 0;

    public MenuFragment() {
        super();
    }

    @Override
    public void initializePresenter() {

        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();
        GetTotalDailySalesUseCase getTotalDailySalesUseCase = new GetTotalDailySalesUseCaseImpl(threadExecutor, postExecutionThread);

        menuPresenter = new MenuPresenter(this, getTotalDailySalesUseCase);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_menu, container, false);
        ButterKnife.inject(this, fragmentView);

        setupUI();
        return fragmentView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        navigator = new Navigator();
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof MenuListListener){
            this.menuListListener = (MenuListListener) activity;
        }
        imageLoader = new ImageLoader(activity);

        this.activity = activity;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        this.loadProductList();
    }

    private void loadProductList(){
        this.menuPresenter.initialize();
    }

    @Override
    public void renderMenuView(List<MenuModel> menus) {

        if(this.menuApdater == null){

            this.menuApdater = new MenusAdapter(getActivity(), menus);

        } else {

            this.menuApdater.setMenusCollection(menus);

        }

        this.menuApdater.setOnItemClickListener(onItemClickListener);

        this.rv_menus.setAdapter(this.menuApdater);

    }

    @Override
    public void goTo( MenuModel menuModel ) {

        if( menuModel.getTo().equals( OrderActivity.class) ){

            Intent it = OrderActivity.getCallingIntent( activity, -1, ViewMode.INCLUSAO );
            startActivityForResult( it, RESULT_MENU);
            navigator.transitionGo(activity);

        } else if(menuModel.getTo().equals(LoginActivity.class)) {

            navigator.previousActivity(activity);

        } else {

            this.navigator.navigateTo( activity , menuModel.getTo() );

        }

        //menuListListener.onMenuClicked(menuModel);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_MENU) {


            menuPresenter.initialize();
        }

    }


    private MenusAdapter.OnItemClickListener onItemClickListener = new MenusAdapter.OnItemClickListener() {
        @Override
        public void onMenuItemClicked(MenuModel menuModel) {
            if(MenuFragment.this.menuPresenter != null && menuModel != null){
                MenuFragment.this.menuPresenter.onMenuClicked(menuModel);
            }
        }
    };

    private void setupUI(){
        this.kratosLayoutManager = new KratosLayoutManager(getActivity());
        this.rv_menus.setLayoutManager(kratosLayoutManager);

        imageLoader.displayImage(VendasUp.getUser().getPictureUrl(), img_user_picture);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context getContext() {
        return getActivity().getApplicationContext();
    }


    @Override
    public void loadTotalDailySales(Double value) {

        tv_menu_daily_sales.setText( DoubleUtil.formatToCurrency(value, true) );

    }


}

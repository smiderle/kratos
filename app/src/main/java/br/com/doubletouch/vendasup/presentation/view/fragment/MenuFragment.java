package br.com.doubletouch.vendasup.presentation.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.presentation.MenuModel;
import br.com.doubletouch.vendasup.presentation.presenter.MenuPresenter;
import br.com.doubletouch.vendasup.presentation.view.MenuView;
import br.com.doubletouch.vendasup.presentation.view.adapter.KratosLayoutManager;
import br.com.doubletouch.vendasup.presentation.view.adapter.MenusAdapter;
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

    public MenuFragment() {
        super();
    }

    @Override
    void initializePresenter() {
        menuPresenter = new MenuPresenter(this);
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
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof MenuListListener){
            this.menuListListener = (MenuListListener) activity;
        }
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
    public void goTo(MenuModel menuModel) {
        menuListListener.onMenuClicked(menuModel);
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
    }
}

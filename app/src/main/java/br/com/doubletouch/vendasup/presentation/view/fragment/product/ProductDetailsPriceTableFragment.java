package br.com.doubletouch.vendasup.presentation.view.fragment.product;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.PriceTable;
import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.data.entity.enumeration.ViewMode;
import br.com.doubletouch.vendasup.data.executor.JobExecutor;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.domain.interactor.pricetable.GetPriceTableListUseCase;
import br.com.doubletouch.vendasup.domain.interactor.pricetable.GetPriceTableListUseCaseImpl;
import br.com.doubletouch.vendasup.presentation.UIThread;
import br.com.doubletouch.vendasup.presentation.presenter.ProductDetailsPriceTablePresenter;
import br.com.doubletouch.vendasup.presentation.view.components.parallax.ScrollTabHolderFragment;
import br.com.doubletouch.vendasup.presentation.view.fragment.ProductDetailsPriceTable;
import br.com.doubletouch.vendasup.util.DoubleUtil;
import br.com.doubletouch.vendasup.util.SalesUtil;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

/**
 * Created by LADAIR on 17/03/2015.
 */
public class ProductDetailsPriceTableFragment extends ScrollTabHolderFragment implements ProductDetailsPriceTable {


    /**
     * Um listview criado para agrupar o conteudo da tab.
     * Foi utilizado um listview, pois ele possui o evento AbsListView.OnScrollListener ,
     * necessario para functionar os efeitos parallax das tabs em baixo da imagem.
     */
    private ListView lv_content;


    @InjectView(R.id.tv_price_table)
    @Optional
    TextView tv_price_table;

    @InjectView(R.id.tv_product_price)
    @Optional
    TextView tv_product_price;


    public static final String ARGS = "position";
    public static final String ARGS_PRODUCT = "product";
    public static final String ARGS_VIEW_MODE = "view_mode";
    private int position;

    private Product product;

    private Activity activity;

    private ProductDetailsPriceTablePresenter productDetailsPriceTablePresenter;

    private ViewMode viewMode;

    public static Fragment newInstance(int position, Product product, ViewMode viewMode) {
        ProductDetailsPriceTableFragment fragment = new ProductDetailsPriceTableFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARGS, position);
        bundle.putSerializable(ARGS_PRODUCT, product);
        bundle.putSerializable(ARGS_VIEW_MODE, viewMode);
        fragment.setArguments(bundle);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARGS);
        product = (Product) getArguments().getSerializable(ARGS_PRODUCT);
        viewMode = (ViewMode) getArguments().getSerializable(ARGS_VIEW_MODE);

        initializePresenter();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.activity = activity;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        lv_content = new ListView(activity);
        lv_content.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));


        if(ViewMode.INCLUSAO.equals(viewMode) || ViewMode.EDICAO.equals(viewMode)){
            lv_content.setPadding(0,(int) getResources().getDimension(R.dimen.parallax_tab_height),0,0);

        } else {
            View placeHolderView = inflater.inflate(R.layout.component_parallax_header_placeholder, lv_content, false);
            lv_content.addHeaderView(placeHolderView);
        }

        return lv_content;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lv_content.setOnScrollListener(new OnScrollListner());

        productDetailsPriceTablePresenter.initialize(product);



    }


    private void initializePresenter(){
        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();

        GetPriceTableListUseCase getPriceTableListUseCase = new GetPriceTableListUseCaseImpl(threadExecutor, postExecutionThread);

        productDetailsPriceTablePresenter = new ProductDetailsPriceTablePresenter(this, getPriceTableListUseCase);


    }

    @Override
    public void loadPriceTableList(List<PriceTable> tabelas) {

        lv_content.setAdapter(new DetailsProductAdapter(activity, product, tabelas));
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void adjustScroll(int scrollHeight) {
        if (scrollHeight == 0 && lv_content.getFirstVisiblePosition() >= 1) {
            return;
        }

        lv_content.setSelectionFromTop(1, scrollHeight);
    }

    @Override
    public void setAtributes(Object object) {

    }

    @Override
    public void tabSelected() {

    }

    private class OnScrollListner implements  AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (mScrollTabHolder != null)
                mScrollTabHolder.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount, position);
        }
    }



    /**
     * Adapter para o listview(somente com uma linha) com os dados pessoais
     */
    public class DetailsProductAdapter extends BaseAdapter {

        private Context context;
        private Product product;
        private List<PriceTable> tabelas;

        public DetailsProductAdapter(Context context, Product product, List<PriceTable> tabelas) {
            this.context = context;
            this.product = product;
            this.tabelas = tabelas;
        }

        @Override
        public int getCount() {
            return tabelas.size();
        }

        @Override
        public Object getItem(int position) {
            return tabelas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.row_product_pricetable, null);

            TextView tv_price = (TextView) view.findViewById(R.id.tv_product_price);
            TextView tv_price_table = (TextView) view.findViewById(R.id.tv_price_table);

            PriceTable priceTable = tabelas.get(position);

            Double precoFinal = SalesUtil.aplicarPercentual(product.getSalePrice(), priceTable.getPercentage(), priceTable.isIncrease());

            tv_price.setText(DoubleUtil.formatToCurrency( precoFinal, true  ));
            tv_price_table.setText(priceTable.getID() +" - " + priceTable.getDescription());

            return view;
        }

        //Desativa o evento onclick no item do listview.
        @Override
        public boolean isEnabled(int position) {
            return false;
        }
    }

}

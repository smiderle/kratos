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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.data.entity.enumeration.ViewMode;
import br.com.doubletouch.vendasup.presentation.view.adapter.DetailsBaseAdapter;
import br.com.doubletouch.vendasup.presentation.view.components.parallax.ScrollTabHolderFragment;
import br.com.doubletouch.vendasup.util.DoubleUtil;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

/**
 * Created by LADAIR on 17/03/2015.
 */
public class ProductDetailsMainFragment extends ScrollTabHolderFragment {


    /**
     * Um listview criado para agrupar o conteudo da tab.
     * Foi utilizado um listview, pois ele possui o evento AbsListView.OnScrollListener ,
     * necessario para functionar os efeitos parallax das tabs em baixo da imagem.
     */
    private ListView lv_content;

    private ScrollView sv_content;



    @InjectView(R.id.tv_product_description)
    @Optional
    TextView tv_product_description;

    @InjectView(R.id.tv_product_code)
    @Optional
    TextView tv_product_code;

    @InjectView(R.id.tv_product_barcode)
    @Optional
    TextView tv_product_barcode;

    @InjectView(R.id.tv_product_reference)
    @Optional
    TextView tv_product_reference;

    @InjectView(R.id.tv_product_package)
    @Optional
    TextView tv_product_package;

    @InjectView(R.id.tv_product_stock)
    @Optional
    TextView tv_product_stock;

    @InjectView(R.id.et_product_description)
    @Optional
    EditText et_product_description;

    @InjectView(R.id.tv_product_price)
    @Optional
    TextView tv_product_price;

    @InjectView(R.id.et_product_barcode)
    @Optional
    EditText et_product_barcode;

    @InjectView(R.id.et_product_reference)
    @Optional
    EditText et_product_reference;

    @InjectView(R.id.et_product_package)
    @Optional
    EditText et_product_package;

    @InjectView(R.id.et_product_stock)
    @Optional
    EditText et_product_stock;

    @InjectView(R.id.et_product_price)
    @Optional
    EditText et_product_price;


    public static final String ARGS = "position";
    public static final String ARGS_PRODUCT = "product";
    public static final String ARGS_VIEW_MODE = "view_mode";
    private int position;

    private Product product;


    private Activity activity;

    private ViewMode viewMode;

    public static Fragment newInstance(int position,ViewMode viewMode, Product product) {
        ProductDetailsMainFragment fragment = new ProductDetailsMainFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARGS, position);
        bundle.putSerializable(ARGS_VIEW_MODE, viewMode);
        bundle.putSerializable(ARGS_PRODUCT, product);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARGS);
        viewMode = (ViewMode) getArguments().getSerializable(ARGS_VIEW_MODE);
        product = (Product) getArguments().getSerializable(ARGS_PRODUCT);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.activity = activity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(ViewMode.EDICAO.equals(viewMode) || ViewMode.INCLUSAO.equals(viewMode)){
            sv_content = new ScrollView(activity);
            sv_content.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));
            sv_content.setPadding(0,(int) getResources().getDimension(R.dimen.parallax_tab_height),0,0);
            View viewCustomerDetails = inflater.inflate( R.layout.view_product_details_main, null );

            bind(viewCustomerDetails);

            sv_content.addView(viewCustomerDetails );
            return sv_content;
        } else {
            lv_content = new ListView(activity);
            lv_content.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));

            View placeHolderView = inflater.inflate(R.layout.component_parallax_header_placeholder, lv_content, false);

            lv_content.addHeaderView(placeHolderView);
            return lv_content;
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(ViewMode.EDICAO.equals(viewMode) || ViewMode.INCLUSAO.equals(viewMode)){

        } else {
            lv_content.setOnScrollListener(new OnScrollListner());
            lv_content.setAdapter(new DetailsPersonalAdapter(activity));
        }

    }

    private void bind(View view) {

        ButterKnife.inject(this, view);

        if(ViewMode.EDICAO.equals(viewMode) || ViewMode.INCLUSAO.equals(viewMode)){
            ((ViewSwitcher) view.findViewById(R.id.vs_product_barcode)).showNext();
            ((ViewSwitcher) view.findViewById(R.id.vs_product_description)).showNext();
            ((ViewSwitcher) view.findViewById(R.id.vs_product_package)).showNext();
            ((ViewSwitcher) view.findViewById(R.id.vs_product_price)).showNext();
            ((ViewSwitcher) view.findViewById(R.id.vs_product_reference)).showNext();
            ((ViewSwitcher) view.findViewById(R.id.vs_product_stock)).showNext();

            this.et_product_description.setText(product.getDescription());
            this.et_product_barcode.setText(product.getBarcode());
            this.et_product_reference.setText(product.getReference());
            this.et_product_package.setText(product.getPackaging());

            if(product.getStockAmount() != null){
                this.et_product_stock.setText(  product.getStockAmount().toString()  );
            }

            if(product.getSalePrice() != null){

                this.et_product_price.setText( product.getSalePrice().toString() );

            }

            if(product.getID() != null && product.getID() > 0){
                this.tv_product_code.setText( product.getID().toString());
            }



        } else {

            this.tv_product_description.setText(product.getDescription());
            this.tv_product_barcode.setText(product.getBarcode());
            this.tv_product_reference.setText(product.getReference());
            this.tv_product_package.setText(product.getPackaging());
            this.tv_product_stock.setText(DoubleUtil.formatToCurrency(product.getStockAmount(), false));
            this.tv_product_price.setText(DoubleUtil.formatToCurrency(product.getSalePrice(), true));

            if(product.getID() > 0){

                this.tv_product_code.setText(product.getID().toString());

            }

        }
    }


    @Override
    public void adjustScroll(int scrollHeight) {
        if (lv_content == null || ( scrollHeight == 0 && lv_content.getFirstVisiblePosition() >= 1 ) ) {
            return;
        }

        lv_content.setSelectionFromTop(1, scrollHeight);
    }


    @Override
    public void setAtributes(Object object) {

        product.setDescription(this.et_product_description.getText().toString());
        product.setBarcode(this.et_product_barcode.getText().toString());
        product.setReference(this.et_product_reference.getText().toString());
        product.setPackaging(this.et_product_package.getText().toString());
        product.setStockAmount( DoubleUtil.toDouble(this.et_product_stock.getText().toString() ));
        product.setSalePrice( DoubleUtil.toDouble( this.et_product_price.getText().toString() ));

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
    public class DetailsPersonalAdapter extends DetailsBaseAdapter {

        private Context context;

        public DetailsPersonalAdapter(Context context) {
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.view_product_details_main, null);

            bind(view);

            return view;
        }

        //Desativa o evento onclick no item do listview.
        @Override
        public boolean isEnabled(int position) {
            return false;
        }
    }
}

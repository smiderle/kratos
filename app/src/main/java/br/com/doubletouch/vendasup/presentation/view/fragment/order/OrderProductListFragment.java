package br.com.doubletouch.vendasup.presentation.view.fragment.order;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.entity.Order;
import br.com.doubletouch.vendasup.data.entity.OrderItem;
import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.data.entity.enumeration.ViewMode;
import br.com.doubletouch.vendasup.data.executor.JobExecutor;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.domain.interactor.product.GetProductListFilterUseCase;
import br.com.doubletouch.vendasup.domain.interactor.product.GetProductListFilterUseCaseImpl;
import br.com.doubletouch.vendasup.domain.interactor.product.GetProductListUseCase;
import br.com.doubletouch.vendasup.domain.interactor.product.GetProductListUseCaseImpl;
import br.com.doubletouch.vendasup.presentation.UIThread;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.presenter.OrderProductPresenter;
import br.com.doubletouch.vendasup.presentation.view.ProductListView;
import br.com.doubletouch.vendasup.presentation.view.components.parallax.ScrollTabHolderFragment;
import br.com.doubletouch.vendasup.presentation.view.dialog.OrderItemDrialog;
import br.com.doubletouch.vendasup.util.DoubleUtil;
import br.com.doubletouch.vendasup.util.image.ImageLoader;

/**
 * Created by LADAIR on 06/04/2015.
 */
public class OrderProductListFragment  extends ScrollTabHolderFragment implements ProductListView {

    private int tabPosition;

    public static final String ARGS = "position";
    public static final String ARGS_ORDER = "order";
    private static final String ARGUMENT_KEY_VIEW_MODE = "kratos.INTENT_EXTRA_PARAM_EDITION";

    private ListView lv_content;

    private Activity activity;

    private OrderProductPresenter orderProductPresenter;

    private Navigator navigator = new Navigator();

    private ViewMode viewMode;

    private ProductsListAdapter productsListAdapter;

    private ProductsCartListAdapter  productsCartListAdapter;

    public static OrderProductListFragment newInstance(int position, Order order, ViewMode viewMode){
        OrderProductListFragment orderProductListFragment = new OrderProductListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARGS, position);
        bundle.putSerializable(ARGS_ORDER, order);
        bundle.putSerializable(ARGUMENT_KEY_VIEW_MODE, viewMode);
        orderProductListFragment.setArguments(bundle);
        return orderProductListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabPosition = getArguments().getInt(ARGS);
        this.viewMode = (ViewMode) getArguments().getSerializable(ARGUMENT_KEY_VIEW_MODE);
        //order = (Order) getArguments().getSerializable(ARGS_ORDER);

        initializePresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        lv_content = new ListView(activity);
        lv_content.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));
        lv_content.setPadding(0,(int) getResources().getDimension(R.dimen.parallax_tab_height),0,0);



        return lv_content;

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.activity = activity;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lv_content.setOnScrollListener(new OnScrollListner());

        setHasOptionsMenu(true);
        this.loadProductList();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        //Carrega o arquivo de menu.
        inflater.inflate(R.menu.menu_search_view, menu);



        if(ViewMode.VISUALIZACAO.equals(viewMode)){
            menu.findItem(R.id.it_done).setVisible(false);
        } else {
            menu.findItem(R.id.it_done).setVisible(true);

            if( tabPosition == 1 ) {

                menu.findItem(R.id.search).setVisible(false);
                menu.findItem(R.id.search).getActionView().setVisibility(View.INVISIBLE);

            }

        }



        //Pega o Componente.
        SearchView mSearchView = (SearchView) menu.findItem(R.id.search).getActionView();
        //Define um texto de ajuda:
        mSearchView.setQueryHint("Pesquisar");

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                orderProductPresenter.onProductFilterChange(s);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.it_done:
                navigator.previousActivity(activity);
                break;
            default:
                super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }



    /**
     * Carrega os produtos
     */
    private void loadProductList(){
        this.orderProductPresenter.initialize();
    }



    private void initializePresenter(){
        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();

        GetProductListUseCase getProductListUseCase = new GetProductListUseCaseImpl(threadExecutor, postExecutionThread);
        GetProductListFilterUseCase getProductListFilterUseCase = new GetProductListFilterUseCaseImpl(threadExecutor, postExecutionThread);

        orderProductPresenter = new OrderProductPresenter(this, getProductListUseCase, getProductListFilterUseCase);


    }

    @Override
    public void renderProductList( final List<Product> products ) {

        if(ViewMode.VISUALIZACAO.equals(viewMode)){
            viewMode();
        } else {
            editMode(products);
        }




    }

    private void viewMode(){
        lv_content.setAdapter( new ProductsCartListAdapter( activity ) );

    }

    private void editMode(final List<Product> products) {


        if(tabPosition == 0){

            productsListAdapter = new ProductsListAdapter( activity, products );
            lv_content.setAdapter( productsListAdapter );

        } else {

            productsCartListAdapter = new ProductsCartListAdapter( activity );
            lv_content.setAdapter( productsCartListAdapter );

        }


        lv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                OrderItemDrialog dialog = new OrderItemDrialog();

                dialog.setContext(activity);
                dialog.addPositiveButtonListener(new OrderItemDrialog.PositiveButtonListner() {
                    @Override
                    public void pressed() {

                        if (tabPosition == 0) {
                            notifyDataSetChangeProductList();
                        } else {
                            notifyDataSetChangeProductCartList();
                        }
                    }
                });

                dialog.addNegativeButtonListener(new OrderItemDrialog.NegativeButtonListner() {
                    @Override
                    public void pressed() {

                        if (tabPosition == 0) {
                            notifyDataSetChangeProductList();
                        } else {
                            notifyDataSetChangeProductCartList();
                        }
                    }
                });

                if( tabPosition == 0 ){

                    dialog.setProduct( products.get(position), tabPosition );

                } else {

                    OrderItem orderItem = OrderFragment.order.getOrdersItens().get(position);

                    dialog.setProduct( orderItem.getProduct() , tabPosition);

                }


                dialog.show(getFragmentManager(), "Tag");

                //Toast.makeText(activity, "aaaaaaa", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void viewProduct(Product product) {

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
        return null;
    }


    private class OnScrollListner implements  AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (mScrollTabHolder != null)
                mScrollTabHolder.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount, tabPosition);
        }
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

        this.orderProductPresenter.initialize();

    }

    /**
     * Adapter para o listview(somente com uma linha) com os dados pessoais
     */
    public class ProductsListAdapter extends BaseAdapter {

        private Context context;
        private List<Product> products;
        private Order order = OrderFragment.order;
        private ImageLoader imageLoader;

        public ProductsListAdapter(Context context, List<Product> products) {
            this.context = context;
            this.products = products;
            imageLoader = new ImageLoader(context);
        }

        @Override
        public int getCount() {
            return products.size();
        }

        @Override
        public Object getItem(int position) {
            return products.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.row_order_product, null);

            TextView lblProdutoDescricao = (TextView) view.findViewById(R.id.lblProdutoDescricao);
            TextView lblProdutoEstoque = (TextView) view.findViewById(R.id.tv_stock);
            TextView lblProdutoPreco = (TextView) view.findViewById(R.id.lblProdutoPreco);
            ImageView iv_product = (ImageView) view.findViewById(R.id.iv_product);


            final TextView lbl_order_product_amount = (TextView) view.findViewById(R.id.lbl_order_product_amount);
            //final TextView lbl_order_product_amount_title = (TextView) view.findViewById(R.id.lbl_order_product_amount_title);

            final Product product = products.get(position);

            imageLoader.displayImage(product.getPictureUrl(), iv_product);

            if(order != null && order.getOrdersItens() != null && !order.getOrdersItens().isEmpty() ) {

                OrderItem orderItem = order.containsProduct(product);


                if(orderItem != null ){
                    changeValue(lbl_order_product_amount, orderItem.getQuantity());
                    lblProdutoPreco.setText( DoubleUtil.formatToCurrency( orderItem.getSalePrice(), true) );

                } else {
                    lblProdutoPreco.setText( DoubleUtil.formatToCurrency( product.getSalePrice(), true) );
                }
            } else {
                lblProdutoPreco.setText( DoubleUtil.formatToCurrency( product.getSalePrice(), true) );
            }


            lblProdutoDescricao.setText(product.getDescription());
            lblProdutoEstoque.setText( String.valueOf(product.getStockAmount()));


            ImageButton btnAdd = (ImageButton) view.findViewById(R.id.btn_order_product_add);
            ImageButton btnRemove = (ImageButton) view.findViewById(R.id.btn_order_product_remove);

            btnRemove.setColorFilter(getResources().getColor(R.color.primary_color));
            btnAdd.setColorFilter(getResources().getColor(R.color.primary_color));
            //btnAdd.setBackgroundColor(Color.TRANSPARENT);
            //btnRemove.setBackgroundColor(Color.TRANSPARENT);

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(order != null && order.getOrdersItens() != null  ) {

                        OrderItem orderItem = order.containsProduct(product);

                        if(orderItem != null){

                            orderItem.addQuantity(1.0);
                            changeValue(lbl_order_product_amount, orderItem.getQuantity());

                        } else {
                            OrderItem orderCriado = createOrderItem(product);
                            order.getOrdersItens().add(orderCriado);
                            changeValue(lbl_order_product_amount, orderCriado.getQuantity());
                        }
                    }


                }
            });

            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(order != null && order.getOrdersItens() != null ) {

                        OrderItem orderItem = order.containsProduct(product);

                        if(orderItem != null){
                            orderItem.removeQuantity(1.0);
                            changeValue(lbl_order_product_amount, orderItem.getQuantity());

                            if(orderItem.getQuantity() <= 0){
                                order.getOrdersItens().remove(orderItem);
                            }


                        }
                    }

                }
            });



            return view;
        }




        public void changeValue(TextView view, Double value) {
            if(value <= 0) {

                view.setVisibility(View.INVISIBLE);
                view.setText( DoubleUtil.formatToCurrency( 0.0, false ));

            } else {
                view.setVisibility(View.VISIBLE);
                view.setText( DoubleUtil.formatToCurrency( value, false ));
            }


        }



    }

    private OrderItem createOrderItem(Product product){

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setQuantity(1.0);
        orderItem.setSalePrice(product.getSalePrice());
        orderItem.setBranchID( VendasUp.getBranchOffice().getBranchOfficeID() );
        orderItem.setOrganizationID( VendasUp.getBranchOffice().getOrganization().getOrganizationID() );
        orderItem.setSequence(OrderFragment.order.getOrdersItens().size() + 1);

        return  orderItem;
    }




    /**
     * Adapter para o listview(somente com uma linha) com os dados pessoais
     */
    public class ProductsCartListAdapter extends BaseAdapter {

        private Context context;
        private Order order = OrderFragment.order;
        private ImageLoader imageLoader;

        public ProductsCartListAdapter (Context context) {
            this.context = context;
            imageLoader = new ImageLoader(context);
        }

        @Override
        public int getCount() {
            return order.getOrdersItens().size();
        }

        @Override
        public Object getItem(int position) {
            return order.getOrdersItens().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.row_order_product, null);

            TextView lblProdutoDescricao = (TextView) view.findViewById(R.id.lblProdutoDescricao);
            TextView lblProdutoPreco = (TextView) view.findViewById(R.id.lblProdutoPreco);
            TextView tv_stock_title = (TextView) view.findViewById(R.id.tv_stock_title);
            TextView tv_stock = (TextView) view.findViewById(R.id.tv_stock);


            ImageView iv_product = (ImageView) view.findViewById(R.id.iv_product);

            tv_stock_title.setVisibility(View.INVISIBLE);
            tv_stock.setVisibility(View.INVISIBLE);


            final TextView lbl_order_product_amount = (TextView) view.findViewById(R.id.lbl_order_product_amount);
            //final TextView lbl_order_product_amount_title = (TextView) view.findViewById(R.id.lbl_order_product_amount_title);

            final OrderItem orderItem = order.getOrdersItens().get(position);
            final Product product = orderItem.getProduct();

            imageLoader.displayImage(product.getPictureUrl(), iv_product);

            if(order != null && order.getOrdersItens() != null ) {



                if(orderItem != null && orderItem.getQuantity() > 0){
                    changeValue(lbl_order_product_amount,  orderItem.getQuantity());

                }
            }

            ImageButton btnAdd = (ImageButton) view.findViewById(R.id.btn_order_product_add);
            ImageButton btnRemove = (ImageButton) view.findViewById(R.id.btn_order_product_remove);



            if(ViewMode.VISUALIZACAO.equals(viewMode)){
                btnAdd.setVisibility(View.INVISIBLE);
                btnRemove.setVisibility(View.INVISIBLE);
            } else {


                btnRemove.setColorFilter(getResources().getColor(R.color.primary_color));
                btnAdd.setColorFilter(getResources().getColor(R.color.primary_color));
                btnAdd.setBackgroundColor(Color.TRANSPARENT);
                btnRemove.setBackgroundColor(Color.TRANSPARENT);

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if(order != null && order.getOrdersItens() != null ) {

                            OrderItem orderItem = order.containsProduct(product);

                            if(orderItem != null){
                                orderItem.addQuantity(1.0);
                                changeValue(lbl_order_product_amount,  orderItem.getQuantity());

                            } else {
                                OrderItem orderCriado = createOrderItem(product);
                                order.getOrdersItens().add(orderCriado);
                                changeValue(lbl_order_product_amount, orderCriado.getQuantity());
                            }
                        }


                    }
                });

                btnRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(order != null && order.getOrdersItens() != null ) {

                            OrderItem orderItem = order.containsProduct(product);

                            if(orderItem != null){
                                orderItem.removeQuantity(1.0);
                                changeValue(lbl_order_product_amount, orderItem.getQuantity());

                                if(orderItem.getQuantity() <= 0){
                                    order.getOrdersItens().remove(orderItem);
                                }

                            }
                        }

                    }
                });

            }



            lblProdutoDescricao.setText( orderItem.getProduct().getDescription());
            lblProdutoPreco.setText( DoubleUtil.formatToCurrency( orderItem.getSalePrice(), true) );
            //lblProdutoEstoque.setText( String.valueOf( orderItem.getQuantity() ));

            return view;
        }




        public void changeValue(TextView view, Double value) {
            if(value <= 0) {

                view.setVisibility(View.INVISIBLE);
                view.setText( DoubleUtil.formatToCurrency( 0.0, false ));

            } else {
                view.setVisibility(View.VISIBLE);
                view.setText( DoubleUtil.formatToCurrency( value, false ));
            }


        }

        /*private OrderItem createOrderItem(Product product){

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(1.0);

            return  orderItem;
        }*/


    }




    public void notifyDataSetChangeProductList(){
        this.productsListAdapter.notifyDataSetChanged();
    }

    public void notifyDataSetChangeProductCartList(){
        this.productsCartListAdapter.notifyDataSetChanged();
    }



}

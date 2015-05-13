package br.com.doubletouch.vendasup.presentation.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.regex.Pattern;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.entity.OrderItem;
import br.com.doubletouch.vendasup.data.entity.PriceTable;
import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.data.executor.JobExecutor;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.domain.interactor.pricetable.GetPriceTableListUseCase;
import br.com.doubletouch.vendasup.domain.interactor.pricetable.GetPriceTableListUseCaseImpl;
import br.com.doubletouch.vendasup.presentation.UIThread;
import br.com.doubletouch.vendasup.presentation.presenter.OrderItemDialogPresenter;
import br.com.doubletouch.vendasup.presentation.view.fragment.order.OrderFragment;
import br.com.doubletouch.vendasup.util.DoubleUtil;
import br.com.doubletouch.vendasup.util.SalesUtil;
import br.com.doubletouch.vendasup.util.StringUtil;

/**
 * Created by LADAIR on 09/04/2015.
 */
public class OrderItemDrialog extends DialogFragment {

    AlertDialog alertDialog;

    private Activity context;

    private OrderItem orderItem;

    private Product product;

    private int tabPosition;

    private TextView tv_order_product_description;
    private TextView tv_order_product_stock;
    private TextView tv_order_product_price;
    private EditText ed_order_quantity;
    private EditText ed_order_price_sales;
    private Spinner sp_order_price_table;
    private ImageButton btnRemove;
    private ImageButton btnAdd;


    private TextView tv_order_discount_title;
    private TextView et_order_discount;
    private TextView et_order_discount_reais;
    private TextView tv_order_discount_reais_title;


    private PriceTable priceTableSelected = null;

    private List<PriceTable> tabelas = null;

    private OrderItemDialogPresenter orderItemDialogPresenter;

    public OrderItemDrialog() {
    }

    public void setContext(Activity context) {
        this.context = context;
    }


    public void setProduct( Product product, int tabPosition ){
        this.product = product;
        this.tabPosition = tabPosition;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflate  = context.getLayoutInflater();
        View view = inflate.inflate(R.layout.dialog_order_item,null);

        btnRemove = (ImageButton) view.findViewById(R.id.btn_order_dialog_remove);
        btnAdd = (ImageButton) view.findViewById(R.id.btn_order_dialog_add);
        tv_order_product_description = (TextView)  view.findViewById(R.id.tv_order_product);
        tv_order_product_stock = (TextView)  view.findViewById(R.id.tv_order_stock);
        tv_order_product_price = (TextView)  view.findViewById(R.id.tv_order_product_price);
        ed_order_quantity = (EditText)  view.findViewById(R.id.ed_order_quantity);
        ed_order_price_sales = (EditText)  view.findViewById(R.id.ed_order_price_sales);
        sp_order_price_table = (Spinner) view.findViewById(R.id.sp_order_price_table);
        tv_order_discount_title = (TextView) view.findViewById(R.id.tv_order_discount_title);
        et_order_discount = (TextView) view.findViewById(R.id.et_order_discount);
        et_order_discount_reais = (TextView) view.findViewById(R.id.et_order_discount_reais);
        tv_order_discount_reais_title = (TextView) view.findViewById(R.id.tv_order_discount_reais_title);

        //btnRemove.setBackgroundColor(Color.TRANSPARENT);
        //btnAdd.setBackgroundColor(Color.TRANSPARENT);
        btnAdd.setColorFilter(getResources().getColor(R.color.primary_color));
        btnRemove.setColorFilter(getResources().getColor(R.color.primary_color));

        orderItem = getOrCreate(product);

        tv_order_product_description.setText(orderItem.getProduct().getDescription());
        tv_order_product_stock.setText(DoubleUtil.formatToCurrency(orderItem.getProduct().getStockAmount(), false));
        tv_order_product_price.setText(DoubleUtil.formatToCurrency(orderItem.getSalePrice(), true));
        ed_order_quantity.setText(String.valueOf( orderItem.getQuantity() ));
        ed_order_price_sales.setText(String.valueOf( orderItem.getSalePrice() ));


        addListners();


        String labelButton = tabPosition == 0 ? "Adicionar ao carrinho" : "Confirmar Edição";

        AlertDialog.Builder builder = new AlertDialog.Builder( context )
                .setView(view)
                .setTitle("Editar/Remover Produtos do Carrinho");


        builder.setPositiveButton(labelButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                orderItem.setSalePrice( getPrice() );
                orderItem.setQuantity(getQuantity());
                orderItem.setPriceTable(priceTableSelected);
                addSeNaoExiste();


            }
        });

        if( tabPosition == 1 ) {

            builder.setNegativeButton("Remover do carrinho", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    remove(orderItem);

                }
            });

        }

        alertDialog = builder.create();

        initializePresenter();


        return alertDialog;

    }

    private void addListners(){

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Double quantidade = getQuantity();
                quantidade -= 1;

                if(quantidade < 0 ){
                    quantidade = 0.00;
                }
                setQuantity(quantidade);

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Double quantidade = getQuantity();
                quantidade += 1;

                setQuantity(quantidade);

            }
        });

        ed_order_quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {

                String regex = StringUtil.DECIMAL_REGEX;

                String text = s.toString();
                int length = text.length();

                if( length > 0 ) {
                    if( !Pattern.matches( regex, text ) ) {
                        s.delete(length - 1, length);
                    }

                }
            }
        });

        ed_order_price_sales.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                atualizaReajuste(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

                String regex = StringUtil.DECIMAL_REGEX;

                String text = s.toString();
                int length = text.length();

                if( length > 0 ) {
                    if( !Pattern.matches( regex, text ) ) {
                        s.delete(length - 1, length);
                    }

                }



            }
        });
    }

    private Double getPrice() {

        return DoubleUtil.toDouble(ed_order_price_sales.getText().toString());

    }

    private Double getQuantity() {

        return DoubleUtil.toDouble( ed_order_quantity .getText().toString() );

    }

    private void setQuantity(Double quantity) {

        ed_order_quantity.setText( DoubleUtil.formatToCurrency( quantity, false , "." ) ) ;

    }

    private OrderItem getOrCreate(Product product){

        OrderItem orderItem = getOrderItem(product);

        if(orderItem == null){

            orderItem = new OrderItem();
            orderItem.setSalePrice(product.getSalePrice());
            orderItem.setProduct(product);
            orderItem.setQuantity(1.0);
            orderItem.setBranchID( VendasUp.getBranchOffice().getBranchOfficeID() );
            orderItem.setOrganizationID( VendasUp.getBranchOffice().getOrganization().getOrganizationID() );
            orderItem.setSequence(OrderFragment.order.getOrdersItens().size() + 1);



        }

        return orderItem;

    }


    private OrderItem getOrderItem(Product product) {

        OrderItem retorno = null;
        List<OrderItem> ordersItens = OrderFragment.order.getOrdersItens();

        for(OrderItem orderItem : ordersItens) {

            if(orderItem.getProduct().equals(product)){

                retorno = orderItem;
                break;

            }
        }

        return retorno;

    }

    private void addSeNaoExiste(){

        if( getOrderItem( orderItem.getProduct()) == null ){

            OrderFragment.order.getOrdersItens().add( orderItem );

        }

    }

    private void remove( OrderItem orderItem ){

        OrderFragment.order.getOrdersItens().remove(orderItem);
    }


    public void loadPriceTableList(final List<PriceTable> tabelas) {

        this.tabelas = tabelas;

        ArrayAdapter<PriceTable> adapter = new ArrayAdapter<PriceTable>( context, android.R.layout.simple_spinner_dropdown_item, tabelas );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_order_price_table.setAdapter(adapter);

        if( orderItem.getPriceTable() != null ) {


            PriceTable priceTable1 = getPriceTable(orderItem.getPriceTable().getID());
            sp_order_price_table.setSelection( tabelas.indexOf( priceTable1 ) );

        } else {

            priceTableSelected = getDefaultPriceTable();
            orderItem.setPriceTable(priceTableSelected);
            sp_order_price_table.setSelection( tabelas.indexOf( priceTableSelected ) );


        }

        sp_order_price_table.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                priceTableSelected = tabelas.get(position);

                Double precoFinal = getPrecoComTabela(priceTableSelected, product);

                ed_order_price_sales.setText( DoubleUtil.formatToCurrency(precoFinal, false, ".") );
                tv_order_product_price.setText( DoubleUtil.formatToCurrency(precoFinal, false) );

                if(tabPosition == 1){

                    ed_order_price_sales.setText( DoubleUtil.formatToCurrency(orderItem.getSalePrice(), false, ".")  );
                }

                OrderFragment.lastPriceTableSelected = priceTableSelected;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    private void atualizaReajuste(String valor){

        double valorPretendido = DoubleUtil.toDouble(valor);

        double percentual = calculaReajuste(priceTableSelected, orderItem.getProduct().getSalePrice(), valorPretendido);

        Double precoComTabela = getPrecoComTabela(priceTableSelected, orderItem.getProduct());

        if( precoComTabela > valorPretendido ){
            //DESCONTO

            et_order_discount.setText( String.valueOf( percentual ));
            tv_order_discount_title.setText(  getResources().getString( R.string.tv_order_discount )  );

            double descontoReais = precoComTabela - valorPretendido;

            tv_order_discount_reais_title.setText(getResources().getString( R.string.tv_order_discount_money ));
            et_order_discount_reais.setText( DoubleUtil.formatToCurrency(descontoReais, false) );


        } else {
            //ACRESCIMO

            et_order_discount.setText(String.valueOf( percentual ) );
            tv_order_discount_title.setText(  getResources().getString( R.string.tv_order_increase ));


            double acrescimoReais = valorPretendido - precoComTabela;
            tv_order_discount_reais_title.setText(getResources().getString( R.string.tv_order_increase_money ));
            et_order_discount_reais.setText( DoubleUtil.formatToCurrency(acrescimoReais, false) );

        }


    }


    private double calculaReajuste( PriceTable priceTable, Double valorProduto, Double valorPretendido){


        Double precoTabela = getPrecoComTabela(priceTableSelected, orderItem.getProduct());


        return SalesUtil.getPercentual( precoTabela,  valorPretendido);

    }


    private Double getPrecoComTabela(PriceTable priceTable, Product product){

        return SalesUtil.aplicarPercentual(product.getSalePrice(), priceTable.getPercentage(), priceTable.isIncrease());

    }

    private void initializePresenter(){

        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();
        GetPriceTableListUseCase getPriceTableListUseCase = new GetPriceTableListUseCaseImpl(threadExecutor, postExecutionThread);


        orderItemDialogPresenter = new OrderItemDialogPresenter(this, getPriceTableListUseCase);
        orderItemDialogPresenter.initialize();

    }


    private PriceTable getPriceTable( Integer priceTableId ) {

        PriceTable retorno = null;

        if( tabelas != null ) {

            for (int i = 0 ; i <  tabelas.size(); i++) {

                PriceTable priceTable = tabelas.get(i);

                if( priceTable.getID().equals(priceTableId) ) {

                    retorno = priceTable;
                    break;

                }

            }

        }

        return  retorno;

    }


    private PriceTable getDefaultPriceTable() {

        PriceTable defaultPriceTable = null;

        if(OrderFragment.lastPriceTableSelected != null) {

            defaultPriceTable = getPriceTable(OrderFragment.lastPriceTableSelected.getID());;

        } else if( OrderFragment.order.getCustomer() != null ){

            Integer priceTableCustomer =  OrderFragment.order.getCustomer().getPriceTable();

            if( priceTableCustomer != null ){

                defaultPriceTable = getPriceTable(priceTableCustomer);;

            }

        } else {

            defaultPriceTable =   tabelas.get( 0 );

        }

        return  defaultPriceTable;
    }

}


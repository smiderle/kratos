package br.com.doubletouch.vendasup.presentation.view.fragment.customer;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.presentation.view.adapter.CustomerDetailsBaseAdapter;
import br.com.doubletouch.vendasup.presentation.view.components.parallax.ScrollTabHolderFragment;
import br.com.doubletouch.vendasup.util.DoubleUtil;

/**
 * Created by LADAIR on 24/02/2015.
 */
public class CustomerDetailsFinancialFragment extends ScrollTabHolderFragment {

    /**
     * Um listview criado para agrupar o conteudo da tab.
     * Foi utilizado um listview, pois ele possui o evento AbsListView.OnScrollListener ,
     * necessario para functionar os efeitos parallax das tabs em baixo da imagem.
     */
    private ListView lv_content;

    public static final String ARGS = "position";
    public static final String ARGS_CUSTOMER = "customer";
    private int position;

    private Customer customer;

    public static Fragment newInstance(int position, Customer customer) {
        CustomerDetailsFinancialFragment fragment = new CustomerDetailsFinancialFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARGS, position);
        bundle.putSerializable(ARGS_CUSTOMER, customer);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARGS);
        customer = (Customer) getArguments().getSerializable(ARGS_CUSTOMER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        lv_content = new ListView(getActivity());
        lv_content.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));

        View placeHolderView = inflater.inflate(R.layout.component_parallax_header_placeholder, lv_content, false);
        lv_content.addHeaderView(placeHolderView);
        return lv_content;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lv_content.setOnScrollListener(new OnScrollListner());
        lv_content.setAdapter(new CustomerDetailsPersonalAdapter(getActivity()));
    }

    @Override
    public void adjustScroll(int scrollHeight) {
        if (scrollHeight == 0 && lv_content.getFirstVisiblePosition() >= 1) {
            return;
        }

        lv_content.setSelectionFromTop(1, scrollHeight);
    }

    @Override
    public void setAtributes(Customer customer) {

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
    public class CustomerDetailsPersonalAdapter extends CustomerDetailsBaseAdapter {

        private Context context;

        public CustomerDetailsPersonalAdapter(Context context) {
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.view_customer_details_financial, null);

            /*((TextView)view.findViewById(R.id.tv_customer_payment)).setText(customer.getFormPayment());
            ((TextView)view.findViewById(R.id.tv_customer_credit_limit)).setText(DoubleUtil.formatToCurrency( customer.getCreditLimit(), true ) );
            ((TextView)view.findViewById(R.id.tv_customer_price_table)).setText(customer.getPriceTable());*/
            //((TextView)view.findViewById(R.id.tv_customer_installment)).setText(customer.getIPriceTable());


            return view;
        }

        //Desativa o evento onclick no item do listview.
        @Override
        public boolean isEnabled(int position) {
            return false;
        }
    }

}

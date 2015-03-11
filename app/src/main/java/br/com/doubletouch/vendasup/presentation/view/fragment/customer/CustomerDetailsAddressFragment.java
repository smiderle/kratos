package br.com.doubletouch.vendasup.presentation.view.fragment.customer;

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
import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.presentation.view.adapter.CustomerDetailsBaseAdapter;
import br.com.doubletouch.vendasup.presentation.view.components.parallax.ScrollTabHolderFragment;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

/**
 * Created by LADAIR on 24/02/2015.
 */
public class CustomerDetailsAddressFragment extends ScrollTabHolderFragment {

    /**
     * Um listview criado para agrupar o conteudo da tab.
     * Foi utilizado um listview, pois ele possui o evento AbsListView.OnScrollListener ,
     * necessario para functionar os efeitos parallax das tabs em baixo da imagem.
     */
    private ListView lv_content;
    private ScrollView sv_content;

    public static final String ARGS = "position";
    public static final String ARGS_CUSTOMER = "customer";
    public static final String ARGS_STATE_EDITION = "state_edition";
    private int position;
    private boolean isEdition;

    private Customer customer;



    @InjectView(R.id.et_customer_postalcode)
    @Optional
    EditText et_customer_postalcode;

    @InjectView(R.id.et_customer_city)
    @Optional
    EditText et_customer_city;

    @InjectView(R.id.et_customer_district)
    @Optional
    EditText et_customer_district;

    @InjectView(R.id.et_customer_street)
    @Optional
    EditText et_customer_street;

    @InjectView(R.id.et_customer_number)
    @Optional
    EditText et_customer_number;

    @InjectView(R.id.tv_customer_postalcode)
    @Optional
    TextView tv_customer_postalcode;

    @InjectView(R.id.tv_customer_city)
    @Optional
    TextView tv_customer_city;

    @InjectView(R.id.tv_customer_district)
    @Optional
    TextView tv_customer_district;

    @InjectView(R.id.tv_customer_street)
    @Optional
    TextView tv_customer_street;

    @InjectView(R.id.tv_customer_number)
    @Optional
    TextView tv_customer_number;

    public static Fragment newInstance(int position,boolean isEdition, Customer customer) {
        CustomerDetailsAddressFragment fragment = new CustomerDetailsAddressFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARGS, position);
        bundle.putBoolean(ARGS_STATE_EDITION, isEdition);
        bundle.putSerializable(ARGS_CUSTOMER, customer);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARGS);
        isEdition = getArguments().getBoolean(ARGS_STATE_EDITION);
        customer = (Customer) getArguments().getSerializable(ARGS_CUSTOMER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(isEdition) {
            sv_content = new ScrollView(getActivity());
            sv_content.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));
            sv_content.setPadding(0,(int) getResources().getDimension(R.dimen.parallax_tab_height),0,0);
            View viewCustomerDetails = inflater.inflate(R.layout.view_customer_details_address, null);

            bind(viewCustomerDetails);

            sv_content.addView(viewCustomerDetails);
            return sv_content;
        } else {
            lv_content = new ListView(getActivity());
            lv_content.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));

            View placeHolderView = inflater.inflate(R.layout.component_parallax_header_placeholder, lv_content, false);
            lv_content.addHeaderView(placeHolderView);
            return lv_content;
        }


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(!isEdition){
            lv_content.setOnScrollListener(new OnScrollListner());
            lv_content.setAdapter(new CustomerDetailsPersonalAdapter(getActivity()));
        }

    }

    @Override
    public void adjustScroll(int scrollHeight) {
        if (lv_content == null ||  (scrollHeight == 0 && lv_content.getFirstVisiblePosition() >= 1) ) {
            return;
        }

        lv_content.setSelectionFromTop(1, scrollHeight);
    }

    @Override
    public void setAtributes(Customer customer) {
        customer.setDistrict( et_customer_district.getText().toString());
        customer.setNumber(et_customer_number.getText().toString());
        customer.setPostalCode(et_customer_postalcode.getText().toString());
        customer.setStreet(et_customer_street.getText().toString());
    }


    private void bind(View view) {

        ButterKnife.inject(this, view);

        if (isEdition) {
            ((ViewSwitcher) view.findViewById(R.id.vs_customer_postalcode)).showNext();
            ((ViewSwitcher) view.findViewById(R.id.vs_customer_city)).showNext();
            ((ViewSwitcher) view.findViewById(R.id.vs_customer_street)).showNext();
            ((ViewSwitcher) view.findViewById(R.id.vs_customer_district)).showNext();
            ((ViewSwitcher) view.findViewById(R.id.vs_customer_number)).showNext();

            et_customer_district.setText(customer.getDistrict());
            et_customer_number.setText(customer.getNumber());
            et_customer_postalcode.setText(customer.getPostalCode());
            et_customer_street.setText(customer.getStreet());
        } else {
            tv_customer_district.setText(customer.getDistrict());
            tv_customer_number.setText(customer.getNumber());
            tv_customer_postalcode.setText(customer.getPostalCode());
            tv_customer_street.setText(customer.getStreet());
        }
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
            View view = inflater.inflate(R.layout.view_customer_details_address, null);

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

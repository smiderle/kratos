package br.com.doubletouch.vendasup.presentation.view.fragment.customer;

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
import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.enumeration.ViewMode;
import br.com.doubletouch.vendasup.presentation.view.adapter.DetailsBaseAdapter;
import br.com.doubletouch.vendasup.presentation.view.components.parallax.ScrollTabHolderFragment;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

/**
 * Created by LADAIR on 24/02/2015.
 */
public class CustomerDetailsContactFragment extends ScrollTabHolderFragment {

    /**
     * Um listview criado para agrupar o conteudo da tab.
     * Foi utilizado um listview, pois ele possui o evento AbsListView.OnScrollListener ,
     * necessario para functionar os efeitos parallax das tabs em baixo da imagem.
     */
    private ListView lv_content;
    private ScrollView sv_content;

    @InjectView(R.id.et_customer_phone_home)
    @Optional
    EditText et_customer_phone_home;

    @InjectView(R.id.et_customer_phone_cell)
    @Optional
    EditText et_customer_phone_cell;

    @InjectView(R.id.et_customer_phone_commercial)
    @Optional
    EditText et_customer_phone_commercial;

    @InjectView(R.id.et_customer_email)
    @Optional
    EditText et_customer_email;

    @InjectView(R.id.tv_customer_phone_home)
    @Optional
    TextView tv_customer_phone_home;

    @InjectView(R.id.tv_customer_phone_cell)
    @Optional
    TextView tv_customer_phone_cell;

    @InjectView(R.id.tv_customer_phone_commercial)
    @Optional
    TextView tv_customer_phone_commercial;

    @InjectView(R.id.tv_customer_email)
    @Optional
    TextView tv_customer_email;

    private Activity activity;

    public static final String ARGS = "position";
    public static final String ARGS_CUSTOMER = "customer";
    public static final String ARGS_VIEW_MODE = "state_edition";
    private int position;

    private Customer customer;

    private ViewMode viewMode;

    public static Fragment newInstance(int position,ViewMode viewMode, Customer customer) {
        CustomerDetailsContactFragment fragment = new CustomerDetailsContactFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARGS, position);
        bundle.putSerializable(ARGS_VIEW_MODE, viewMode);
        bundle.putSerializable(ARGS_CUSTOMER, customer);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARGS);
        viewMode = (ViewMode) getArguments().getSerializable(ARGS_VIEW_MODE);
        customer = (Customer) getArguments().getSerializable(ARGS_CUSTOMER);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if( ViewMode.EDICAO.equals(viewMode) || ViewMode.INCLUSAO.equals(viewMode)){
            sv_content = new ScrollView(activity);
            sv_content.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));
            sv_content.setPadding(0,(int) getResources().getDimension(R.dimen.parallax_tab_height),0,0);

            View viewCustomerDetails = inflater.inflate( R.layout.view_customer_details_contact, null );

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

    private void bind(View view) {

        ButterKnife.inject(this, view);

        if( ViewMode.EDICAO.equals(viewMode) || ViewMode.INCLUSAO.equals(viewMode)){
            ((ViewSwitcher) view.findViewById(R.id.vs_customer_phone_home)).showNext();
            ((ViewSwitcher) view.findViewById(R.id.vs_customer_phone_comercial)).showNext();
            ((ViewSwitcher) view.findViewById(R.id.vs_customer_phone_cell)).showNext();
            ((ViewSwitcher) view.findViewById(R.id.vs_customer_email)).showNext();

            et_customer_email.setText(customer.getEmail());
            et_customer_phone_cell.setText(customer.getCellPhone());
            et_customer_phone_commercial.setText(customer.getCommercialPhone());
            et_customer_phone_home.setText(customer.getHomePhone());
            et_customer_email.setText(customer.getEmail());
        } else {
            tv_customer_email.setText(customer.getEmail());
            tv_customer_phone_cell.setText(customer.getCellPhone());
            tv_customer_phone_commercial.setText(customer.getCommercialPhone());
            tv_customer_phone_home.setText(customer.getHomePhone());
            tv_customer_email.setText(customer.getEmail());
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if( ViewMode.VISUALIZACAO.equals(viewMode)){
            lv_content.setOnScrollListener(new OnScrollListner());
            lv_content.setAdapter(new DetailsPersonalAdapter(activity));
        }




    }

    @Override
    public void adjustScroll(int scrollHeight) {
        if (lv_content == null || (scrollHeight == 0 && lv_content.getFirstVisiblePosition() >= 1)) {
            return;
        }

        lv_content.setSelectionFromTop(1, scrollHeight);
    }

    @Override
    public void setAtributes(Object object) {
        Customer customer = (Customer) object;
        customer.setEmail(et_customer_email.getText().toString());
        customer.setCellPhone(et_customer_phone_cell.getText().toString());
        customer.setCommercialPhone(et_customer_phone_commercial.getText().toString());
        customer.setHomePhone(et_customer_phone_home.getText().toString());
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
            View view = inflater.inflate(R.layout.view_customer_details_contact, null);

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

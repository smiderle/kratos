package br.com.doubletouch.vendasup.presentation.view.fragment.customer;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.enumeration.PersonType;
import br.com.doubletouch.vendasup.presentation.view.adapter.CustomerDetailsBaseAdapter;
import br.com.doubletouch.vendasup.presentation.view.components.parallax.ScrollTabHolderFragment;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

/**
 * Created by LADAIR on 21/02/2015.
 */
public class CustomerDetailsPersonalFragment extends ScrollTabHolderFragment  {

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




    @InjectView(R.id.et_customer_name)
    @Optional
    EditText et_customer_name;

    @InjectView(R.id.et_customer_cpfcnpj)
    @Optional
    EditText et_customer_cpfcnpj;

    @InjectView(R.id.et_customer_inscricao)
    @Optional
    EditText et_customer_inscricao;

    @InjectView(R.id.et_customer_nickname)
    @Optional
    EditText et_customer_nickname;

    @InjectView(R.id.et_customer_observation)
    @Optional
    EditText et_customer_observation;

    @InjectView(R.id.sp_customer_persontype)
    @Optional
    Spinner sp_customer_type;

    @InjectView(R.id.tv_customer_name_title)
    @Optional
    TextView tv_customer_name_title;

    @InjectView(R.id.tv_customer_cpfcnpj_title)
    @Optional
    TextView tv_customer_cpfcnpj_title;

    @InjectView(R.id.tv_customer_inscricao_title)
    @Optional
    TextView tv_customer_inscricao_title;

    @InjectView(R.id.tv_customer_name)
    @Optional
    TextView tv_customer_name;

    @InjectView(R.id.tv_customer_nickname)
    @Optional
    TextView tv_customer_nickname;

    @InjectView(R.id.tv_customer_persontype_)
    @Optional
    TextView tv_customer_persontype_;

    @InjectView(R.id.tv_customer_cpfcnpj)
    @Optional
    TextView tv_customer_cpfcnpj;

    @InjectView(R.id.tv_customer_inscricao)
    @Optional
    TextView tv_customer_inscricao;

    @InjectView(R.id.tv_customer_observation)
    @Optional
    TextView tv_customer_observation;

    public static Fragment newInstance(int position,boolean isEdition, Customer customer) {
        CustomerDetailsPersonalFragment fragment = new CustomerDetailsPersonalFragment();
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

        if(isEdition){
            sv_content = new ScrollView(getActivity());
            sv_content.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));
            sv_content.setPadding(0,(int) getResources().getDimension(R.dimen.parallax_tab_height),0,0);
            View viewCustomerDetails = inflater.inflate( R.layout.view_customer_details_personal, null );

            bind(viewCustomerDetails);

            sv_content.addView(viewCustomerDetails );
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

        if(isEdition){

        } else {
            lv_content.setOnScrollListener(new OnScrollListner());
            lv_content.setAdapter(new CustomerDetailsPersonalAdapter(getActivity()));
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
    public void setAtributes(Customer customer) {
        customer.setName(et_customer_name.getText().toString());
        customer.setCpfCnpj(et_customer_cpfcnpj.getText().toString());
        customer.setIncricao(et_customer_inscricao.getText().toString());
        customer.setNickName(et_customer_nickname.getText().toString());
        customer.setObservation(et_customer_observation.getText().toString());
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

    private void bind(View view) {

        ButterKnife.inject(this, view);

        if(isEdition){
            ((ViewSwitcher) view.findViewById(R.id.vs_customer_name)).showNext();
            ((ViewSwitcher) view.findViewById(R.id.vs_customer_birthday)).showNext();
            ((ViewSwitcher) view.findViewById(R.id.vs_customer_cpfcnpj)).showNext();
            ((ViewSwitcher) view.findViewById(R.id.vs_customer_inscricao)).showNext();
            ((ViewSwitcher) view.findViewById(R.id.vs_customer_nickname)).showNext();
            ((ViewSwitcher) view.findViewById(R.id.vs_customer_observation)).showNext();
            ((ViewSwitcher) view.findViewById(R.id.vs_customer_persontype)).showNext();

            et_customer_name.setText(customer.getName());
            et_customer_cpfcnpj.setText(customer.getCpfCnpj());
            et_customer_inscricao.setText(customer.getIncricao());
            et_customer_nickname.setText(customer.getNickName());
            et_customer_observation.setText(customer.getObservation());
            et_customer_name.setText(customer.getName());

            sp_customer_type.setOnItemSelectedListener(new PersonTypeSelectedItem());

        } else {
            tv_customer_name.setText(customer.getName());
            tv_customer_nickname.setText(customer.getNickName());
            tv_customer_persontype_.setText( PersonType.get(customer.getPersonType()).toString() );
            tv_customer_cpfcnpj.setText(customer.getCpfCnpj());
            tv_customer_inscricao.setText(customer.getIncricao());
            tv_customer_observation.setText(customer.getObservation());

            if(PersonType.JURIDICA.ordinal() == customer.getPersonType()){
                tv_customer_cpfcnpj_title.setText(getResources().getString(R.string.tv_customer_cnpj));
                tv_customer_inscricao_title.setText(getResources().getString(R.string.tv_customer_inscricao));
            } else {
                tv_customer_cpfcnpj_title.setText(getResources().getString(R.string.tv_customer_cpf));
                tv_customer_inscricao_title.setText(getResources().getString(R.string.tv_customer_rg));
            }
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
            View view = inflater.inflate(R.layout.view_customer_details_personal, null);

            bind(view);

            return view;
        }

        //Desativa o evento onclick no item do listview.
        @Override
        public boolean isEnabled(int position) {
            return false;
        }
    }

    private class PersonTypeSelectedItem implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if( position == 0 ){
                tv_customer_name_title.setText(getResources().getString(R.string.tv_customer_name));
                tv_customer_cpfcnpj_title.setText(getResources().getString(R.string.tv_customer_cpf));
                tv_customer_inscricao_title.setText(getResources().getString(R.string.tv_customer_rg));
            } else {
                tv_customer_name_title.setText(getResources().getString(R.string.tv_customer_real_name));
                tv_customer_cpfcnpj_title.setText(getResources().getString(R.string.tv_customer_cnpj));
                tv_customer_inscricao_title.setText(getResources().getString(R.string.tv_customer_inscricao));
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}

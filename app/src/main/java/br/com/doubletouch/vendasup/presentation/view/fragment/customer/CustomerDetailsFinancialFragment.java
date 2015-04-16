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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.util.List;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.Installment;
import br.com.doubletouch.vendasup.data.entity.PriceTable;
import br.com.doubletouch.vendasup.data.entity.enumeration.ViewMode;
import br.com.doubletouch.vendasup.data.executor.JobExecutor;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.domain.interactor.installment.GetInstallmentListUseCase;
import br.com.doubletouch.vendasup.domain.interactor.installment.GetInstallmentListUseCaseImpl;
import br.com.doubletouch.vendasup.domain.interactor.installment.GetInstallmentUseCase;
import br.com.doubletouch.vendasup.domain.interactor.installment.GetInstallmentUseCaseImpl;
import br.com.doubletouch.vendasup.domain.interactor.pricetable.GetPriceTableListUseCase;
import br.com.doubletouch.vendasup.domain.interactor.pricetable.GetPriceTableListUseCaseImpl;
import br.com.doubletouch.vendasup.domain.interactor.pricetable.GetPriceTableUseCase;
import br.com.doubletouch.vendasup.domain.interactor.pricetable.GetPriceTableUseCaseImpl;
import br.com.doubletouch.vendasup.presentation.UIThread;
import br.com.doubletouch.vendasup.presentation.presenter.CustomerDetailsFinancialPresenter;
import br.com.doubletouch.vendasup.presentation.view.adapter.DetailsBaseAdapter;
import br.com.doubletouch.vendasup.presentation.view.components.parallax.ScrollTabHolderFragment;
import br.com.doubletouch.vendasup.presentation.view.fragment.CustomerDetailsFinancial;
import br.com.doubletouch.vendasup.util.DoubleUtil;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

/**
 * Created by LADAIR on 24/02/2015.
 */
public class CustomerDetailsFinancialFragment extends ScrollTabHolderFragment implements CustomerDetailsFinancial {

    /**
     * Um listview criado para agrupar o conteudo da tab.
     * Foi utilizado um listview, pois ele possui o evento AbsListView.OnScrollListener ,
     * necessario para functionar os efeitos parallax das tabs em baixo da imagem.
     */
    private ListView lv_content;
    private ScrollView sv_content;

    @InjectView(R.id.tv_customer_payment)
    @Optional
    TextView tv_customer_payment;

    @InjectView(R.id.sp_customer_price_table)
    @Optional
    Spinner sp_customer_price_table;

    @InjectView(R.id.tv_customer_price_table)
    @Optional
    TextView tv_customer_price_table;

    @InjectView(R.id.tv_customer_installment)
    @Optional
    TextView tv_customer_installment;

    @InjectView(R.id.sp_customer_installment)
    @Optional
    Spinner sp_customer_installment;

    @InjectView(R.id.sp_customer_payment)
    @Optional
    Spinner sp_customer_payment;

    @InjectView(R.id.tv_customer_credit_limit)
    @Optional
    TextView tv_customer_credit_limit;

    @InjectView(R.id.et_customer_credit_limit)
    @Optional
    EditText et_customer_credit_limit;



    public static final String ARGS = "position";
    public static final String ARGS_CUSTOMER = "customer";
    public static final String ARGS_VIEW_MODE = "view_mode";
    private int position;
    private Customer customer;

    private CustomerDetailsFinancialPresenter customerDetailsFinancialPresenter;

    private Activity activity;

    private ViewMode viewMode;

    public static Fragment newInstance(int position, ViewMode viewMode, Customer customer) {
        CustomerDetailsFinancialFragment fragment = new CustomerDetailsFinancialFragment();
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

        initializePresenter();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.activity = activity;
    }

    private void initializePresenter(){
        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();

        GetPriceTableListUseCase getPriceTableListUseCase = new GetPriceTableListUseCaseImpl(threadExecutor, postExecutionThread);
        GetPriceTableUseCase getPriceTableUseCase = new GetPriceTableUseCaseImpl( threadExecutor, postExecutionThread );
        GetInstallmentUseCase getInstallmentUseCase = new GetInstallmentUseCaseImpl(threadExecutor, postExecutionThread );
        GetInstallmentListUseCase getInstallmentListUseCase = new GetInstallmentListUseCaseImpl(threadExecutor, postExecutionThread );

        customerDetailsFinancialPresenter = new CustomerDetailsFinancialPresenter(this, getPriceTableListUseCase, getPriceTableUseCase, getInstallmentUseCase, getInstallmentListUseCase);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if( ViewMode.EDICAO.equals(viewMode) || ViewMode.INCLUSAO.equals(viewMode)){
            sv_content = new ScrollView(activity);
            sv_content.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));
            sv_content.setPadding(0,(int) getResources().getDimension(R.dimen.parallax_tab_height),0,0);

            View viewCustomerDetails = inflater.inflate( R.layout.view_customer_details_financial, null );

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


        if( ViewMode.VISUALIZACAO.equals(viewMode)){

            lv_content.setOnScrollListener(new OnScrollListner());
            lv_content.setAdapter(new DetailsPersonalAdapter(activity));
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
        Customer customer = (Customer) object;
        PriceTable priceTable = (PriceTable) sp_customer_price_table.getSelectedItem();

        if(priceTable != null){

            customer.setPriceTable(priceTable.getID());

        }


        Installment installment = (Installment) sp_customer_installment.getSelectedItem();

        if(installment  != null){

            customer.setInstallmentId( installment.getID() );

        }



        int formPayment = sp_customer_payment.getSelectedItemPosition();


        customer.setFormPayment( formPayment + 1);

         customer.setCreditLimit( DoubleUtil.toDouble( et_customer_credit_limit.getText().toString() ) );

    }

    @Override
    public void tabSelected() {

    }

    @Override
    public void loadPriceTableList(final List<PriceTable> tabelas) {

        ArrayAdapter<PriceTable> adapter = new ArrayAdapter<PriceTable>(activity, android.R.layout.simple_spinner_dropdown_item, tabelas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_customer_price_table.setAdapter(adapter);

        //Seleciona a tabela do cliente como default;
        for (int i = 0 ; i <  tabelas.size(); i++){
            PriceTable priceTable = tabelas.get(i);
            if(priceTable.getID() == customer.getPriceTable()){
                sp_customer_price_table.setSelection(i);
            }
        }

        sp_customer_price_table.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PriceTable priceTable = tabelas.get(position);
                customer.setPriceTable(priceTable.getID());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }



    @Override
    public void loadPriceTable(PriceTable priceTable) {

        if(priceTable != null){
            tv_customer_price_table.setText( priceTable.toString() );
        }

    }

    @Override
    public void loadInstallmentList(final List<Installment> parcelas) {
        ArrayAdapter<Installment> adapter = new ArrayAdapter<Installment>(activity, android.R.layout.simple_spinner_dropdown_item, parcelas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_customer_installment.setAdapter(adapter);

        //Seleciona a parcela do cliente como default;
        for (int i = 0 ; i <  parcelas.size(); i++){
            Installment installment = parcelas.get(i);
            if( installment.getID() == customer.getInstallmentId()){
                sp_customer_installment.setSelection(i);
                break;
            }
        }

        sp_customer_installment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Installment installment = parcelas.get(position);
                customer.setInstallmentId(installment.getID());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void loadInstallment(Installment installment) {
        if(installment != null){
            tv_customer_installment.setText(installment.toString());
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
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


    public void bind(View view) {

        ButterKnife.inject(this, view);

        if( ViewMode.EDICAO.equals(viewMode) || ViewMode.INCLUSAO.equals(viewMode)){
            ((ViewSwitcher) view.findViewById(R.id.vs_customer_price_table)).showNext();
            ((ViewSwitcher) view.findViewById(R.id.vs_customer_installment)).showNext();
            ((ViewSwitcher) view.findViewById(R.id.vs_customer_payment)).showNext();
            ((ViewSwitcher) view.findViewById(R.id.vs_customer_credit_limit)).showNext();

            sp_customer_payment.setSelection(customer.getFormPayment() -1);
            et_customer_credit_limit.setText(String.valueOf( customer.getCreditLimit() ));

            customerDetailsFinancialPresenter.initializeEditionMode(customer);

        } else {
            customerDetailsFinancialPresenter.initializeViewMode(customer);

            tv_customer_credit_limit.setText(DoubleUtil.formatToCurrency(customer.getCreditLimit(), true));

            if(customer.getFormPayment() != null){
                String[] formPayment = getResources().getStringArray(R.array.form_payment);
                tv_customer_payment.setText( formPayment[customer.getFormPayment() -1] );
            }
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
            View view = inflater.inflate(R.layout.view_customer_details_financial, null);

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

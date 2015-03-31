package br.com.doubletouch.vendasup.presentation.view.activity;

import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.data.entity.User;
import br.com.doubletouch.vendasup.data.entity.Usuario;
import br.com.doubletouch.vendasup.data.service.ProductService;
import br.com.doubletouch.vendasup.data.service.ProductServiceImpl;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.data.net.Integracao;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class CargaInicialActivity extends ActionBarActivity {

    @InjectView(R.id.btnCargaInicial)
    Button btnCargaInicial;

    @InjectView((R.id.btnProdutos))
    Button btnProdutos;

    @InjectView((R.id.btnAtualizar))
    Button btnAtualizar;

    @InjectView(R.id.txtEmail)
    EditText txtEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_inicial);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        //TODO PROVISÒRIO
        User user = new User();
        user.setEmail("junior@gmail.com");
        user.setOrganizationID(116);

        VendasUp.setUser(user);

        ButterKnife.inject(this);

        /*btnCargaInicial = (Button) findViewById(R.id.btnCargaInicial);
        btnProdutos = (Button) findViewById(R.id.btnProdutos);
        txtEmail = (EditText) findViewById(R.id.txtEmail);*/
        /*btnCargaInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Integracao integracao = new Integracao();
                    integracao.receberProdutos();

                    List<Produto> produtosSalvos = produtoController.listaTodos();
                    for (Produto produto : produtosSalvos){
                        Log.i("KRATOS", produto.getDescription());
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });*/

        /*btnProdutos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Navigator().navigateToProductList(CargaInicialActivity.this);
            }
        });*/

    }

    @OnClick(R.id.btnAtualizar)
    void onRefreshClick(){

        Integracao integracao = new Integracao();


        try{

            integracao.enviarProdutos(1);

            integracao.enviarClientes(1);

        } catch (Exception e) {

            Log.e(VendasUp.APP_TAG, e.getMessage(),e);

        }



    }


    @OnClick(R.id.btnCargaInicial)
    void onInitLoadClick(){

        try {

            Integracao integracao = new Integracao();

            integracao.receberParcelamentos(VendasUp.getUser().getOrganizationID());

            integracao.receberProdutos(VendasUp.getUser().getOrganizationID());

            integracao.receberClientes(VendasUp.getUser().getOrganizationID());

            integracao.receberTabelasPrecos(VendasUp.getUser().getOrganizationID());

            integracao.receberUsuarios(VendasUp.getUser().getOrganizationID());

            integracao.receberEmpresa(VendasUp.getUser().getOrganizationID());

            integracao.receberFilial(VendasUp.getUser().getOrganizationID());

            integracao.receberPromocoes(VendasUp.getUser().getOrganizationID());

            integracao.receberMetas(VendasUp.getUser().getOrganizationID());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.btnProdutos)
    void onButtonNavigateToProductListClick(){
        new Navigator().navigateToLogin(CargaInicialActivity.this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_carga_inicial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

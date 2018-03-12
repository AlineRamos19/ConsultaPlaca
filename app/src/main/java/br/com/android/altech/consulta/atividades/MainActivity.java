package br.com.android.altech.consulta.atividades;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.NumberFormat;

import br.com.android.altech.consulta.PlacaMask;
import br.com.android.altech.consulta.R;
import br.com.android.altech.consulta.dao.DatabaseManager;
import br.com.android.altech.consulta.dao.PlacaDbHelper;
import br.com.android.altech.consulta.modelo.CarroModelo;
import br.com.android.altech.consulta.service.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    protected DrawerLayout drawerLayout;

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private EditText mPlacaInformada;
    private LinearLayout mLinearLayout;
    private TextView mMarca;
    private TextView mModelo;
    private TextView mAno;
    private TextView mVersao;
    private TextView mPreço;
    private LinearLayout mLayoutExibeCarro;
    private TextWatcher mplacaMask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.tollbar);
        setSupportActionBar(toolbar);

        mLayoutExibeCarro = findViewById(R.id.layout_exibe_carro);
        mLayoutExibeCarro.setVisibility(View.GONE);
        mLinearLayout = findViewById(R.id.linear);
        mMarca = findViewById(R.id.marca);
        mModelo = findViewById(R.id.modelo);
        mAno = findViewById(R.id.ano);
        mVersao = findViewById(R.id.versão);
        mPreço = findViewById(R.id.preço);
        mPlacaInformada = findViewById(R.id.placa_informada);
        mPlacaInformada.addTextChangedListener(PlacaMask.insert("###-####", mPlacaInformada));


        TextView mBuscarPlaca = findViewById(R.id.txt_buscar);
        mBuscarPlaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String placa = mPlacaInformada.getText().toString();
                placa = placa.replace("-", "");
                if (placa.length() < 7 || placa.trim().isEmpty()) {
                    Snackbar.make(mLinearLayout, R.string.aviso_placa_invalida,
                            Snackbar.LENGTH_SHORT).show();
                    mPlacaInformada.setText("");
                } else {
                    pesquisar(placa);
                    esconderTeclado(MainActivity.this, mPlacaInformada);
                }
            }
        });
    }

    private void pesquisar(final String placa) {

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo informacaoRede = null;
        if (connectivityManager != null) {
            informacaoRede = connectivityManager.getActiveNetworkInfo();
        }
        if (informacaoRede != null && informacaoRede.isConnected()) {

            Call<CarroModelo> call = new RetrofitConfig().getRetrofitService().buscarCarro(placa);
            call.enqueue(new Callback<CarroModelo>() {
                @Override
                public void onResponse(Call<CarroModelo> call, Response<CarroModelo> response) {

                    CarroModelo carro = response.body();
                    if (carro == null) {
                        Snackbar snackbar = Snackbar.make(mLinearLayout, R.string.placa_sem_retorno,
                                Snackbar.LENGTH_LONG).setAction("REFAZER", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mPlacaInformada.setText("");
                                mLayoutExibeCarro.setVisibility(View.GONE);
                            }
                        });
                        snackbar.show();
                    } else {

                        mLayoutExibeCarro.setVisibility(View.VISIBLE);
                        mMarca.setText(carro.getmMarca());
                        mModelo.setText(carro.getmModelo());
                        mAno.setText(String.valueOf(carro.getmAno()));
                        mVersao.setText(carro.getmVersao());

                        BigDecimal valor = new BigDecimal(carro.getmPreço());
                        NumberFormat nf = NumberFormat.getCurrencyInstance();
                        String preco = nf.format(valor);
                        mPreço.setText(preco);
                        carro.setmPlaca(placa);

                        inserirDB(carro);
                    }
                }

                @Override
                public void onFailure(Call<CarroModelo> call, Throwable t) {
                    Log.e(LOG_TAG, "Erro: " + t.getMessage());
                }
            });

        } else {
            Snackbar.make(mLinearLayout, R.string.aviso_sem_conexao,
                    Snackbar.LENGTH_LONG).show();
        }
    }

    private void inserirDB(CarroModelo carro) {
        PlacaDbHelper helper = new PlacaDbHelper(MainActivity.this);
        DatabaseManager.inicializarInstancia(helper);
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        databaseManager.inserirCarro(carro);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_historico:
                Intent intent = new Intent(MainActivity.this, ListaHistorico.class);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void esconderTeclado(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService
                (Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}

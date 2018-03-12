package br.com.android.altech.consulta.atividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import br.com.android.altech.consulta.R;
import br.com.android.altech.consulta.adapter.CarroAdapter;
import br.com.android.altech.consulta.dao.DatabaseManager;
import br.com.android.altech.consulta.dao.PlacaDbHelper;

public class ListaHistorico extends AppCompatActivity {

    private RecyclerView mRecycler;
    CarroAdapter adapter;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_historico);

        Toolbar toolbar = findViewById(R.id.tollbar);
        setSupportActionBar(toolbar);
        TextView titulo_toolbar = toolbar.findViewById(R.id.titulo_toolbar);
        titulo_toolbar.setText(R.string.txt_toolbar_historico);
        titulo_toolbar.setTextColor(getResources().getColor(R.color.txtToolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_menu);


        configurarRecycler();

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaHistorico.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void configurarRecycler() {
        mRecycler = findViewById(R.id.recycler);
        mRecycler.setHasFixedSize(true);
        LinearLayoutManager linearVertical = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(linearVertical);

        PlacaDbHelper helper = new PlacaDbHelper(ListaHistorico.this);
        DatabaseManager.inicializarInstancia(helper);
        DatabaseManager databaseManager = DatabaseManager.getInstance();

        adapter = new CarroAdapter(databaseManager.obterCarros());
        mRecycler.setAdapter(adapter);
    }
}

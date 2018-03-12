package br.com.android.altech.consulta.dao;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.android.altech.consulta.modelo.CarroModelo;

public class DatabaseManager {

    private static DatabaseManager instance;
    private static PlacaDbHelper mPlacaDbHelper;
    private boolean conexaoAberta = false;
    private SQLiteDatabase db;

    public static synchronized void inicializarInstancia(PlacaDbHelper helper) {
        if (instance == null) {
            instance = new DatabaseManager();
            mPlacaDbHelper = helper;
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Declare 'inicializarInstancia(...)' primeiro.");
        }
        return instance;
    }

    public void openDb() {
        if (!conexaoAberta) {
            db = mPlacaDbHelper.getWritableDatabase();
            conexaoAberta = true;
        }
    }

    public void closeDb() {
        if (conexaoAberta) {
            db.close();
            conexaoAberta = false;
        }
    }

    public void inserirCarro(CarroModelo carro) {
        openDb();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PlacaContrato.PlacaEntrada.COLUNA_PLACA, carro.getmPlaca());
        contentValues.put(PlacaContrato.PlacaEntrada.COLUNA_MARCA, carro.getmMarca());
        contentValues.put(PlacaContrato.PlacaEntrada.COLUNA_MODELO, carro.getmModelo());
        contentValues.put(PlacaContrato.PlacaEntrada.COLUNA_ANO, carro.getmAno());
        contentValues.put(PlacaContrato.PlacaEntrada.COLUNA_VERSAO, carro.getmVersao());
        contentValues.put(PlacaContrato.PlacaEntrada.COLUNA_PRECO, carro.getmPreço());

        db.insert(PlacaContrato.PlacaEntrada.NOME_TABELA, null, contentValues);
        closeDb();
    }

    public List<CarroModelo> obterCarros() {

        List<CarroModelo> carros = new ArrayList<>();

        openDb();

        String sql = "SELECT * FROM " + PlacaContrato.PlacaEntrada.NOME_TABELA;
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                String placa = cursor.getString(cursor.getColumnIndex
                        (PlacaContrato.PlacaEntrada.COLUNA_PLACA));

                int ano = cursor.getInt(cursor.getColumnIndex
                        (PlacaContrato.PlacaEntrada.COLUNA_ANO));

                double preco = cursor.getDouble(cursor.getColumnIndex(
                        PlacaContrato.PlacaEntrada.COLUNA_PRECO));

                CarroModelo itemCarro = new CarroModelo(placa, ano, preco);
                carros.add(itemCarro);
            } while (cursor.moveToNext());
        }
        return carros;
    }
}

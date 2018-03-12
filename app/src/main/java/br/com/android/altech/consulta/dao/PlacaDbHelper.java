package br.com.android.altech.consulta.dao;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlacaDbHelper extends SQLiteOpenHelper {

    public static final String NOME_TABELA = "produto.db";
    public static final int VERSAO_DB = 1;

    public static final String CRIAR_TABELA =
            "CREATE TABLE " + PlacaContrato.PlacaEntrada.NOME_TABELA + "(" +
            PlacaContrato.PlacaEntrada._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            PlacaContrato.PlacaEntrada.COLUNA_PLACA + " TEXT NOT NULL , " +
            PlacaContrato.PlacaEntrada.COLUNA_MARCA + " TEXT NOT NULL , " +
            PlacaContrato.PlacaEntrada.COLUNA_MODELO + " TEXT NOT NULL , " +
            PlacaContrato.PlacaEntrada.COLUNA_ANO + " INTEGER NOT NULL , " +
            PlacaContrato.PlacaEntrada.COLUNA_VERSAO + " TEXT NOT NULL , " +
            PlacaContrato.PlacaEntrada.COLUNA_PRECO + " REAL NOT NULL );";

    private static final String DELETAR_TABELA = "DROP TABLE IF IXISTS " + PlacaContrato.PlacaEntrada.NOME_TABELA;

    public PlacaDbHelper(Context context) {
        super(context, NOME_TABELA, null, VERSAO_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CRIAR_TABELA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DELETAR_TABELA);
        onCreate(db);
    }
}

package br.com.android.altech.consulta.dao;


import android.provider.BaseColumns;

public final class PlacaContrato {

    private PlacaContrato(){}

    public static final class PlacaEntrada implements BaseColumns{

        public static final String NOME_TABELA = "placas";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUNA_PLACA = "placa";
        public static final String COLUNA_MARCA = "marca";
        public static final String COLUNA_MODELO = "modelo";
        public static final String COLUNA_ANO = "ano";
        public static final String COLUNA_VERSAO = "versao";
        public static final String COLUNA_PRECO = "preco";

    }
}

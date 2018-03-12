package br.com.android.altech.consulta.service;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    public static final String BASE_URL = "http://linux2.autorisco.com.br:5500";
    private static Retrofit retrofit = null;

    public RetrofitConfig(){
        if(retrofit == null){
            this.retrofit = new Retrofit.Builder().
                    baseUrl(BASE_URL).
                    addConverterFactory(GsonConverterFactory.create()).
                    build();
        }
    }

    public CarroService getRetrofitService(){
        return retrofit.create(CarroService.class);
    }
}

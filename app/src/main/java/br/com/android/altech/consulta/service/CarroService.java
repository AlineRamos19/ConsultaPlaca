package br.com.android.altech.consulta.service;


import br.com.android.altech.consulta.modelo.CarroModelo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CarroService {

    @GET("/api/veiculo")
    Call<CarroModelo> buscarCarro(@Query("placa") String placa);

}

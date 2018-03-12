package br.com.android.altech.consulta.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

import br.com.android.altech.consulta.R;
import br.com.android.altech.consulta.modelo.CarroModelo;


public class CarroAdapter extends RecyclerView.Adapter {

    private List<CarroModelo> mListaCarros;


    public CarroAdapter( List<CarroModelo> listaCarros) {
        this.mListaCarros = listaCarros;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carro_historico, parent,
                false);
        CarroViewHolder holder = new CarroViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        CarroViewHolder carroHolder = (CarroViewHolder) holder;
        final CarroModelo itemCarro =  mListaCarros.get(position);

        String placa = itemCarro.getmPlaca();
        StringBuilder stringBuilder = new StringBuilder(placa);
        stringBuilder.insert(placa.length() - 4, '-');
        carroHolder.mPlaca.setText(stringBuilder.toString());

        carroHolder.mAno.setText(String.valueOf(itemCarro.getmAno()));

        BigDecimal valor = new BigDecimal(itemCarro.getmPreço());
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        String preco = nf.format(valor);
        carroHolder.mPreco.setText(preco);
    }

    @Override
    public int getItemCount() {
        if(mListaCarros != null){
            return mListaCarros.size();
        } else{
            return 0;
        }
    }

    public class CarroViewHolder extends RecyclerView.ViewHolder{

        final TextView mPlaca;
        final TextView mAno;
        final TextView mPreco;

        public CarroViewHolder(View itemView) {
            super(itemView);

            mPlaca = itemView.findViewById(R.id.item_placa);
            mAno = itemView.findViewById(R.id.item_ano);
            mPreco = itemView.findViewById(R.id.item_preco);
        }

    }
}

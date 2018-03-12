package br.com.android.altech.consulta.modelo;

import com.google.gson.annotations.SerializedName;

public class CarroModelo {

    @SerializedName("marca")
    private String mMarca;

    @SerializedName("modelo")
    private String mModelo;

    @SerializedName("ano")
    private int mAno;

    @SerializedName("versao")
    private String mVersao;

    @SerializedName("preco")
    private double mPreço;

    private String mPlaca;

    private int mId;

    public CarroModelo(String marca, String modelo, int ano, String versao, double preço) {
        this.mMarca = marca;
        this.mModelo = modelo;
        this.mAno = ano;
        this.mVersao = versao;
        this.mPreço = preço;
    }

    public CarroModelo(String placa, int ano, double preço) {
        this.mPlaca = placa;
        this.mAno = ano;
        this.mPreço = preço;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmPlaca() {
        return mPlaca;
    }

    public void setmPlaca(String mPlaca) {
        this.mPlaca = mPlaca;
    }

    public String getmMarca() {
        return mMarca;
    }

    public void setmMarca(String mMarca) {
        this.mMarca = mMarca;
    }

    public String getmModelo() {
        return mModelo;
    }

    public void setmModelo(String mModelo) {
        this.mModelo = mModelo;
    }

    public int getmAno() {
        return mAno;
    }

    public void setmAno(int mAno) {
        this.mAno = mAno;
    }

    public String getmVersao() {
        return mVersao;
    }

    public void setmVersao(String mVersao) {
        this.mVersao = mVersao;
    }

    public double getmPreço() {
        return mPreço;
    }

    public void setmPreço(double mPreço) {
        this.mPreço = mPreço;
    }
}

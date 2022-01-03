package com.smallacademy.MobileTani;

import java.io.Serializable;

public class Produkmodel implements Serializable {
    private String Nama;
    private String Deskripsi;
    private String Harga;
    private String jmlhbarang;
    private String picbarang;
    private String Jenis;

    public String getNama(){return Nama;}
    public void  setNama(String Nama){
        this.Nama= Nama;
    }
    public String getDeskripsi(){return  Deskripsi;}
    public void setDeskripsi(String Desrkripsi){
        this.Deskripsi= Desrkripsi;
    }
    public  String getPicbarang(){
        return picbarang;
    }
    public void setPicbarang(String picbarang){
        this.picbarang=  picbarang;
    }
    public String getHarga(){return Harga;}
    public void setHarga(String Harga){
        this.Harga = Harga;
    }
    public String getJmlhbarang(){return jmlhbarang;}
    public void setJmlhbarang(String jmlhbarang){
        this.jmlhbarang = jmlhbarang;
    }
    public  String getJenis(){
        return Jenis;
    }
    public void setJenis(String jenis){
        this.Jenis=  Jenis;
    }
}

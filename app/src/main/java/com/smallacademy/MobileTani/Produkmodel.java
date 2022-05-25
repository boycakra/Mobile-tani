package com.smallacademy.MobileTani;

import java.io.Serializable;

public class Produkmodel implements Serializable {
    private String Nama;
    private String Deskripsi;
    private String Harga;
    private String jmlhbarang;
    private String picbarang;
    private String jenis;
    private String Idpetani;
    private String Phonenumber;

    public Produkmodel(String Nama,String Deskripsi, String Harga, String jmlhbarang,String picbarang, String jenis,String Idpetani,String Phonenumber) {
        this.Nama=Nama;
        this.Deskripsi=Deskripsi;
        this.Harga=Harga;
        this.jmlhbarang=jmlhbarang;
        this.picbarang=picbarang;
        this.jenis= jenis;
        this.Idpetani=Idpetani;
        this.Phonenumber=Phonenumber;

    }
    public  Produkmodel(){

    }


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
    public  String getjenis(){
        return jenis;
    }
    public void setJenis(String jenis){
        this.jenis=  jenis;
    }
    public  String getIdpetani(){
        return Idpetani;
    }
    public void setIdpetani(String Idpetani){
        this.Idpetani=  Idpetani;
    }
    public  String getPhonenumber(){
        return Phonenumber;
    }
    public void setPhonenumber(String Phonenumber){
        this.Phonenumber=  Phonenumber;
    }
}

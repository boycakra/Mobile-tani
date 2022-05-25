package com.smallacademy.MobileTani;

import java.io.Serializable;

import androidx.appcompat.app.AppCompatActivity;

public class modelcheckout implements Serializable {
    private String jenischeckout;
    private String namaproduk;
    private String hargaproduk;
    private String picbarangchekout;
    private String Phonenumber;

    public modelcheckout(String jenischeckout,String namaproduk, String hargaproduk, String picbarangchekout, String Phonenumber) {
        this.jenischeckout=jenischeckout;
        this.namaproduk=namaproduk;
        this.hargaproduk=hargaproduk;
        this.picbarangchekout=picbarangchekout;
        this.Phonenumber=Phonenumber;


    }
    public  modelcheckout(){

    }
    public String getJenischeckout(){return jenischeckout;}
    public void  setJenischeckout(String jenischeckout){
        this.jenischeckout= jenischeckout;
    }

    public String getNamaproduk(){return  namaproduk;}
    public void setNamaproduk(String namaproduk){
        this.namaproduk= namaproduk;
    }

    public  String getHargaproduk(){
        return hargaproduk;
    }
    public void setHargaproduk(String hargaproduk){

        this.hargaproduk=  hargaproduk;
    }
    public String getPicbarangchekout(){return picbarangchekout;}
    public void setPicbarangchekout(String picbarangchekout){
        this.picbarangchekout = picbarangchekout;
    }

    public String getPhonenumber(){return Phonenumber;}
    public void setPhonenumber(String Phonenumber){
        this.Phonenumber = Phonenumber;
    }

}

package com.smallacademy.MobileTani;

import java.io.Serializable;

public class UserPetani implements Serializable {
    private String FullName;
    private String Useremail;
    private String password;
    private String address;
    private String more;
    private String Phonenumber;
    private String Housephone;
    private String picprofil;
    private String Saldo;


    public UserPetani(){}
    public UserPetani(String name,String email, String Phonenumber) {
        this.FullName = name;
        this.Useremail = email;
        this.Phonenumber = Phonenumber;
    }

    public String getFullName() {
        return FullName;
    }

    public String getUseremail() {
        return Useremail;
    }

    public String getPhonenumber() {
        return Phonenumber;
    }

    public void setFullName(String name) {
        this.FullName = name;
    }

    public void setUseremail(String useremail) {
        this.Useremail = useremail;
    }

    public void setPhonenumber(String Phonenumber) {
        this.Phonenumber = Phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }

    public String getHousephone() {
        return Housephone;
    }

    public void setHousephone(String housephone) {
        Housephone = housephone;
    }

    public  String getPicprofil(){
        return picprofil;
    }
    public void setPicprofil(String picprofil){
        this.picprofil=  picprofil;
    }

    public  String getSaldo(){
        return Saldo;
    }
    public void setSaldo(String Saldo){
        this.Saldo=  Saldo;
    }



}
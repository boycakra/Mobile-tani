package com.smallacademy.MobileTani;

import java.io.Serializable;

public class modeltopupuser implements Serializable {
    private String iduserpembeli;
    private String jumlahtopup;
    public modeltopupuser(String iduserpembeli,String jumlahtopup ) {
        this.iduserpembeli=iduserpembeli;
        this.jumlahtopup=jumlahtopup;


    }
    public  modeltopupuser(){

    }
    public String getIduserpembeli(){return iduserpembeli;}
    public void  setIduserpembeli(String iduserpembeli){
        this.iduserpembeli= iduserpembeli;
    }
    public String getJumlahtopup(){return  jumlahtopup;}
    public void setJumlahtopup(String jumlahtopup){
        this.jumlahtopup= jumlahtopup;
    }
}

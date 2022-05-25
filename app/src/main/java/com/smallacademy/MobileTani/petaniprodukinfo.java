package com.smallacademy.MobileTani;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class petaniprodukinfo extends AppCompatActivity {
    String Nama;
    String Deskripsi;
    String Harga;
    String jmlhbarang;
    String picbarang;
    String Jenis;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eachproduk);

        Nama = getIntent().getStringExtra("Nama");
        Jenis = getIntent().getStringExtra("Jenis");
        picbarang = getIntent().getStringExtra("picbarang");
        Deskripsi = getIntent().getStringExtra("Deskripsi");
        Harga = getIntent().getStringExtra("Harga");
        jmlhbarang = getIntent().getStringExtra("jmlhbarang");

        ImageView post_pictempat = (ImageView) findViewById(R.id.picgambar);
        Picasso.get().load(picbarang).into(post_pictempat);

        TextView post_labeltempat = (TextView) findViewById(R.id.textinfo);
        post_labeltempat.setText(Jenis);
        TextView post_labelproduk = (TextView) findViewById(R.id.produk);
        post_labelproduk.setText(Jenis);

        TextView post_namatempat = (TextView) findViewById(R.id.Nama );
        post_namatempat.setText(Nama);

        TextView post_deskripsi = (TextView) findViewById(R.id.deskripsi);
        post_deskripsi.setText(Deskripsi);

        TextView post_Harga = (TextView) findViewById(R.id.hargadatabase);
        post_Harga.setText(Harga);

        TextView post_jmlhbarang = (TextView) findViewById(R.id.jmlha);
        post_jmlhbarang.setText(jmlhbarang);


    }



}


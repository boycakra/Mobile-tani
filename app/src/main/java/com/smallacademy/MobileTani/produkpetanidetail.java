package com.smallacademy.MobileTani;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class produkpetanidetail extends AppCompatActivity {
    String Nama;
    String Deskripsi;
    String Harga;
    String jmlhbarang;
    String picbarang;
    String Jenis;
    String phonenumber;
    String jumlahharga;
    private EditText banyakbeli;
    private EditText jumlahbeli;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference chekoutuser =db.collection("chekoutuser");
    Button btn;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eachprodukpembeli);

        Nama = getIntent().getStringExtra("Nama");
        Jenis = getIntent().getStringExtra("Jenis");
        picbarang = getIntent().getStringExtra("picbarang");
        Deskripsi = getIntent().getStringExtra("Deskripsi");
        Harga = getIntent().getStringExtra("Harga");
        jmlhbarang = getIntent().getStringExtra("jmlhbarang");
        phonenumber = getIntent().getStringExtra("phonenumber");
        banyakbeli = findViewById(R.id.banyakbeli);
        jumlahbeli = findViewById(R.id.jumlahharga);
        btn = findViewById(R.id.beli);
        final String num = phonenumber;
        final String text = "Halo apakah Barang";
        final String text1 = Nama;
        final String text3 = "Masih ada ?";
        Log.i("Phone",phonenumber);



        Button pencet = (Button) findViewById(R.id.onClick);


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

        pencet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int belibanyak = Integer.parseInt(banyakbeli.getText().toString());
                int hargaproduk = Integer.parseInt(Harga);
                int total = belibanyak * hargaproduk ;
                jumlahbeli.setText(String.valueOf(total));
                jumlahharga = jumlahbeli.getText().toString();

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean installed = isAppInstalled("com.whatsapp");

                if (installed)
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+num+"&text="+ text +text1 +jumlahharga +text3));
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(produkpetanidetail.this, "Whatsapp is not installed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private boolean isAppInstalled(String s) {
        PackageManager packageManager = getPackageManager();
        boolean is_installed;

        try {
            packageManager.getPackageInfo(s, PackageManager.GET_ACTIVITIES);
            is_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            is_installed = false;
            e.printStackTrace();
        }
        return is_installed;
        }
    public void addNote(View v) {
        String jenischeckout = Jenis;
        String namaproduk=Nama;
        String hargaproduk=jumlahharga;
        String picbarangchekout=picbarang;
        String Phonenumber=phonenumber;
        Log.i("addphone",phonenumber);


        modelcheckout note = new modelcheckout(jenischeckout,namaproduk,hargaproduk,picbarangchekout,Phonenumber);

        chekoutuser.add(note).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(),"Top up succes",Toast.LENGTH_LONG).show();
            }
        });

    }
    }
//    public void MARIO(View v){
//        int belibanyak = Integer.parseInt(banyakbeli.getText().toString());
//        int hargaproduk = Integer.parseInt(Harga);
//        int total = belibanyak * hargaproduk ;
//        jumlahbeli.setText(total);
//
//    }



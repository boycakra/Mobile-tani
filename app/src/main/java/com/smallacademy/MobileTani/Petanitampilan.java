package com.smallacademy.MobileTani;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;
import com.firebase.ui.database.FirebaseRecyclerAdapter;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Petanitampilan extends AppCompatActivity {
    private EditText FullName;
    private RecyclerView produklist;
    private TextView txt_more;
    private DatabaseReference mdatabase;
    private ImageView profilpic;
    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String LANGUAGE = "En";
    private UserPetani user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petanimain);
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Produk");
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        Query query = rootRef.collection("Produk")
                .orderBy("Produk", Query.Direction.ASCENDING);
        mdatabase.keepSynced(true);
        mdatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {


            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult()));
                }
            }
        });
        FullName = findViewById(R.id.usertext);
        txt_more = findViewById(R.id.deskripsi);
        getProfil();
        setProfilpic();
        profilpic = findViewById(R.id.profilegambar);
        produklist =(RecyclerView)findViewById(R.id.rycleview);
        produklist.setHasFixedSize(true);
        produklist.setLayoutManager(new LinearLayoutManager(this));

        firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<UserPetani,petaniholder>
                (UserPetani.class,R.layout.produk,petaniholder.class,mdatabase) {
            @Override
            protected void populateViewHolder(petaniholder viewHolder, UserPetani model, int position) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), petaniprodukinfo.class);
                        intent.putExtra("jenis",model.getjenis());
                        intent.putExtra("Nama",model.getNama());
                        intent.putExtra("picbarang",model.getPicbarang());
                        intent.putExtra("Deskripsi",model.getDeskripsi());
                        intent.putExtra("Harga",model.getHarga().toString());
                        intent.putExtra("jmlhbarang",model.getJmlhbarang().toString());


                        //Toast.makeText(v.getContext(),model.getLongtitude().toString(),Toast.LENGTH_LONG).show();
                        v.getContext().startActivity(intent);

                    }
                });
                viewHolder.setjenisbarang(model.getjenis());
                viewHolder.setpicbarang(getApplication(),model.getPicbarang());
                viewHolder.setHarga(model.getHarga());
                viewHolder.setNamabarang(model.getNama());
                viewHolder.setdeskripsi(model.getDeskripsi());
                viewHolder.setJmlhbarang(model.getJmlhbarang());

            }
        };
        produklist.setAdapter(firebaseRecyclerAdapter);
        Log.i("Mario1", String.valueOf(firebaseRecyclerAdapter));




    }
    protected void onStart() {
        super.onStart();

    }
    public static class petaniholder extends RecyclerView.ViewHolder
    {
        View mView;
        public petaniholder(View itemView)
        {
            super(itemView);
            mView=itemView;

        }
        public void setpicbarang(Application ctx, String pictempat){
            ImageView post_pictempat=(ImageView) mView.findViewById(R.id.mainpic);
            Picasso.get().load(pictempat).into(post_pictempat);
        }
        public void setjenisbarang (String jenis){
            TextView post_labeljenis=(TextView)mView.findViewById(R.id.tempatlbl);
            post_labeljenis.setText(jenis);
        }
        public void setHarga(Double harga){
            TextView post_harga=(TextView)mView.findViewById(R.id.harga);
            post_harga.setText(Double.toString(harga));
        }
        public void setNamabarang(String namabarang){
            TextView post_namabarang=(TextView) mView.findViewById(R.id.tempatsite);
            post_namabarang.setText(namabarang);
            Log.i("Mario", String.valueOf(post_namabarang));

        }
        public  void setdeskripsi(String deskripsi){
            TextView post_deskripsi=(TextView) mView.findViewById(R.id.deskripsiproduk);
            post_deskripsi.setText((deskripsi));
        }
        public  void setJmlhbarang(Double Jmlhbarang){
            TextView post_longtitude=(TextView)mView.findViewById(R.id.longdivesite);
            post_longtitude.setText(Double.toString(Jmlhbarang));
        }

    }

    private void setProfilpic() {
        DocumentReference userRef = db.collection("Users").document(FirebaseAuth.getInstance().getUid());
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(UserPetani.class);
                if(user.getPicprofil()!=null){
                    Picasso.get().load(user.getPicprofil()).into(profilpic);
                }
            }
        });
    }

    public void getProfil() {
        DocumentReference userRef = db.collection("Users").document(FirebaseAuth.getInstance().getUid());
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(UserPetani.class);
                if(user.getFullName() != null){
                    FullName.setText("Welcome "+user.getFullName());
                }
                if(user.getMore() != null){
                    txt_more.setText(user.getMore());
                }
            }
        });
    }

    public void logoutAdmin(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
    public void personalProfile(View view) {
        DocumentReference userRef = db.collection("Users").document(FirebaseAuth.getInstance().getUid());
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(UserPetani.class);
                Intent intent = new Intent(getApplicationContext(), PersonalProfileTani.class);
                intent.putExtra("Users", user);
                startActivity(intent);
            }
        });
    }
    public String loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String currentLanguage = sharedPreferences.getString(LANGUAGE, "En");
        return currentLanguage;
    }
}

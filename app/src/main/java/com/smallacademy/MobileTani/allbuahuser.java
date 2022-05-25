package com.smallacademy.MobileTani;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class allbuahuser extends AppCompatActivity {
    private RecyclerView produklist;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirestoreRecyclerAdapter adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allbuahuser);
        Query query = db.collection("Buah");
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String data = "";

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Log.i("halo", documentSnapshot.getId());


                }

            }
        });
        db.collection("Produk")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("benar", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d("kisa", "Error getting documents: ", task.getException());
                        }
                    }
                });

        FirestoreRecyclerOptions<Produkmodel> options= new FirestoreRecyclerOptions.Builder<Produkmodel>()
                .setQuery(query, Produkmodel.class)
                .build();
        //options.getSnapshots().get(0).getNama();
        // Log.i("Mario", options.getSnapshots().get(0).getNama());


        adapter = new FirestoreRecyclerAdapter<Produkmodel, allbuahuser.produkviewHolder>(options) {
            @NonNull
            @NotNull
            @Override
            public allbuahuser.produkviewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.produk, parent, false);

                return new allbuahuser.produkviewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull @NotNull allbuahuser.produkviewHolder holder, int position, @NonNull @NotNull Produkmodel model) {
                holder.Name.setText(model.getNama());
                holder.jenis.setText(model.getjenis());
                holder.Harga.setText(model.getHarga());
                holder.jmlhbarang.setText(model.getJmlhbarang());
                holder.Deskripsi.setText(model.getDeskripsi());
                try {
                    Picasso.get().load(model.getPicbarang()).into(holder.picbarang);
                }
                catch (Exception e ){

                }
                Log.i("Rizal",model.getNama());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), produkpetanidetail.class);
                        intent.putExtra("Jenis",model.getjenis());
                        intent.putExtra("Nama",model.getNama());
                        intent.putExtra("picbarang",model.getPicbarang());
                        intent.putExtra("Deskripsi",model.getDeskripsi());
                        intent.putExtra("Harga",model.getHarga().toString());
                        intent.putExtra("jmlhbarang",model.getJmlhbarang().toString());
                        intent.putExtra("phonenumber",model.getPhonenumber().toString());



                        //Toast.makeText(v.getContext(),model.getLongtitude().toString(),Toast.LENGTH_LONG).show();
                        v.getContext().startActivity(intent);

                    }
                });
            }
        };
        produklist =(RecyclerView)findViewById(R.id.rycleview1);
        produklist.setHasFixedSize(true);
        produklist.setLayoutManager(new LinearLayoutManager(this));
        produklist.setAdapter(adapter);


        produklist.setHasFixedSize(true);
        produklist.setLayoutManager(new LinearLayoutManager(this));



    }
    private class produkviewHolder extends RecyclerView.ViewHolder{
        private TextView Name;
        private TextView Deskripsi;
        private TextView jmlhbarang;
        private ImageView picbarang;
        private TextView Harga;
        private TextView jenis;
        public produkviewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.namaproduk);
            Deskripsi = itemView.findViewById(R.id.deskripsiproduk);
            Harga = itemView.findViewById(R.id.harga);
            jenis = itemView.findViewById(R.id.labelproduk);
            picbarang = itemView.findViewById(R.id.mainpic);
            jmlhbarang = itemView.findViewById(R.id.jmlha);
            Deskripsi = itemView.findViewById(R.id.deskripsiproduk);

        }
    }

    protected void onStop() {
        super.onStop();
        adapter.stopListening();

    }
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }
    public void back(View view) {
        Intent intent = new Intent(this, Usertampilan.class);
        startActivity(intent);
    }
    }


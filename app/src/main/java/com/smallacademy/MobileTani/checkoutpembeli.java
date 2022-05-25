package com.smallacademy.MobileTani;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class checkoutpembeli extends AppCompatActivity {
    private RecyclerView produklist;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirestoreRecyclerAdapter adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chekoutpembeli);
        Query query = db.collection("chekoutuser");
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String data = "";

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Log.i("halo", documentSnapshot.getId());


                }

            }
        });
//        db.collection("Produk")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d("benar", document.getId() + " => " + document.getData());
//                            }
//                        } else {
//                            Log.d("kisa", "Error getting documents: ", task.getException());
//                        }
//                    }
//                });

        FirestoreRecyclerOptions<modelcheckout> options= new FirestoreRecyclerOptions.Builder<modelcheckout>()
                .setQuery(query, modelcheckout.class)
                .build();
        //options.getSnapshots().get(0).getNama();
        // Log.i("Mario", options.getSnapshots().get(0).getNama());


        adapter = new FirestoreRecyclerAdapter<modelcheckout, checkoutpembeli.produkchekout>(options) {
            @NonNull
            @NotNull
            @Override
            public checkoutpembeli.produkchekout onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chekout, parent, false);

                return new checkoutpembeli.produkchekout(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull @NotNull checkoutpembeli.produkchekout holder, int position, @NonNull @NotNull modelcheckout model) {
              Log.i("jenischekout", String.valueOf(holder));
                holder.jenischeckout.setText(model.getJenischeckout());
                holder.namaproduk.setText(model.getNamaproduk());
                holder.hargaproduk.setText(model.getHargaproduk());
                holder.Phonenumber.setText(model.getPhonenumber());
                try {
                    Picasso.get().load(model.getPicbarangchekout()).into(holder.picbarangchekout);
                }
                catch (Exception e ){
                    Log.i("Eror", String.valueOf(e));

                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { boolean installed = isAppInstalled("com.whatsapp");
                        final String text = "Halo apakah sudah di proses Barang";
                        final String text3 = "Sudah di kirim ?";
                        if (installed)
                        {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+model.getPhonenumber()+"&text="+ text  + model.getHargaproduk()+""+text3));
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(checkoutpembeli.this, "Whatsapp is not installed!", Toast.LENGTH_SHORT).show();
                        }

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
    private class produkchekout extends RecyclerView.ViewHolder{
        private TextView jenischeckout;
        private TextView namaproduk;
        private TextView hargaproduk;
        private ImageView picbarangchekout;
        private TextView Phonenumber;
        public produkchekout(@NonNull @NotNull View itemView) {
            super(itemView);
            jenischeckout = itemView.findViewById(R.id.Jenis);
            namaproduk = itemView.findViewById(R.id.namaproduk);
            hargaproduk = itemView.findViewById(R.id.hargachekou2);

            picbarangchekout = itemView.findViewById(R.id.picchekout);
            Phonenumber = itemView.findViewById(R.id.Phone_Numberchekout);


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

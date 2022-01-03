package com.smallacademy.MobileTani;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;



import org.jetbrains.annotations.NotNull;

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
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseFirestore firebaseFirestore;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String LANGUAGE = "En";
    private UserPetani user;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petanimain);


        //query
        Query query = db.collection("Users").document(FirebaseAuth.getInstance().getUid()).collection("Produk");
//        CollectionReference docRef =db.collection("Users").document(FirebaseAuth.getInstance().getUid()).collection("Produkmodel");
//        docRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        String data = "";
//
//                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
//                           Log.i("Nigga", documentSnapshot.getId());
//
//
//                        }
//
//                    }
//                });
        //Rycle
        FirestoreRecyclerOptions<Produkmodel> options= new FirestoreRecyclerOptions.Builder<Produkmodel>()
                .setQuery(query, Produkmodel.class)
                .build();
        //options.getSnapshots().get(0).getNama();
       // Log.i("Mario", options.getSnapshots().get(0).getNama());


        adapter = new FirestoreRecyclerAdapter<Produkmodel, produkviewHolder>(options) {
            @NonNull
            @NotNull
            @Override
            public produkviewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.produk, parent, false);

                return new produkviewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull @NotNull Petanitampilan.produkviewHolder holder, int position, @NonNull @NotNull Produkmodel model) {
                holder.Name.setText(model.getNama());
                holder.jenis.setText(model.getJenis());
                holder.Harga.setText(model.getHarga());
                holder.jmlhbarang.setText(model.getJmlhbarang());
                holder.Deskripsi.setText(model.getDeskripsi());
                Picasso.get().load(model.getPicbarang()).into(holder.picbarang);
                Log.i("Rizal",model.getNama());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), petaniprodukinfo.class);
                        intent.putExtra("Jenis",model.getJenis());
                        intent.putExtra("Nama",model.getNama());
                        intent.putExtra("picbarang",model.getPicbarang());
                        intent.putExtra("Deskripsi",model.getDeskripsi());
                        intent.putExtra("Harga",model.getHarga().toString());
                        intent.putExtra("jmlhbarang",model.getJmlhbarang().toString());


                        //Toast.makeText(v.getContext(),model.getLongtitude().toString(),Toast.LENGTH_LONG).show();
                        v.getContext().startActivity(intent);

                    }
                });
            }
        };
        produklist =(RecyclerView)findViewById(R.id.rycleview);
        produklist.setHasFixedSize(true);
        produklist.setLayoutManager(new LinearLayoutManager(this));
        produklist.setAdapter(adapter);

        FullName = findViewById(R.id.usertext);
        txt_more = findViewById(R.id.deskripsi);
        getProfil();
        setProfilpic();

        profilpic = findViewById(R.id.profilegambar);

        produklist.setHasFixedSize(true);
        produklist.setLayoutManager(new LinearLayoutManager(this));





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

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();

    }
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }
    public void addbarang (View view) {
        DocumentReference userRef = db.collection("Users").document(FirebaseAuth.getInstance().getUid());
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(UserPetani.class);
                Intent intent = new Intent(getApplicationContext(), Addproduk.class);
                intent.putExtra("Users", user);
                startActivity(intent);
            }
        });
    }
}

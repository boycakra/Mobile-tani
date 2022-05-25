package com.smallacademy.MobileTani;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class Usertampilan extends AppCompatActivity {
    SliderView sliderView;
    int[] images = {R.drawable.promo,
            R.drawable.jeruk,
            R.drawable.apel,
    };

    private TextView FullName;
    private TextView saldo;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String LANGUAGE = "En";
    private userpembeli user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usermain);
        sliderView = findViewById(R.id.image_slider);

        SliderAdapter sliderAdapter = new SliderAdapter(images);


        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();
        saldo= findViewById(R.id.saldouserdatabase);
        FullName = findViewById(R.id.usertext);
        getProfil();
        TextView logout = findViewById(R.id.logoutBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });
    }
    public void getProfil() {
        DocumentReference userRef = db.collection("Users").document(FirebaseAuth.getInstance().getUid());
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(userpembeli.class);
                Log.i("Mario", String.valueOf(user.getFullName()));
                if(user.getFullName() != null){
                    FullName.setText("Welcome "+user.getFullName());
                    Log.i("Mario", String.valueOf(FullName));
                }
                if(user.getSaldo() != null){
                    saldo.setText(user.getSaldo());
                }
            }
        });
    }
    public void personalProfileuser(View view) {
        DocumentReference userRef = db.collection("Users").document(FirebaseAuth.getInstance().getUid());
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(userpembeli.class);
                Intent intent = new Intent(getApplicationContext(), PersonalProfilUser.class);
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
    public void pindah(View view) {
        DocumentReference userRef = db.collection("Produk").document(FirebaseAuth.getInstance().getUid());
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(userpembeli.class);
                Intent intent = new Intent(getApplicationContext(), allprodukuser.class);
                startActivity(intent);
            }

        });
    }
    public void checkout(View view){
        Intent intent = new Intent(getApplicationContext(),checkoutpembeli.class);
        startActivity(intent);
    }
    public void topup(View view){
        Intent intent = new Intent(getApplicationContext(),topupPembeli.class);
        startActivity(intent);
    }
    public void Buah(View view) {
        DocumentReference userRef = db.collection("Buah").document(FirebaseAuth.getInstance().getUid());
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(userpembeli.class);
                Intent intent = new Intent(getApplicationContext(), allbuahuser.class);
                startActivity(intent);
            }

        });
    }
    public void Sayur(View view) {
        DocumentReference userRef = db.collection("Sayur").document(FirebaseAuth.getInstance().getUid());
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(userpembeli.class);
                Intent intent = new Intent(getApplicationContext(), allsayuruser.class);
                startActivity(intent);
            }

        });
    }

}
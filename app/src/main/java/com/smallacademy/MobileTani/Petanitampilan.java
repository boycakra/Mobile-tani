package com.smallacademy.MobileTani;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AppCompatActivity;

public class Petanitampilan extends AppCompatActivity {
    private EditText FullName;
    private TextView txt_more;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String LANGUAGE = "En";
    private UserPetani user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petanimain);
        FullName = findViewById(R.id.usertext);
        txt_more = findViewById(R.id.deskripsi);
        getProfil();



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

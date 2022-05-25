package com.smallacademy.MobileTani;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AppCompatActivity;

public class topupPembeli extends AppCompatActivity {
    private EditText topuptext;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference topup =db.collection("Topup");
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.topuppembeli);
        topuptext= findViewById(R.id.topupjumlah);

    }
    public void addNote(View v) {
        String jumlahtopup = topuptext.getText().toString();
        String Iduser = FirebaseAuth.getInstance().getUid();


        modeltopupuser note = new modeltopupuser(jumlahtopup,Iduser);

        topup.add(note).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(),"Top up succes",Toast.LENGTH_LONG).show();
            }
        });

    }
    public void back(View view) {
        Intent intent = new Intent(this, Usertampilan.class);
        startActivity(intent);
    }
}





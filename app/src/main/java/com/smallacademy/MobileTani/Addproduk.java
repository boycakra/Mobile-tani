package com.smallacademy.MobileTani;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Addproduk extends AppCompatActivity {
    private EditText nama;
    private EditText Jenis;
    private EditText jmlhbarang;
    private EditText harga;
    private EditText Deskripsi;
    public Uri imageUri;
    private String Phoennumber = "";
    public  String uploadImage="";
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private ImageView picbarang;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference produk =db.collection("Produk");
    private CollectionReference sayur =db.collection("Sayur");
    private CollectionReference buah =db.collection("Buah");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addproduk);
        nama = findViewById(R.id.nambahnamaproduk);
        Jenis = findViewById(R.id.nambahjenis);
        jmlhbarang = findViewById(R.id.jmlhadatabase);
        harga = findViewById(R.id.hargadatabase);
        Deskripsi = findViewById(R.id.deskripsiproduk);
        picbarang = findViewById(R.id.picproduk);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        picbarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }

        });
        DocumentReference userRef = db.collection("Users").document(FirebaseAuth.getInstance().getUid());
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserPetani user = documentSnapshot.toObject(UserPetani.class);
                Phoennumber = user.getPhonenumber();

            }
        });

    }
    public void addNote(View v) {
        String namaValue = nama.getText().toString();
        String deskripsiValue = Deskripsi.getText().toString();
        String JenisValue = Jenis.getText().toString();
        String jmlhValue = jmlhbarang.getText().toString();
        String hargaValue = harga.getText().toString();
        String picValue = uploadImage;
        String Idpetani = FirebaseAuth.getInstance().getUid();


        if (picValue.isEmpty()){
            Toast.makeText(getApplicationContext(),"tambah foto dulu",Toast.LENGTH_LONG).show();
        }else {
            Produkmodel note = new Produkmodel(namaValue,deskripsiValue,JenisValue,jmlhValue,picValue,hargaValue,Idpetani,Phoennumber);
            Log.i("dwiki",Phoennumber);
            produk.add(note).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(getApplicationContext(),"Produk Add Success",Toast.LENGTH_LONG).show();
                }
            });
            Log.i("Sayur",note.getjenis());
            if(note.getjenis().equals("Sayur")) {
                sayur.add(note).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Sayur", Toast.LENGTH_LONG).show();
                    }
                });

            }
            else {
                buah.add(note).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(),"Buah",Toast.LENGTH_LONG).show();
                    }
                });
            }
        }


    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1 );

    }

    public void back(View view) {
        Intent intent = new Intent(this, Petanitampilan.class);
        startActivity(intent);
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData()!=null ){
            imageUri = data.getData();
            picbarang.setImageURI(imageUri);
            uploadPicture();
        }
    }

    private void uploadPicture() {
        final ProgressDialog pd = new ProgressDialog(this );
        pd.setTitle("Uploaded Image");
        pd.show();


        final String randomkey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("produk/"+ randomkey);
        Log.i("access_Token", randomkey);
        UploadTask uploadTask = riversRef.putFile(imageUri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return riversRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            uploadImage = downloadUri.toString();
                        } else {
                            // Handle failures
                            // ...
                        }
                    }
                });

                Snackbar.make(findViewById(android.R.id.content), "Image Uploaded", Snackbar.LENGTH_LONG).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Gagal Upload Gambar", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred()/ snapshot.getTotalByteCount());
                        pd.setMessage("Progress : " + (int) progressPercent + "%");
                    }
                });




    }
}
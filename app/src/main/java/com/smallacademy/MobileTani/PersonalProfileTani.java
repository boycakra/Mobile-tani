package com.smallacademy.MobileTani;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.UUID;

public class PersonalProfileTani extends AppCompatActivity{
    private EditText FullName;
    private EditText Useremail;
    private EditText PhoneNumber;
    private TextView txt_name;
    private TextView txt_name2;
    private TextView txt_address;
    private TextView txt_more;
    private UserPetani user;
    private ImageView profilpic;
    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String LANGUAGE = "En";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profiltani);
        user = (UserPetani) getIntent().getSerializableExtra("Users");
        FullName = findViewById(R.id.name);
        Useremail = findViewById(R.id.university_ID);
        PhoneNumber = findViewById(R.id.Phone_Number);
        txt_name = findViewById(R.id.textView9);
        txt_name2 = findViewById(R.id.textName2);
        txt_address = findViewById(R.id.address);
        txt_more = findViewById(R.id.more);
        profilpic = findViewById(R.id.imageView6);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        profilpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }

        });



        if(loadData().equals("En")){
            TextView textView=findViewById(R.id.header);
            /*textView.setText("Wosool\nPersonal Profile");*/
            TextView textView2=findViewById(R.id.EditTextID);
            /*textView2.setText(" University ID/Employee ID");*/
            TextView textView3=findViewById(R.id.EditTextPhone);
            /*textView3.setText(" Phone Number");*/
            Button button=findViewById(R.id.save);
            button.setText("Save");
            button.setAllCaps(false);

        }



        if(user.getFullName()!=null){
            FullName.setText(user.getFullName());
            txt_name.setText(user.getFullName());
            txt_name2.setText("About "+user.getFullName());
        }
        if(user.getPhonenumber()!=null){
            PhoneNumber.setText(user.getPhonenumber());
        }
        if(user.getAddress()!=null){
            txt_address.setText(user.getAddress());
        }
        if(user.getMore()!=null){
            txt_more.setText(user.getMore());
        }
        if(user.getUseremail()!=null){
            Useremail.setText(user.getUseremail());
        }
        if(user.getPicprofil()!=null){

            Picasso.get().load(user.getPicprofil()).into(profilpic);
        }

    }


    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1 );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData()!=null ){
            imageUri = data.getData();
            profilpic.setImageURI(imageUri);
            uploadPicture();
        }
    }

    private void uploadPicture() {
        final ProgressDialog pd = new ProgressDialog(this );
        pd.setTitle("Uploaded Image");
        pd.show();


        final String randomkey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/"+ randomkey);
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
                                   db.collection("Users").document(FirebaseAuth.getInstance().getUid())
                                      .update("picprofil",downloadUri.toString());
                                   Log.i("Mario", downloadUri.toString());
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

    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void save(View view){
        db.collection("Users").document(FirebaseAuth.getInstance().getUid())
                .update("name",FullName.getText().toString());
        db.collection("Users").document(FirebaseAuth.getInstance().getUid())
                .update("Phonenumber",PhoneNumber.getText().toString());
        db.collection("Users").document(FirebaseAuth.getInstance().getUid())
                .update("Useremail",Useremail.getText().toString());
        db.collection("Users").document(FirebaseAuth.getInstance().getUid())
                .update("address",txt_address.getText().toString());
        db.collection("Users").document(FirebaseAuth.getInstance().getUid())
                .update("more",txt_more.getText().toString());

        if(loadData().equals("En")) {
            Toast.makeText(this,"Saved", Toast.LENGTH_SHORT).show();
        }
    }
    public String loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String currentLanguage = sharedPreferences.getString(LANGUAGE, "En");
        return currentLanguage;
    }

    public void back(View view) {
        Intent intent = new Intent(this, Petanitampilan.class);
        startActivity(intent);
    }
}
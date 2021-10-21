package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AdminActivity extends AppCompatActivity {

    EditText mobTitle;
    EditText mobPrice;
    EditText labTitle;
    EditText labPrice;
    EditText camTitle;
    EditText camPrice;
    EditText gameTitle;
    EditText gamePrice;
    EditText watchTitle;
    EditText watchPrice;
    EditText mobOldPrice;
    EditText labOldPrice;
    EditText camOldPrice;
    EditText watchOldPrice;
    EditText gameOldPrice;
    Button addMob;
    Button addCam;
    Button addLap;
    Button addWatch;
    Button addGame;
    Button btnOrdered;
    ImageView mobImage;
    ImageView camImage;
    ImageView labImage;
    ImageView gameImage;
    ImageView watchImage;


    private DatabaseReference dbRef;
    private FirebaseDatabase fdb;
    private Uri mob_img_uri;
    private Uri cam_img_uri;
    private Uri lab_img_uri;
    private Uri game_img_uri;
    private Uri watch_img_uri;
    private StorageReference mStorage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mobTitle =findViewById(R.id.mob_title);
        mobPrice = findViewById(R.id.mob_price);
        labTitle =findViewById(R.id.lab_title);
        labPrice = findViewById(R.id.lab_price);
        camTitle =findViewById(R.id.cam_title);
        camPrice = findViewById(R.id.cam_price);
        gameTitle =findViewById(R.id.GC_title);
        gamePrice = findViewById(R.id.GC_price);
        watchTitle =findViewById(R.id.watch_title);
        watchPrice = findViewById(R.id.watch_price);
        addMob =findViewById(R.id.btnAddMob);
        addLap =findViewById(R.id.btnAddLab);
        addCam =findViewById(R.id.btnAddCam);
        addGame =findViewById(R.id.btnAddGame);
        addWatch =findViewById(R.id.btnAddWatch);
        mobImage =findViewById(R.id.mobImage);
        labImage =findViewById(R.id.labImage);
        camImage =findViewById(R.id.camImage);
        gameImage =findViewById(R.id.gameImage);
        watchImage =findViewById(R.id.watchImage);
        btnOrdered = findViewById(R.id.btn_ordered);
        mobOldPrice = findViewById(R.id.mob_old_price);
        camOldPrice = findViewById(R.id.cam_old_price);
        labOldPrice = findViewById(R.id.lab_old_price);
        watchOldPrice = findViewById(R.id.watch_old_price);
        gameOldPrice = findViewById(R.id.GC_old_price);


        fdb = FirebaseDatabase.getInstance();
        dbRef = fdb.getReference().child("products");
        mStorage = FirebaseStorage.getInstance().getReference();



        addMob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = dbRef.child("mobiles").push().child("title");
                databaseReference.setValue(mobTitle.getText().toString());
                databaseReference.getParent().child("oldPrice").setValue(mobOldPrice.getText().toString());
                databaseReference.getParent().child("price").setValue(mobPrice.getText().toString());
                StorageReference filepath = mStorage.child("products").child("mobiles").child(databaseReference.getParent().getKey()).child(databaseReference.getParent().getKey());
                filepath.putFile(mob_img_uri);
                mobTitle.setText("");
                mobPrice.setText("");
                mobOldPrice.setText("");
            }
        });
        addLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = dbRef.child("labtops").push().child("title");
                databaseReference.setValue(labTitle.getText().toString());
                databaseReference.getParent().child("oldPrice").setValue(labOldPrice.getText().toString());
                databaseReference.getParent().child("price").setValue(labPrice.getText().toString());
                StorageReference filepath = mStorage.child("products").child("labtops").child(databaseReference.getParent().getKey()).child(databaseReference.getParent().getKey());
                filepath.putFile(lab_img_uri);
                labTitle.setText("");
                labPrice.setText("");
                labOldPrice.setText("");
            }
        });
        addCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = dbRef.child("cameras").push().child("title");
                databaseReference.setValue(camTitle.getText().toString());
                databaseReference.getParent().child("oldPrice").setValue(camOldPrice.getText().toString());
                databaseReference.getParent().child("price").setValue(camPrice.getText().toString());
                StorageReference filepath = mStorage.child("products").child("cameras").child(databaseReference.getParent().getKey()).child(databaseReference.getParent().getKey());
                filepath.putFile(cam_img_uri);
                camTitle.setText("");
                camPrice.setText("");
                camOldPrice.setText("");
            }
        });
        addGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = dbRef.child("gameConsole").push().child("title");
                databaseReference.setValue(gameTitle.getText().toString());
                databaseReference.getParent().child("oldPrice").setValue(gameOldPrice.getText().toString());
                databaseReference.getParent().child("price").setValue(gamePrice.getText().toString());
                StorageReference filepath = mStorage.child("products").child("gameConsole").child(databaseReference.getParent().getKey()).child(databaseReference.getParent().getKey());
                filepath.putFile(game_img_uri);
                gameTitle.setText("");
                gamePrice.setText("");
                gameOldPrice.setText("");
            }
        });
        addWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = dbRef.child("smartWatchs").push().child("title");
                databaseReference.setValue(watchTitle.getText().toString());
                databaseReference.getParent().child("oldPrice").setValue(watchOldPrice.getText().toString());
                databaseReference.getParent().child("price").setValue(watchPrice.getText().toString());
                StorageReference filepath = mStorage.child("products").child("smartWatchs").child(databaseReference.getParent().getKey()).child(databaseReference.getParent().getKey());
                filepath.putFile(watch_img_uri);
                watchTitle.setText("");
                watchPrice.setText("");
                watchOldPrice.setText("");
            }
        });
        btnOrdered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),cartActivity.class);
                intent.putExtra("check","users");
                startActivity(intent);
            }
        });


    }

    public void imageView_click(View view) {
       Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
       switch (view.getId()){
           case R.id.mobImage:  startActivityForResult(gallery, 1);
               break;
           case R.id.camImage:  startActivityForResult(gallery, 2);
               break;
           case R.id.labImage:  startActivityForResult(gallery, 3);
               break;
           case R.id.watchImage:  startActivityForResult(gallery, 4);
               break;
           case R.id.gameImage:  startActivityForResult(gallery, 5);
               break;
       }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK  && data != null) {

            Uri imagerUri = data.getData();
            switch (requestCode){
                case 1: mobImage.setImageURI(imagerUri);
                mob_img_uri = imagerUri;
                    break;
                case 2: camImage.setImageURI(imagerUri);
                cam_img_uri =imagerUri;
                    break;
                case 3: labImage.setImageURI(imagerUri);
                lab_img_uri =imagerUri;
                break;
                case 4: watchImage.setImageURI(imagerUri);
                watch_img_uri =imagerUri;
                break;
                case 5: gameImage.setImageURI(imagerUri);
                game_img_uri=imagerUri;
                    break;
            }

            Toast.makeText(getApplicationContext(), imagerUri.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
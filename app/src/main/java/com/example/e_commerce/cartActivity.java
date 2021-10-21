package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class cartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView orderText;
    private TextView cartTitle;
    private TextView cartOldPrice;
    private TextView cartPrice;
    private TextView cartDetails;
    private ImageView cartImage;



    private ArrayList<products> arrayList;
    private ArrayList<users> users;


    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        firebaseDatabase = FirebaseDatabase.getInstance();

        if(getIntent().getStringExtra("check").equals("specficUser")){
            String id = getIntent().getStringExtra("id");
            databaseReference = firebaseDatabase.getReference().child("users").child(id).child("ordered");

        }else {
            databaseReference = firebaseDatabase.getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("ordered");
        }

        arrayList = new ArrayList<>();
        users = new ArrayList<>();

        cartImage = findViewById(R.id.cartImage);
        cartDetails = findViewById(R.id.cartDetails);
        cartOldPrice = findViewById(R.id.cartOldPrice);
        cartPrice = findViewById(R.id.cartprice);
        cartTitle = findViewById(R.id.cartTitle);
        orderText = findViewById(R.id.orderedItem_text);
        progressBar = findViewById(R.id.progressBarCart);
        recyclerView = findViewById(R.id.items_recycle);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        productAdapter ad = new productAdapter(this,arrayList);
        baseAdapter ad2 = new baseAdapter(this,users);

        if(getIntent().getStringExtra("check").equals("users")){

            recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
            recyclerView.setHasFixedSize(true);

            DatabaseReference databaseReference1 = firebaseDatabase.getReference().child("users");
            databaseReference1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    users.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String user = (String) dataSnapshot.child("username").getValue();
                        users.add(new users(user,dataSnapshot.getKey()));
                    }
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setAdapter(ad2);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else if(getIntent().getStringExtra("check").equals("productDetails")){
             orderText.setVisibility(View.GONE);
             recyclerView.setVisibility(View.GONE);
             progressBar.setVisibility(View.GONE);
             cartImage.setVisibility(View.VISIBLE);
             cartPrice.setVisibility(View.VISIBLE);
             cartDetails.setVisibility(View.VISIBLE);
             cartTitle.setVisibility(View.VISIBLE);
             cartOldPrice.setPaintFlags(cartOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
             cartTitle.setText(getIntent().getStringArrayExtra("details")[0]);
             if(getIntent().getStringArrayExtra("details")[1]!=null){
                 cartOldPrice.setVisibility(View.VISIBLE);
                 cartOldPrice.setText(getIntent().getStringArrayExtra("details")[1]+" $");
             }
             cartPrice.setText(getIntent().getStringArrayExtra("details")[2]);
             cartDetails.setText(getIntent().getStringArrayExtra("details")[3]);
             StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl((getIntent().getStringArrayExtra("details")[4]));
            Glide.with(this).load(storageReference).into(cartImage);

        } else {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    arrayList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String title = (String) dataSnapshot.child("title").getValue();
                        String oldPrice = (String) dataSnapshot.child("oldPrice").getValue();
                        String price = (String) dataSnapshot.child("price").getValue();
                        String quantity = (String) dataSnapshot.child("quantity").getValue();
                        String details = (String) dataSnapshot.child("details").getValue();
                        String url = (String) dataSnapshot.child("imagePath").getValue();
                        FirebaseStorage fs = FirebaseStorage.getInstance();
                        try {
                            StorageReference filepath = fs.getReferenceFromUrl(url);
                            if(getIntent().getStringExtra("check").equals("specficUser")) {
                                String id = getIntent().getStringExtra("id");
                                arrayList.add(new products(title, oldPrice, price, filepath, Integer.parseInt(quantity), details,id));
                            }else{
                                arrayList.add(new products(title, oldPrice, price, filepath, Integer.parseInt(quantity), details,"0"));

                            }

                        } catch (IllegalArgumentException e) {
                        }

                    }
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setAdapter(ad);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}
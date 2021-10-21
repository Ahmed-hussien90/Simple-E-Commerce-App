package com.example.e_commerce;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link productFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class productFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseDatabase fdb;
    private DatabaseReference dbref;
    private RecyclerView recyclerView;
    private ArrayList<products> arrayList;
    private ProgressBar progressBar;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tasksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static productFragment newInstance(String param1, String param2) {
        productFragment fragment = new productFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public productFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fdb = FirebaseDatabase.getInstance();
        dbref = fdb.getReference().child("products");
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.product_recyclar);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        arrayList = new ArrayList<>();
        productAdapter adapter = new productAdapter(this.getContext(),arrayList);

        if (mParam2.equals("mobile")) {

            dbref.child("mobiles").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    arrayList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String title = (String) dataSnapshot.child("title").getValue();
                        String oldPrice = (String) dataSnapshot.child("oldPrice").getValue();
                        String price = (String) dataSnapshot.child("price").getValue();
                        String details = (String) dataSnapshot.child("details").getValue();
                        FirebaseStorage fs = FirebaseStorage.getInstance();
                        StorageReference filepath = fs.getReference().child("products").child("mobiles").child(dataSnapshot.getKey()).child(dataSnapshot.getKey());
                        arrayList.add(new products(title,oldPrice,price,filepath,0,details,"0"));
                    }
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }else if(mParam2.equals("game console")){
            dbref.child("gameConsole").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    arrayList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String title = (String) dataSnapshot.child("title").getValue();
                        String oldPrice = (String) dataSnapshot.child("oldPrice").getValue();
                        String price = (String) dataSnapshot.child("price").getValue();
                        String details = (String) dataSnapshot.child("details").getValue();
                        StorageReference filepath = FirebaseStorage.getInstance().getReference().child("products").child("gameConsole").child(dataSnapshot.getKey()).child(dataSnapshot.getKey());
                        arrayList.add(new products(title,oldPrice,price,filepath,0,details,"0"));
                    }
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else if(mParam2.equals("smart watches")){
            dbref.child("smartWatchs").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    arrayList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String title = (String) dataSnapshot.child("title").getValue();
                        String oldPrice = (String) dataSnapshot.child("oldPrice").getValue();
                        String price = (String) dataSnapshot.child("price").getValue();
                        String details = (String) dataSnapshot.child("details").getValue();
                        StorageReference filepath = FirebaseStorage.getInstance().getReference().child("products").child("smartWatchs").child(dataSnapshot.getKey()).child(dataSnapshot.getKey());
                        arrayList.add(new products(title,oldPrice,price,filepath,0,details,"0"));
                    }
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else if(mParam2.equals("labtop")){
            dbref.child("labtops").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    arrayList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String title = (String) dataSnapshot.child("title").getValue();
                        String oldPrice = (String) dataSnapshot.child("oldPrice").getValue();
                        String price = (String) dataSnapshot.child("price").getValue();
                        String details = (String) dataSnapshot.child("details").getValue();
                        StorageReference filepath = FirebaseStorage.getInstance().getReference().child("products").child("labtops").child(dataSnapshot.getKey()).child(dataSnapshot.getKey());
                        arrayList.add(new products(title,oldPrice,price,filepath,0,details,"0"));
                    }
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else if(mParam2.equals("camera")){
            dbref.child("cameras").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    arrayList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String title = (String) dataSnapshot.child("title").getValue();
                        String oldPrice = (String) dataSnapshot.child("oldPrice").getValue();
                        String price = (String) dataSnapshot.child("price").getValue();
                        String details = (String) dataSnapshot.child("details").getValue();
                        StorageReference filepath = FirebaseStorage.getInstance().getReference().child("products").child("cameras").child(dataSnapshot.getKey()).child(dataSnapshot.getKey());
                        arrayList.add(new products(title,oldPrice,price,filepath,0,details,"0"));
                    }
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
}

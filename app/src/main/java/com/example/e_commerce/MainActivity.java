package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase fdb;
    private FirebaseStorage fs;
    private StorageReference adsRef;
    private DatabaseReference dbRefUname;
    private DatabaseReference dbRefEmail;
    private DatabaseReference dbRefAdmins;
    private DatabaseReference orderedItems;




    TextView currUser;
    ImageView cart;
    ImageView navigation_menu;
    private DrawerLayout drawer;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private LinearLayout logOut;
    String currEmail;
    String adminEmail;
    MenuItem adminCenter;
    CardView notiCounter;
    TextView notiNumber;
    long noOFOrders;


    SliderView sliderView;
    ArrayList<StorageReference> images ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fdb = FirebaseDatabase.getInstance();
        fs = FirebaseStorage.getInstance();
        adsRef = fs.getReference().child("products").child("adv");
        dbRefUname = fdb.getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("username");
        dbRefEmail = fdb.getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("email");
        orderedItems = fdb.getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("ordered");
        dbRefAdmins = fdb.getReference().child("admins");
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        cart = findViewById(R.id.btnCart);
        drawer = findViewById(R.id.drawer_layout);
        notiCounter =findViewById(R.id.notification_counter);
        notiNumber = findViewById(R.id.notification_number);
        noOFOrders = 0;

        orderedItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              noOFOrders =snapshot.getChildrenCount();
                if(noOFOrders!=0){
                    notiCounter.setVisibility(View.VISIBLE);
                    notiNumber.setText(String.valueOf(noOFOrders));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Log.e("MAin ",String.valueOf(noOFOrders));


        NavigationView navigationView = findViewById(R.id.nav_view);
        adminCenter = navigationView.getMenu().findItem(R.id.nav_admin);
        adminCenter.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(getBaseContext(),AdminActivity.class));
                return false;
            }
        });
        View view = navigationView.getHeaderView(0);
        currUser = view.findViewById(R.id.currUsername);
        logOut = view.findViewById(R.id.logout);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getBaseContext(),"LogedOut",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getBaseContext(),start.class));
                finish();
            }
        });

        navigation_menu = findViewById(R.id.nav_menu);
        navigation_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });



        sliderView = findViewById(R.id.image_slider);
        images = new ArrayList<>();

        SliderAdapter sliderAdapter = new SliderAdapter(this,images);

        adsRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference item : listResult.getItems()) {
                   images.add(item);
                }
                sliderView.setSliderAdapter(sliderAdapter);
                sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
                sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
                sliderView.startAutoCycle();
            }
        });


        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager());
        pageAdapter.addTap(new Taps("mobile",productFragment.newInstance("1","mobile")));
        pageAdapter.addTap(new Taps("labtop",productFragment.newInstance("2","labtop")));
        pageAdapter.addTap(new Taps("camera",productFragment.newInstance("3","camera")));
        pageAdapter.addTap(new Taps("game console",productFragment.newInstance("4","game console")));
        pageAdapter.addTap(new Taps("smart watches",productFragment.newInstance("5","smart watches")));

        viewPager.setAdapter(pageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        dbRefUname.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currUser.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),cartActivity.class);
                intent.putExtra("check","ordered");
                startActivity(intent);
            }
        });

        orderedItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    noOFOrders++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        dbRefEmail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currEmail = snapshot.getValue().toString();
                dbRefAdmins.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        adminEmail = snapshot.getValue().toString();
                        if(currEmail.equals(adminEmail)){
                            adminCenter.setVisible(true);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}